/*
 *
 *  * The MIT License (MIT)
 *  *
 *  * Copyright (c) 2019 Dmitry Degrave
 *  * Copyright (c) 2019 Garvan Institute of Medical Research
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package au.org.garvan.vsal.dnaerys.service;

import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.core.entity.VariantType;
import au.org.garvan.vsal.core.service.CoreService;
import au.org.garvan.vsal.dnaerys.entity.BMAinterface;

import org.dnaerys.AlleleBms;
import org.dnaerys.AlleleWithStats;
import org.dnaerys.analytics.BMA;
import org.dnaerys.variantstore.VariantsStats;
import org.dnaerys.variantstore.SamplesInRegions;
import org.dnaerys.variantstore.VariantsInRegions;

import java.util.*;
import java.util.AbstractMap;

public class DnaerysCalls {

    public static AbstractMap.SimpleImmutableEntry<Long,List<CoreVariant>> variantsInRegions(CoreQuery query) {
        BMA bma = BMAinterface.getBMA();
        String[] chr = new String[query.getChromosome().length];
        for (int i=0; i < chr.length; ++i) { chr[i] = query.getChromosome()[i].toString(); }
        Long start = System.nanoTime();
        Map<String,LinkedList<AlleleBms>> variants =
            VariantsInRegions.selectUniqueVariantsInRegions(bma, chr, query.getPositionStart(), query.getPositionEnd(),
                                                            query.getRefAllele(), query.getAltAllele());
        Long elapsedDbMs = (System.nanoTime() - start) / CoreService.NANO_TO_MILLI;
        return new AbstractMap.SimpleImmutableEntry(elapsedDbMs, toCoreVariants(variants));
    }

    public static AbstractMap.SimpleImmutableEntry<Long,List<CoreVariant>> variantsInVirtualCohort(CoreQuery query, List<String> samples) {
        BMA bma = BMAinterface.getBMA();
        String[] chr = new String[query.getChromosome().length];
        for (int i=0; i < chr.length; ++i) { chr[i] = query.getChromosome()[i].toString(); }
        Long start = System.nanoTime();
        Map<String,LinkedList<AlleleBms>> variants =
            VariantsInRegions.selectUniqueVariantsInRegionsInVirtualCohort(bma, chr, query.getPositionStart(),
                                           query.getPositionEnd(), query.getRefAllele(), query.getAltAllele(), samples);
        Long elapsedDbMs = (System.nanoTime() - start) / CoreService.NANO_TO_MILLI;
        return new AbstractMap.SimpleImmutableEntry(elapsedDbMs, toCoreVariants(variants));
    }

    public static AbstractMap.SimpleImmutableEntry<Long,List<CoreVariant>> variantsInVirtualCohortWithStats(CoreQuery query, List<String> samples) {
        BMA bma = BMAinterface.getBMA();
        String[] chr = new String[query.getChromosome().length];
        for (int i=0; i < chr.length; ++i) { chr[i] = query.getChromosome()[i].toString(); }
        Long start = System.nanoTime();
        Map<String,LinkedList<AlleleWithStats>> variants =
          VariantsStats.selectVariantsInRegionsInVCWithStats(bma, chr, query.getPositionStart(), query.getPositionEnd(),
                                  query.getRefAllele(), query.getAltAllele(), samples, query.getHwe(), query.getChi2());
        Long elapsedDbMs = (System.nanoTime() - start) / CoreService.NANO_TO_MILLI;
        return new AbstractMap.SimpleImmutableEntry(elapsedDbMs, toCoreVariantsWithStats(variants));
    }


    public static AbstractMap.SimpleImmutableEntry<Long,List<String>> selectSamplesByGT(CoreQuery query) {
        BMA bma = BMAinterface.getBMA();
        String[] chr = new String[query.getChromosome().length];
        for (int i=0; i < chr.length; ++i) { chr[i] = query.getChromosome()[i].toString(); }
        Long start = System.nanoTime();
        int[] samples = SamplesInRegions.selectSamplesInMultiRegions(bma, chr, query.getPositionStart(), query.getPositionEnd(),
                                                                     query.getRefAllele(), query.getAltAllele());
        Long elapsedDbMs = (System.nanoTime() - start) / CoreService.NANO_TO_MILLI;
        return new AbstractMap.SimpleImmutableEntry(elapsedDbMs, SamplesInRegions.samplesToNames(bma, samples));
    }

    private static List<CoreVariant> toCoreVariants(Map<String,LinkedList<AlleleBms>> variants) {
        List<CoreVariant> coreVariants = new LinkedList<>();
        for (Map.Entry<String,LinkedList<AlleleBms>> entry : variants.entrySet()) {
            Iterator<AlleleBms> it = entry.getValue().iterator();
            while (it.hasNext()) {
                AlleleBms a = it.next();
                VariantType t = VariantType.fromByte(a.vtype());
                String type = (t == null) ? null : t.toString();
                CoreVariant cv = new CoreVariant(entry.getKey(), a.start(), "", a.alt(), a.ref(), type, a.ac(), a.af(),
                                                 a.homc(), a.hetc(), null, null, null, null, null, null, null);
                coreVariants.add(cv);
            }
        }
        return coreVariants;
    }

    private static List<CoreVariant> toCoreVariantsWithStats(Map<String,LinkedList<AlleleWithStats>> variants) {
        List<CoreVariant> coreVariants = new LinkedList<>();
        for (Map.Entry<String,LinkedList<AlleleWithStats>> entry : variants.entrySet()) {
            Iterator<AlleleWithStats> it = entry.getValue().iterator();
            while (it.hasNext()) {
                AlleleWithStats alwt = it.next();
                AlleleBms a = alwt.allele();
                VariantType t = VariantType.fromByte(a.vtype());
                String type = (t == null) ? null : t.toString();
                CoreVariant cv = new CoreVariant(entry.getKey(), a.start(), "", a.alt(), a.ref(), type, a.ac(), a.af(),
                    a.homc(), a.hetc(), alwt.vac(), alwt.vaf(), alwt.vhomc(), alwt.vhetc(), alwt.phwe(), alwt.pchi2(), alwt.or());
                coreVariants.add(cv);
            }
        }
        return coreVariants;
    }
}