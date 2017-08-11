package au.org.garvan.vsal.kudu.service;

import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.core.entity.VariantType;
import org.apache.kudu.Schema;
import org.apache.kudu.client.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.apache.kudu.client.KuduPredicate.newComparisonPredicate;

public class KuduCalls {

    private static class Counters {
        int sc   = 0; // alt allele sample count
        int homc = 0; // alt allele hom count
    }

    private static final String propFileName = "vsal.properties";
    private static final Properties prop = readConfig();

    private static Properties readConfig() {
        Properties p = new Properties();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream != null) {
                p.load(inputStream);
            } else {
                System.out.println("Can't read properties from " + propFileName + ". Setting up Kudu Master as localhost:7051");
                p.setProperty("kuduMaster","localhost:7051");
            }
        } catch (IOException e) {
            System.out.println("Can't read properties from " + propFileName + ". Setting up Kudu Master as localhost:7051");
            p.setProperty("kuduMaster","localhost:7051");
        }
        return p;
    }

    private static KuduScanner getScanner(KuduClient client, String tableName, List<String> columns, CoreQuery query, Integer sampleId)
            throws KuduException {
        KuduTable table = client.openTable(tableName);
        Schema schema = table.getSchema();
        KuduScanner.KuduScannerBuilder ksb = client.newScannerBuilder(table);
        ksb.setProjectedColumnNames(columns);
        if (query.getChromosome() != null)
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("contig"), KuduPredicate.ComparisonOp.EQUAL, query.getChromosome().toString()));
        if (query.getPositionStart() != null)
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("start"), KuduPredicate.ComparisonOp.GREATER_EQUAL, query.getPositionStart()));
        if (query.getPositionEnd() != null)
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("start"), KuduPredicate.ComparisonOp.LESS_EQUAL, query.getPositionEnd()));
        if (query.getType() != null)
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("vtype"), KuduPredicate.ComparisonOp.EQUAL, query.getType().toByte()));
        if (query.getRefAllele() != null && !query.getRefAllele().isEmpty())
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("ref"), KuduPredicate.ComparisonOp.EQUAL, query.getRefAllele()));
        if (query.getAltAllele() != null && !query.getAltAllele().isEmpty())
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("alt"), KuduPredicate.ComparisonOp.EQUAL, query.getAltAllele()));
        if (query.getDbSNP() != null && !query.getDbSNP().isEmpty())
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("rsid"), KuduPredicate.ComparisonOp.EQUAL, query.getDbSNP().get(0)));
        if (sampleId != null)
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("sample_id"), KuduPredicate.ComparisonOp.EQUAL, sampleId));
        return ksb.build();
    }

    private static List<Integer> getSampleIds(KuduClient client, List<String> samples)
            throws KuduException {
        List<Integer> sid = new LinkedList<>();
        KuduTable table = client.openTable("samples");
        Schema schema = table.getSchema();
        for (String name: samples) {
            KuduScanner.KuduScannerBuilder ksb = client.newScannerBuilder(table);
            ksb.setProjectedColumnNames(Collections.singletonList("sample_id"));
            ksb.addPredicate(newComparisonPredicate(schema.getColumn("sample_name"), KuduPredicate.ComparisonOp.EQUAL, name));
            KuduScanner scanner = ksb.build();
            boolean found = false;
            while (scanner.hasMoreRows()) {
                RowResultIterator results = scanner.nextRows();
                while (results != null && results.hasNext()) {
                    sid.add(results.next().getInt(0));
                    found = true;
                }
            }
            scanner.close();
            if (!found)
                throw new RuntimeException("Inconsistency - sample doesn't exist: " + name);
        }
        return sid;
    }

    public static List<CoreVariant> variants(CoreQuery query) {
        List<CoreVariant> coreVariants = new ArrayList<>();
        KuduClient client = new KuduClient.KuduClientBuilder(prop.getProperty("kuduMaster")).build();

        try {
            List<String> columns = Arrays.asList("contig", "start", "ref", "alt", "rsid", "vtype", "af", "ac", "homc", "hetc");
            KuduScanner scanner = getScanner(client, "variants", columns, query, null);
            int i = 0;
            boolean unlim = query.getLimit() == null;
            int lim = (unlim) ? 0 : query.getLimit();
            while ((unlim || i < lim) && scanner.hasMoreRows()) {
                RowResultIterator results = scanner.nextRows();
                if (results == null) break;
                while ((unlim || i < lim) && results.hasNext()) {
                    RowResult result = results.next();
                    VariantType t = VariantType.fromByte(result.getByte(5));
                    String type = (t == null) ? null : t.toString();
                    CoreVariant cv = new CoreVariant(result.getString(0), result.getInt(1), (result.getInt(4)==0) ? null : " rs" + result.getInt(4),
                            result.getString(3), result.getString(2), type, result.getInt(7), result.getFloat(6), result.getInt(8), result.getInt(9),
                            null, null, null, null);
                    coreVariants.add(cv);
                    ++i;
                 }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (coreVariants);
    }

    public static List<CoreVariant> variantsInVirtualCohort(CoreQuery query, List<String> samples) {
        KuduClient client = new KuduClient.KuduClientBuilder(prop.getProperty("kuduMaster")).build();
        Map<CoreVariant, Counters> res = new HashMap<>();
        boolean first = true;

        try {
            List<Integer> sampleIds = getSampleIds(client, samples);
            if (samples.size() != sampleIds.size())
                throw new RuntimeException("Inconsistency: some samples have several Ids");
            List<String> columns = Arrays.asList("contig", "start", "ref", "alt", "rsid", "vtype", "gt");
            for (Integer sid: sampleIds) {
                KuduScanner scanner = getScanner(client, "gt", columns, query, sid);
                while (scanner.hasMoreRows()) {
                    RowResultIterator results = scanner.nextRows();
                    if (results == null) break;
                    while (results.hasNext()) {
                        RowResult result = results.next();
                        VariantType t = VariantType.fromByte(result.getByte(5));
                        String type = (t == null) ? null : t.toString();
                        CoreVariant cv = new CoreVariant(result.getString(0), result.getInt(1), (result.getInt(4)==0) ? null : " rs" + result.getInt(4),
                                result.getString(3), result.getString(2), type, null, null, null, null, null, null, null, null);
                        Counters c = (res.containsKey(cv)) ? res.get(cv) : new Counters();
                        c.sc += 1;
                        String gt = result.getString(6);
                        if (gt == null)
                            throw new RuntimeException("Inconsistency: Null GT");
                        if (gt.equals("1/1") || gt.equals("1|1"))
                            c.homc += 1;
                        if (first || res.containsKey(cv) || !query.getConj())
                            res.put(cv, c);
                    }
                }
                scanner.close();
                first = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int i = 0;
        boolean unlim = query.getLimit() == null;
        int lim = (unlim) ? 0 : query.getLimit();
        List<CoreVariant> coreVariants = new ArrayList<>(res.size());

        for (Map.Entry<CoreVariant, Counters> entry : res.entrySet()) {
            if (!unlim && i >= lim) break;
            CoreVariant cv = entry.getKey();
            Counters c = entry.getValue();
            if (query.getConj() && (c.sc != samples.size())) continue;
            cv.setVhomc(c.homc);
            cv.setVhetc(c.sc - c.homc);
            cv.setVac(2*c.homc + c.sc - c.homc);
            cv.setVaf(cv.getVac()/(float)(2*c.sc));
            coreVariants.add(cv);
            ++i;
        }

        return coreVariants;
    }
}
