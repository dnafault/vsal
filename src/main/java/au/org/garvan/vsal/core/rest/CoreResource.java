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
import java.util.ArrayList;
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
     * Either <b>chromosome</b> or <b>dbSNP</b> is required. <b>dataset</b> is always required. Everything else is optional.
     * <p>
     * E.g.
     * <pre><i>
     * find?genes=TP53&dataset=ASPREE&count=true
     * find?chromosome=1&dataset=ASPREE&glcStart=8&glcEnd=10&limit=100
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
     * @param samples list of samples ids
     * @param conj variant conjunction in samples
     * @param returnAnnotations return annotations in variants
     * @param gender gender, [female, male]
     * @param yobStart yob, start of range, inclusive
     * @param yobEnd yob, end of range, inclusive
     * @param sbpStart systolic blood pressure, mm Hg, start of range, inclusive
     * @param sbpEnd systolic blood pressure, mm Hg, end of range, inclusive
     * @param heightStart height in metres, start of range, inclusive
     * @param heightEnd height in metres, end of range, inclusive
     * @param weightStart weight in kg, start of range, inclusive
     * @param weightEnd weight in kg, end of range, inclusive
     * @param abdCircStart abdominal circumference in cm, start of range, inclusive
     * @param abdCircEnd abdominal circumference in cm, end of range, inclusive
     * @param glcStart glucose level in blood, mmol/L, start of range, inclusive
     * @param glcEnd glucose level in blood, mmol/L, end of range, inclusive
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

                              // Sample filtering
                              @QueryParam("samples") String samples, // csv
                              @QueryParam("conj") Boolean conj,

                              // Annotations
                              @QueryParam("returnAnnotations") Boolean returnAnnotations,

                              // Clinical parameters
                              @QueryParam("gender") String gender,
                              @QueryParam("yobStart") Integer yobStart,
                              @QueryParam("yobEnd") Integer yobEnd,
                              @QueryParam("sbpStart") Integer sbpStart,
                              @QueryParam("sbpEnd") Integer sbpEnd,
                              @QueryParam("heightStart") Float heightStart,
                              @QueryParam("heightEnd") Float heightEnd,
                              @QueryParam("weightStart") Float weightStart,
                              @QueryParam("weightEnd") Float weightEnd,
                              @QueryParam("abdCircStart") Integer abdCircStart,
                              @QueryParam("abdCircEnd") Integer abdCircEnd,
                              @QueryParam("glcStart") Float glcStart,
                              @QueryParam("glcEnd") Float glcEnd) {

        CoreQuery coreQuery = CoreQueryUtils.getCoreQuery(chromosome, positionStart, positionEnd, refAllele,
                altAllele, "hg19", dataset, dbSNP, type, limit, skip, returnAnnotations, samples, conj,
                // Clinical
                gender, yobStart, yobEnd, sbpStart, sbpEnd, heightStart, heightEnd, weightStart, weightEnd,
                abdCircStart, abdCircEnd, glcStart, glcEnd);

        return service.query(coreQuery);
    }
}
