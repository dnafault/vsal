/*
 * The MIT License
 *
 * Copyright 2014 Miroslav Cupak (mirocupak@gmail.com).
 * Copyright 2019 Dmitry Degrave (dmeetry@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package au.org.garvan.vsal.core.util;

import au.org.garvan.vsal.beacon.entity.Chromosome;
import au.org.garvan.vsal.beacon.entity.Reference;
import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.DatasetID;
import au.org.garvan.vsal.core.entity.VariantType;

import java.util.*;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils for query manipulation.
 *
 * @author Miroslav Cupak (mirocupak@gmail.com)
 * @author Dmitry Degrave
 * @version 1.0
 */
public class CoreQueryUtils {

    private static final Map<Reference, String> chromMapping = new HashMap<>();
    private static final int MAX_VARIANTS = 100000;

    static {
        chromMapping.put(Reference.HG38, "GRCh38");
        chromMapping.put(Reference.HG19, "GRCh37");
        chromMapping.put(Reference.HG18, "NCBI36");
        chromMapping.put(Reference.HG17, "NCBI35");
        chromMapping.put(Reference.HG16, "NCBI34");
    }

    /**
     * Generates a canonical chrom ID.
     *
     * @param chrom chromosome
     * @return normalized chromosome value
     */
    private static Chromosome normalizeChromosome(String chrom) {
        // parse chrom value
        if (chrom != null) {
            String orig = chrom.toUpperCase();
            for (Chromosome c : Chromosome.values()) {
                if (orig.endsWith(c.toString())) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Converts 0-based position to 1-based position.
     *
     * @param pos 0-based position
     * @return 1-based position
     */
    public static Long normalizePosition(Long pos) {
        if (pos == null) {
            return null;
        }
        return ++pos;
    }

    /**
     * Generate a canonical allele string.
     *
     * @param allele denormalized allele
     * @return normalized allele
     */
    private static String normalizeAllele(String allele) {
        if (allele == null || allele.isEmpty()) {
            return null;
        }

        String res = allele.toUpperCase();
        if (res.equals("DEL") || res.equals("INS")) {
            return res.substring(0, 1);
        }
        if (Pattern.matches("([D,I])|([A,C,T,G]+)", res)) {
            return res;
        }

        return null;
    }

    /**
     * Generate a canonical genome representation (hg*).
     *
     * @param ref denormalized genome
     * @return normalized genome
     */
    private static Reference normalizeReference(String ref) {
        if (ref == null || ref.isEmpty()) {
            return null;
        }

        for (Reference s : chromMapping.keySet()) {
            if (s.toString().equalsIgnoreCase(ref)) {
                return s;
            }
        }
        for (Map.Entry<Reference, String> e : chromMapping.entrySet()) {
            if (e.getValue().equalsIgnoreCase(ref)) {
                return e.getKey();
            }
        }

        return null;
    }

    /**
     * Obtains a canonical query object.
     */
    public static CoreQuery getCoreQuery(String chromosome, Integer position_start, Integer position_end, String ref_allele,
                                         String alt_allele, String ref, String dataset, List<String> dbSNP,
                                         String type, Integer limit, Integer skip, String jwt,
                                         Boolean returnAnnotations,
                                         String samplesAsCSV,
                                         Boolean samplesConj,
                                         Boolean returnPheno) {
        Chromosome c = normalizeChromosome(chromosome);
        Reference r = normalizeReference(ref);
        String refAllele= normalizeAllele(ref_allele);
        String altAllele= normalizeAllele(alt_allele);
        DatasetID datasetId = DatasetID.fromString(dataset);
        VariantType variantType = VariantType.fromString(type);
        Integer lim = (limit == null || limit < 0 || limit > MAX_VARIANTS) ? MAX_VARIANTS : limit; // production limits for Beta
        Boolean conj = (samplesConj == null) ? false : samplesConj;
        Boolean retAnnot = (returnAnnotations == null) ? false : returnAnnotations;
        Boolean pheno = (returnPheno == null) ? false : returnPheno;
        List<String> samples = (samplesAsCSV != null) ? Arrays.asList(samplesAsCSV.split("\\s*,\\s*")) : null;

        return new CoreQuery(c, position_start, position_end, refAllele, altAllele, datasetId, dbSNP, variantType, r,
                lim, skip, jwt, conj, retAnnot, pheno, samples);
    }

    /**
     * Obtains a canonical query object.
     */
    public static CoreQuery getCoreQuery(String chromosome, Integer position, String alt_allele, String ref, String dataset, String type) {

        Chromosome c = normalizeChromosome(chromosome);
        Reference r = normalizeReference(ref);
        String altAllele= normalizeAllele(alt_allele);
        DatasetID datasetId = DatasetID.fromString(dataset);
        VariantType t = VariantType.fromString(type);

        return new CoreQuery(c, position, altAllele, datasetId, r, t);
    }
}