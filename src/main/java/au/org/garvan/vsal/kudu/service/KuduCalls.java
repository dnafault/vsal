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
import org.apache.kudu.Schema;
import org.apache.kudu.client.*;

import java.util.*;

import static org.apache.kudu.client.KuduPredicate.newComparisonPredicate;

public class KuduCalls {

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

    public static List<CoreVariant> variants(CoreQuery query) {
        List<CoreVariant> coreVariants = new ArrayList<>();
        KuduClient client = new KuduClient.KuduClientBuilder(ReadConfig.getProp().getProperty("kuduMaster")).build();

        try {
            List<String> columns = Arrays.asList("contig", "start", "ref", "alt", "rsid", "vtype", "af", "ac", "homc", "hetc");
            KuduScanner scanner = getScanner(client, query.getDatasetId() + "_variants", columns, query, null);
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
                            result.getString(3), result.getString(2), type, result.getFloat(7), result.getFloat(6), result.getInt(8), result.getInt(9),
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
        return coreVariants;
    }
}