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

package au.org.garvan.vsal.core.rest;

import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreResponse;
import au.org.garvan.vsal.core.entity.POSTParamsJaxBean;
import au.org.garvan.vsal.core.service.CoreService;
import au.org.garvan.vsal.core.util.CoreQueryUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * VSAL Core rest resource.
 *
 * @author Dmitry Degrave
 * @version 1.0
 */
@Path("/find")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
public class CoreResource {

    @Inject
    private CoreService service;

    /**
     * VSAL REST end point: /find
     * <p>
     * Either <b>chromosome</b> or <b>dbSNP</b> or <b>pheno</b> is required. <b>dataset</b> is always required. Everything else is optional.
     * <p>
     * E.g.
     * <pre><i>
     * find?chromosome=1&dataset=ASPREE&glcStart=8&glcEnd=10&limit=100
     * find?pheno=true&dataset=MGRB
     * </i></pre>
     *
     * @param chromosome  chromosome, [1-22, X, Y, MT] or [Chr1-Chr22, ChrX, ChrY, ChrMT] (as csv for multiple regions)
     * @param positionStart start of a region in chromosome, inclusive (as csv for multiple regions)
     * @param positionEnd end of a region in chromosome, inclusive (as csv for multiple regions)
     * @param refAllele reference allele
     * @param altAllele alternate allele
     * @param dataset dataset
     * @param dbSNP list of dbSNP ids
     * @param type type, [SNV, MNV, INDEL, SV, CNV]
     * @param limit limit for # of variants in response
     * @param skip # of skipped variants
     * @param samples list of samples ids, csv
     * @param conj variant conjunction in samples, boolean
     * @param selectSamplesByGT return samples instead of variants, boolean
     * @param returnAnnotations return annotations in variants, boolean
     * @param pheno return phenotypes, boolean
     * @param hwe return p-value for Chi-squared test for deviation from Hardy-Weinberg Equilibrium, boolean
     * @param chi2 return Pearson's chi-squared test p-value and odds ratio, boolean
     * @return {@link CoreResponse}
     */
    @GET
    public CoreResponse query(@QueryParam("chromosome") String chromosome,
                              @QueryParam("positionStart") String positionStart,
                              @QueryParam("positionEnd") String positionEnd,
                              @QueryParam("refAllele") String refAllele,
                              @QueryParam("altAllele") String altAllele,
                              @QueryParam("dataset") String dataset,
                              @QueryParam("dbSNP") List<String> dbSNP,
                              @QueryParam("type") String type,
                              @QueryParam("limit") Integer limit,
                              @QueryParam("skip") Integer skip,
                              @QueryParam("jwt") String jwt,
                              @QueryParam("samples") String samples,
                              @QueryParam("conj") Boolean conj,
                              @QueryParam("selectSamplesByGT") Boolean selectSamplesByGT,
                              @QueryParam("returnAnnotations") Boolean returnAnnotations,
                              @QueryParam("pheno") Boolean pheno,
                              @QueryParam("hwe") Boolean hwe,
                              @QueryParam("chi2") Boolean chi2,
                              @Context HttpHeaders headers) {

        List<String> authzScheme = headers.getRequestHeader("Authorization");
        String authz = (authzScheme != null && !authzScheme.isEmpty()) ? authzScheme.get(0) : null;
        CoreQuery coreQuery = CoreQueryUtils.getCoreQuery(chromosome, positionStart, positionEnd, refAllele, altAllele,
                "hg19", dataset, dbSNP, type, limit, skip, jwt, samples, conj, selectSamplesByGT, returnAnnotations,
                pheno, hwe, chi2, authz);

        return service.query(coreQuery);
    }

    /**
     * VSAL REST end point: /find
     * <p>
     * Either <b>chromosome</b> or <b>dbSNP</b> or <b>pheno</b> is required. <b>dataset</b> is always required. Everything else is optional.
     * <p>
     * @return {@link CoreResponse}
     */
    @POST
    @Consumes({"application/json"})
    public CoreResponse queryPost(POSTParamsJaxBean params, @Context HttpHeaders headers) {
        List<String> authzScheme = headers.getRequestHeader("Authorization");
        String authz = (authzScheme != null && !authzScheme.isEmpty()) ? authzScheme.get(0) : null;
        CoreQuery coreQuery = CoreQueryUtils.getCoreQuery(params.chromosome, params.positionStart, params.positionEnd,
                params.refAllele, params.altAllele, "hg19", params.dataset, params.dbSNP, params.type, params.limit,
                params.skip, params.jwt, params.samples, params.conj, params.selectSamplesByGT, params.returnAnnotations,
                params.pheno, params.hwe, params.chi2, authz);

        return service.query(coreQuery);
    }
}