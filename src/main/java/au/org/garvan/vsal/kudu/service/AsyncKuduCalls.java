package au.org.garvan.vsal.kudu.service;

import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.core.entity.VariantType;
import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;
import org.apache.kudu.Schema;
import org.apache.kudu.client.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.apache.kudu.client.KuduPredicate.newComparisonPredicate;

public class AsyncKuduCalls {

    private static class Counters {
        int sc   = 0; // alt allele sample count
        int homc = 0; // alt allele hom count
    }

    private static class Variant {
        CoreVariant cv;
        String gt;

        public Variant(CoreVariant cv, String gt) {
            this.cv = cv;
            this.gt = gt;
        }
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

    private static AsyncKuduScanner getAsyncScanner(AsyncKuduClient client, KuduTable gtTable, List<String> columns, CoreQuery query, Integer sampleId)
            throws KuduException {
        Schema schema = gtTable.getSchema();
        AsyncKuduScanner.AsyncKuduScannerBuilder aksb = client.newScannerBuilder(gtTable);
        aksb.setProjectedColumnNames(columns);
        if (query.getChromosome() != null)
            aksb.addPredicate(newComparisonPredicate(schema.getColumn("contig"), KuduPredicate.ComparisonOp.EQUAL, query.getChromosome().toString()));
        if (query.getPositionStart() != null)
            aksb.addPredicate(newComparisonPredicate(schema.getColumn("start"), KuduPredicate.ComparisonOp.GREATER_EQUAL, query.getPositionStart()));
        if (query.getPositionEnd() != null)
            aksb.addPredicate(newComparisonPredicate(schema.getColumn("start"), KuduPredicate.ComparisonOp.LESS_EQUAL, query.getPositionEnd()));
        if (query.getType() != null)
            aksb.addPredicate(newComparisonPredicate(schema.getColumn("vtype"), KuduPredicate.ComparisonOp.EQUAL, query.getType().toByte()));
        if (query.getRefAllele() != null && !query.getRefAllele().isEmpty())
            aksb.addPredicate(newComparisonPredicate(schema.getColumn("ref"), KuduPredicate.ComparisonOp.EQUAL, query.getRefAllele()));
        if (query.getAltAllele() != null && !query.getAltAllele().isEmpty())
            aksb.addPredicate(newComparisonPredicate(schema.getColumn("alt"), KuduPredicate.ComparisonOp.EQUAL, query.getAltAllele()));
        if (query.getDbSNP() != null && !query.getDbSNP().isEmpty())
            aksb.addPredicate(newComparisonPredicate(schema.getColumn("rsid"), KuduPredicate.ComparisonOp.EQUAL, query.getDbSNP().get(0)));
        if (sampleId != null)
            aksb.addPredicate(newComparisonPredicate(schema.getColumn("sample_id"), KuduPredicate.ComparisonOp.EQUAL, sampleId));
        return aksb.build();
    }


    public static List<CoreVariant> variantsInVirtualCohort(CoreQuery query, List<String> samples) {
        List<Integer> sampleIds;
        final KuduClient client = new KuduClient.KuduClientBuilder(prop.getProperty("kuduMaster")).build();

        try {
            sampleIds = getSampleIds(client, samples);
            if (samples.size() != sampleIds.size())
                throw new RuntimeException("Inconsistency: some samples have several Ids");
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

        KuduTable gtTable;
        AsyncKuduClient asyncClient = new AsyncKuduClient.AsyncKuduClientBuilder(prop.getProperty("kuduMaster")).build();

        try {
            final class getTable implements Callback<KuduTable, KuduTable> {
                @Override
                public KuduTable call(KuduTable table) { return table; }
            }
            gtTable = asyncClient.openTable("gt").addCallback(new getTable()).join();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        List<AsyncKuduScanner> sampleScanners = new LinkedList<>();
        Map<Integer, List<Variant>> variantsBySamples = new HashMap<>(sampleIds.size());
        Map<Integer, Deferred<List<Variant>>> deferredVariantsBySamples = new HashMap<>(sampleIds.size());
        List<String> columns = Arrays.asList("contig", "start", "ref", "alt", "rsid", "vtype", "gt");

        try {
            final class AsyncVariantsBySample {
                final private AsyncKuduScanner asyncScanner;

                private AsyncVariantsBySample(AsyncKuduScanner asyncScanner) {
                    this.asyncScanner = asyncScanner;
                }

                final class AsyncProcessRows implements Callback<Deferred<List<Variant>>, RowResultIterator> {
                    private final List<Variant> res = new LinkedList<>();
                    @Override
                    public Deferred<List<Variant>> call(RowResultIterator results) {
                        if (results != null) {
                            for (RowResult row : results) {
                                VariantType t = VariantType.fromByte(row.getByte(5));
                                String type = (t == null) ? null : t.toString();
                                CoreVariant cv = new CoreVariant(row.getString(0), row.getInt(1), (row.getInt(4)==0) ? null : " rs" + row.getInt(4),
                                        row.getString(3), row.getString(2), type, null, null, null, null, null, null, null, null);
                                res.add(new Variant(cv, row.getString(6)));
                            }
                            if (asyncScanner.hasMoreRows()) {
                                return asyncScanner.nextRows().addBothDeferring(this);
                            }
                        }
                        return Deferred.fromResult(res);
                    }
                }

                private Deferred<List<Variant>> processAllRows() {
                    return asyncScanner.nextRows().addBothDeferring(new AsyncProcessRows());
                }
            }

            // async calls
            for (Integer sid: sampleIds) {
                final AsyncKuduScanner asyncScanner = getAsyncScanner(asyncClient, gtTable, columns, query, sid);
                sampleScanners.add(asyncScanner); // to close them later
                final AsyncVariantsBySample allVars = new AsyncVariantsBySample(asyncScanner);
                deferredVariantsBySamples.put(sid, allVars.processAllRows());
            }

            // sync deferred
            for (Integer sid : deferredVariantsBySamples.keySet()) {
                variantsBySamples.put(sid, deferredVariantsBySamples.get(sid).join());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                for (AsyncKuduScanner s : sampleScanners) s.close();
                asyncClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean first = true;
        boolean conj = query.getConj();
        Map<CoreVariant, Counters> uniqueVariants = new HashMap<>();

        for (List<Variant> sv : variantsBySamples.values()) {
            for (Variant v : sv) {
                Counters c = (uniqueVariants.containsKey(v.cv)) ? uniqueVariants.get(v.cv) : new Counters();
                c.sc += 1;
                if (v.gt == null)
                    throw new RuntimeException("Inconsistency: Null GT");
                if (v.gt.equals("1/1") || v.gt.equals("1|1"))
                    c.homc += 1;
                if (first || !conj || uniqueVariants.containsKey(v.cv))
                    uniqueVariants.put(v.cv, c);
            }
            first = false;
        }

        // Virtual cohort stats & final results
        int i = 0;
        boolean unlim = query.getLimit() == null;
        int lim = (unlim) ? 0 : query.getLimit();
        List<CoreVariant> coreVariants = new ArrayList<>(uniqueVariants.size());

        for (Map.Entry<CoreVariant, Counters> entry : uniqueVariants.entrySet()) {
            if (!unlim && i >= lim) break;
            CoreVariant cv = entry.getKey();
            Counters c = entry.getValue();
            if (conj && (c.sc != samples.size())) continue;
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