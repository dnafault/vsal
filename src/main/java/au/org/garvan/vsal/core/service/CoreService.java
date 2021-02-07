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

package au.org.garvan.vsal.core.service;

import au.org.garvan.vsal.beacon.entity.Error;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreResponse;
import au.org.garvan.vsal.core.util.CoreJWT;
import au.org.garvan.vsal.core.util.ReadConfig;
import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Properties;

/**
 * VSAL core service.
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @version 1.0
 */
@RequestScoped
public class CoreService {

    public static final int NANO_TO_MILLI = 1000000;

    @PostConstruct
    public void init() {
    }

    public CoreResponse query(CoreQuery q) {

        final long start = System.nanoTime();

        if (q.getDatasetId() == null) {
            Error errorResource = new Error("Incomplete Query", "A valid dataset is required");
            Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
            return new CoreResponse(q, elapsed, errorResource);
        }

        if (q.getChromosome() == null && q.getDbSNP().isEmpty() && !q.getPheno() && !q.getGenelist()) {
            Error errorResource = new Error("Incomplete Query", "Chromosome or dbSNP ID or pheno is required");
            Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
            return new CoreResponse(q, elapsed, errorResource);
        }

        if (q.getChromosome() != null) {
            if (q.getPositionStart() == null || q.getPositionEnd() == null ||
                    q.getChromosome().length != q.getPositionStart().length ||
                    q.getChromosome().length != q.getPositionEnd().length) {
                Error errorResource = new Error("Malformed Query", "Regions must have equal # chr, start & end");
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                return new CoreResponse(q, elapsed, errorResource);
            }
        }

        if (q.getChromosome() != null) {
            for (int i = 0; i < q.getRegions(); ++i) {
                if (q.getPositionStart()[i] < 0) {
                    String errDesc = "Start position of region " + (i + 1) + " should be >= 0, but it's " + q.getPositionStart()[i];
                    Error errorResource = new Error("Malformed Query", errDesc);
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    return new CoreResponse(q, elapsed, errorResource);
                }

                if (q.getPositionEnd()[i] < 0) {
                    String errDesc = "End position of region " + (i + 1) + " should be >= 0, but it's " + q.getPositionEnd()[i];
                    Error errorResource = new Error("Malformed Query", errDesc);
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    return new CoreResponse(q, elapsed, errorResource);
                }

                if (q.getPositionEnd()[i] < q.getPositionStart()[i]) {
                    String errDesc = "End position " + q.getPositionEnd()[i] + "of region " + (i + 1) +
                            " should be >= start position " + q.getPositionStart()[i];
                    Error errorResource = new Error("Malformed Query", errDesc);
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    return new CoreResponse(q, elapsed, errorResource);
                }
            }
        }

        List<String> samples;
        CoreResponse res;

        if (q.getPheno()) {
            try {
                if (q.getJwt() == null && !q.getDatasetId().toString().equalsIgnoreCase("demo")) {
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    Error errorResource = new Error("JWT verification failed", "JWT is required for phenotypes");
                    res = new CoreResponse(q, elapsed, errorResource);
                } else {
                    if (!q.getDatasetId().toString().equalsIgnoreCase("demo"))
                        CoreJWT.verifyJWT(q.getJwt(), q.getDatasetId().toString().toLowerCase() + "/pheno");
                    Properties p = ReadConfig.getProp();
                    String path = p.getProperty("phenoPath") + "/" + q.getDatasetId().toString().toLowerCase() + ".pheno.json";
                    String pheno = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    res = new CoreResponse(q, elapsed, 0l, 0, null, 0, null, pheno, null, null, null);
                }
            } catch (UnsupportedEncodingException | JWTVerificationException e) {
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                Error errorResource = new Error("JWT verification failed", e.getMessage());
                res = new CoreResponse(q, elapsed, errorResource);
            } catch (Exception e) {
                Error errorResource = new Error("VS Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, errorResource);
            }
        } else if (q.getGenelist()) {
            try {
                if (q.getJwt() == null) {
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    Error errorResource = new Error("JWT verification failed", "JWT is required for gene list");
                    res = new CoreResponse(q, elapsed, errorResource);
                } else {
                    String genelist = null; // no gene list for demo
                    if (!q.getDatasetId().toString().equalsIgnoreCase("demo")) {
                        CoreJWT.verifyJWT(q.getJwt(), q.getDatasetId().toString().toLowerCase() + "/pheno"); // same rights as for pheno data
                        Properties p = ReadConfig.getProp();
                        String path = p.getProperty("phenoPath") + "/genelist.json";
                        genelist = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
                    }
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    res = new CoreResponse(q, elapsed, 0l, 0, null, 0, null, null, genelist, null, null);
                }
            } catch (UnsupportedEncodingException | JWTVerificationException e) {
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                Error errorResource = new Error("JWT verification failed", e.getMessage());
                res = new CoreResponse(q, elapsed, errorResource);
            } catch (Exception e) {
                Error errorResource = new Error("VS Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, errorResource);
            }
        } else if (q.getSelectSamplesByGT()) {
            // select samples
            try {
                if (q.getJwt() == null) {
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    Error errorResource = new Error("JWT verification failed", "JWT is required for samples selection");
                    res = new CoreResponse(q, elapsed, errorResource);
                } else {
                    if (!q.getDatasetId().toString().equalsIgnoreCase("demo") &&
                        !q.getDatasetId().toString().equalsIgnoreCase("trio"))
                        CoreJWT.verifyJWT(q.getJwt(), q.getDatasetId().toString().toLowerCase() + "/gt");
                    AbstractMap.SimpleImmutableEntry<Long,List<String>> sampleIDs = au.org.garvan.vsal.kudu.service.AsyncKuduCalls.selectSamplesByGT(q);

                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    res = new CoreResponse(q, elapsed, sampleIDs.getKey(), sampleIDs.getValue().size(), null, 0, sampleIDs.getValue(), null, null, null, null);
                }
            } catch (UnsupportedEncodingException | JWTVerificationException e) {
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                Error errorResource = new Error("JWT verification failed", e.getMessage());
                res = new CoreResponse(q, elapsed, errorResource);
            } catch (Exception e) {
                Error errorResource = new Error("VS Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, errorResource);
            }
        } else if (q.getSamples() != null) {
            // select variants in virtual cohort
            try {
                if (q.getJwt() == null) {
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    Error errorResource = new Error("JWT verification failed", "JWT is required for samples filtering");
                    res = new CoreResponse(q, elapsed, errorResource);
                } else {
                    if (!q.getDatasetId().toString().equalsIgnoreCase("demo") &&
                        !q.getDatasetId().toString().equalsIgnoreCase("trio"))
                        CoreJWT.verifyJWT(q.getJwt(), q.getDatasetId().toString().toLowerCase() + "/gt");
                    samples = q.getSamples();
                    if (samples.isEmpty()) {
                        Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                        res = new CoreResponse(q, elapsed, 0l, 0, null, 0, null, null, null, null, "No samples selected");
                    } else {
                        AbstractMap.SimpleImmutableEntry<Long,List<CoreVariant>> vars =
                                au.org.garvan.vsal.kudu.service.AsyncKuduCalls.variantsInVirtualCohort(q, samples);
                        Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                        res = new CoreResponse(q, elapsed, vars.getKey(), samples.size(), vars.getValue(), vars.getValue().size(), null, null, null, null, null);
                    }
                }
            } catch (UnsupportedEncodingException | JWTVerificationException e) {
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                Error errorResource = new Error("JWT verification failed", e.getMessage());
                res = new CoreResponse(q, elapsed, errorResource);
            } catch (Exception e) {
                Error errorResource = new Error("VS Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, errorResource);
            }
        } else {
            // select variants in regions
            try {
                AbstractMap.SimpleImmutableEntry<Long,List<CoreVariant>> vars = au.org.garvan.vsal.kudu.service.KuduCalls.variants(q);
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, vars.getKey(), 0, vars.getValue(), vars.getValue().size(), null, null, null, null, null);
            } catch (Exception e) {
                Error errorResource = new Error("VS Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, errorResource);
            }
        }

        return res;
    }
}