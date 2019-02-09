/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Dmitry Degrave
 * Copyright (c) 2019 Garvan Institute of Medical Research
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package au.org.garvan.vsal.kudu.service;

import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.core.entity.VariantType;
import au.org.garvan.vsal.core.util.ReadConfig;
import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;
import org.apache.kudu.Schema;
import org.apache.kudu.client.*;

import java.util.*;

import static org.apache.kudu.client.KuduPredicate.newComparisonPredicate;

public class AsyncKuduCalls {

    private static class Counters {
        int sc = 0;   // alt allele sample count
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

    private static List<Integer> getSampleIDsByNames(KuduClient client, String tableName, List<String> samples)
            throws KuduException {
        List<Integer> sid = new LinkedList<>();
        KuduTable table = client.openTable(tableName);
        Schema schema = table.getSchema();
        for (String name : samples) {
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

    private static Map<Integer, String> getAllSamples(KuduClient client, String tableName)
            throws KuduException {
        Map<Integer, String> samples = new HashMap<>();
        KuduTable table = client.openTable(tableName);

        KuduScanner.KuduScannerBuilder ksb = client.newScannerBuilder(table);
        ksb.setProjectedColumnNames(Arrays.asList("sample_id", "sample_name"));
        KuduScanner scanner = ksb.build();

        while (scanner.hasMoreRows()) {
            RowResultIterator results = scanner.nextRows();
            while (results != null && results.hasNext()) {
                RowResult result = results.next();
                samples.put(result.getInt(0), result.getString(1));
            }
        }
        scanner.close();
        return samples;
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
        final KuduClient client = new KuduClient.KuduClientBuilder(ReadConfig.getProp().getProperty("kuduMaster")).build();

        try {
            sampleIds = getSampleIDsByNames(client, query.getDatasetId() + "_samples", samples);
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

        AsyncKuduClient asyncClient = new AsyncKuduClient.AsyncKuduClientBuilder(ReadConfig.getProp().getProperty("kuduMaster")).build();
        KuduTable gtTable = addCallBackToTable(asyncClient, query.getDatasetId() + "_gt");
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
            cv.setVaf(cv.getVac()/(float)(2*samples.size()));
            coreVariants.add(cv);
            ++i;
        }

        return coreVariants;
    }

    public static List<String> selectSamplesByGT(CoreQuery query) {
        // detects variant existence in a region for all sample IDs
        // saved as a boolean flag per sample
        // implementation similar to variantsInVirtualCohort(), but doesn't keep variants - only flags

        Map<Integer, String> allSamples;
        final KuduClient client = new KuduClient.KuduClientBuilder(ReadConfig.getProp().getProperty("kuduMaster")).build();
        try {
            allSamples = getAllSamples(client, query.getDatasetId() + "_samples");
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

        AsyncKuduClient asyncClient = new AsyncKuduClient.AsyncKuduClientBuilder(ReadConfig.getProp().getProperty("kuduMaster")).build();
        KuduTable gtTable = addCallBackToTable(asyncClient, query.getDatasetId() + "_gt");
        List<AsyncKuduScanner> sampleScanners = new LinkedList<>();

        Set<Integer> sampleIds = allSamples.keySet();
        Map<Integer, Boolean> bySamples = new HashMap<>(sampleIds.size());
        Map<Integer, Deferred<Boolean>> deferredBySamples = new HashMap<>(sampleIds.size());
        List<String> columns = Arrays.asList("sample_id");

        try {
            final class AsyncVariantsExistBySample {
                final private AsyncKuduScanner asyncScanner;

                private AsyncVariantsExistBySample(AsyncKuduScanner asyncScanner) {
                    this.asyncScanner = asyncScanner;
                }

                final class AsyncProcessRows implements Callback<Deferred<Boolean>, RowResultIterator> {
                    private Boolean res = false;

                    @Override
                    public Deferred<Boolean> call(RowResultIterator results) {
                        if (results != null && results.hasNext()) {
                            res = true; // there is at least one variant in a region in a given sample
                        }
                        return Deferred.fromResult(res);
                    }
                }

                private Deferred<Boolean> processAllRows() {
                    return asyncScanner.nextRows().addBothDeferring(new AsyncProcessRows());
                }
            }

            // async calls
            for (Integer sid : sampleIds) {
                final AsyncKuduScanner asyncScanner = getAsyncScanner(asyncClient, gtTable, columns, query, sid);
                sampleScanners.add(asyncScanner); // to close them later
                final AsyncVariantsExistBySample allVars = new AsyncVariantsExistBySample(asyncScanner);
                deferredBySamples.put(sid, allVars.processAllRows());
            }

            // sync deferred
            for (Integer sid : deferredBySamples.keySet()) {
                bySamples.put(sid, deferredBySamples.get(sid).join());
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

        List<String> selectedSamplesNames = new LinkedList<>();
        for (Integer sid : bySamples.keySet()) {
            if (bySamples.get(sid))
                selectedSamplesNames.add(allSamples.get(sid));
        }

        return selectedSamplesNames;
    }


    private static KuduTable addCallBackToTable(AsyncKuduClient asyncClient, String tbl) {
        try {
            final class getTable implements Callback<KuduTable, KuduTable> {
                @Override
                public KuduTable call(KuduTable table) {
                    return table;
                }
            }
            return asyncClient.openTable(tbl).addCallback(new getTable()).join();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}