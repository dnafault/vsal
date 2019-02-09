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

    private static final int NANO_TO_MILLI = 1000000;

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

        if (q.getChromosome() == null && q.getDbSNP().isEmpty() && !q.getPheno()) {
            Error errorResource = new Error("Incomplete Query", "Chromosome or dbSNP ID or pheno is required");
            Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
            return new CoreResponse(q, elapsed, errorResource);
        }

        if (q.getPositionStart() != null && q.getPositionStart() < 0 ) {
            Error errorResource = new Error("Malformed Query", "Start position of a region should be >= 0");
            Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
            return new CoreResponse(q, elapsed, errorResource);
        }

        if (q.getPositionEnd() != null && q.getPositionEnd() < 0 ) {
            Error errorResource = new Error("Malformed Query", "End position of a region should be >= 0");
            Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
            return new CoreResponse(q, elapsed, errorResource);
        }

        if (q.getPositionStart() != null && q.getPositionEnd() != null && q.getPositionEnd() < q.getPositionStart()) {
            Error errorResource = new Error("Malformed Query", "End position of a region should be >= start position");
            Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
            return new CoreResponse(q, elapsed, errorResource);
        }

        List<String> samples;
        CoreResponse res;

        if (q.getPheno()) {
            try {
                if (q.getJwt() == null) {
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    Error errorResource = new Error("JWT verification failed", "JWT is required for phenotypes");
                    res = new CoreResponse(q, elapsed, errorResource);
                } else {
                    CoreJWT.verifyJWT(q.getJwt(), q.getDatasetId().toString().toLowerCase() + "/pheno");
                    Properties p = ReadConfig.getProp();
                    String path = p.getProperty("phenoPath") + "/" + q.getDatasetId().toString().toLowerCase() + ".pheno.json";
                    String pheno = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    res = new CoreResponse(q, elapsed, 0, null, 0, null, pheno, null, null);
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
            try {
                if (q.getJwt() == null) {
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    Error errorResource = new Error("JWT verification failed", "JWT is required for samples selection");
                    res = new CoreResponse(q, elapsed, errorResource);
                } else {
                    CoreJWT.verifyJWT(q.getJwt(), q.getDatasetId().toString().toLowerCase() + "/gt");
                    List<String> sampleIDs = au.org.garvan.vsal.kudu.service.AsyncKuduCalls.selectSamplesByGT(q);
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    res = new CoreResponse(q, elapsed, sampleIDs.size(), null, 0, sampleIDs, null, null, null);
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
            try {
                if (q.getJwt() == null) {
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    Error errorResource = new Error("JWT verification failed", "JWT is required for samples filtering");
                    res = new CoreResponse(q, elapsed, errorResource);
                } else {
                    CoreJWT.verifyJWT(q.getJwt(), q.getDatasetId().toString().toLowerCase() + "/gt");
                    samples = q.getSamples();
                    if (samples.isEmpty()) {
                        Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                        res = new CoreResponse(q, elapsed, 0, null, 0, null, null, null, "No samples selected");
                    } else {
                        List<CoreVariant> vars = au.org.garvan.vsal.kudu.service.AsyncKuduCalls.variantsInVirtualCohort(q, samples);
                        Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                        res = new CoreResponse(q, elapsed, samples.size(), vars, vars.size(), null, null, null, null);
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
            try {
                List<CoreVariant> vars = au.org.garvan.vsal.kudu.service.KuduCalls.variants(q);
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, 0, vars, vars.size(), null, null, null, null);
            } catch (Exception e) {
                Error errorResource = new Error("VS Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, errorResource);
            }
        }

        return res;
    }
}