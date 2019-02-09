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
import au.org.garvan.vsal.core.service.CoreService;
import au.org.garvan.vsal.core.util.CoreQueryUtils;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
     * @param chromosome  chromosome, [1-22, X, Y, MT] or [Chr1-Chr22, ChrX, ChrY, ChrMT]
     * @param positionStart start of a region in chromosome, inclusive
     * @param positionEnd end of a region in chromosome, inclusive
     * @param refAllele reference allele
     * @param altAllele alternate allele
     * @param dataset dataset
     * @param dbSNP list of dbSNP ids
     * @param type type, [SNV, MNV, INDEL, SV, CNV]
     * @param limit limit for # of variants in response
     * @param skip # of skipped variants
     * @param jwt JWT token
     * @param samples list of samples ids
     * @param conj variant conjunction in samples, boolean
     * @param selectSamplesByGT return samples instead of variants, boolean
     * @param returnAnnotations return annotations in variants, boolean
     * @param pheno return phenotypes, boolean
     * @return {@link CoreResponse}
     */
    @GET
    public CoreResponse query(@QueryParam("chromosome") String chromosome,
                              @QueryParam("positionStart") Integer positionStart,
                              @QueryParam("positionEnd") Integer positionEnd,
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
                              @QueryParam("pheno") Boolean pheno) {

        CoreQuery coreQuery = CoreQueryUtils.getCoreQuery(chromosome, positionStart, positionEnd, refAllele, altAllele,
                "hg19", dataset, dbSNP, type, limit, skip, jwt, samples, conj, selectSamplesByGT, returnAnnotations, pheno);

        return service.query(coreQuery);
    }
}