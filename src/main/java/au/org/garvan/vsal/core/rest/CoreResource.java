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

    @GET
    public CoreResponse query(@QueryParam("chromosome") String chromosome,
                              @QueryParam("positionStart") Integer positionStart,
                              @QueryParam("positionEnd") Integer positionEnd,
                              @QueryParam("refAllele") String refAllele,
                              @QueryParam("altAllele") String altAllele,
                              @QueryParam("dataset") String dataset,
                              @QueryParam("genes") List<String> genes,
                              @QueryParam("dbSNP") List<String> dbSNP,
                              @QueryParam("type") String type,
                              @QueryParam("limit") Integer limit,
                              @QueryParam("skip") Integer skip,

                              // Stat
                              @QueryParam("maf") String maf, // [<|>|<=|>=]{number}
                              @QueryParam("populationMaf") String popMaf, // [<|>|<=|>=]{number}
                              @QueryParam("populationAltFrequency") String popAltFrq, // [<|>|<=|>=]{number}
                              @QueryParam("populationRefFrequency") String popRefFrq, // [<|>|<=|>=]{number}

                              // Annotations
                              @QueryParam("annotCT") String annotCT,
                              @QueryParam("annotHPO") String annotHPO,
                              @QueryParam("annotGO") String annotGO,
                              @QueryParam("annotXref") String annotXref,
                              @QueryParam("annotBiotype") String annotBiotype,

                              @QueryParam("polyphen") String polyphen, // [<|>|<=|>=]{number} or [~=|=|]{description} e.g. <=0.9 , =benign
                              @QueryParam("sift") String sift, // [<|>|<=|>=]{number} or [~=|=|]{description} e.g. >0.1 , ~=tolerant
                              @QueryParam("conservationScore") String conservationScore, // {conservation_score}[<|>|<=|>=]{number} e.g. phastCons>0.5,phylop<0.1,gerp>0.1

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
                altAllele, "hg19", dataset, genes, dbSNP, type, limit, skip, maf, popMaf, popAltFrq, popRefFrq,
                annotCT, annotHPO, annotGO, annotXref, annotBiotype, polyphen, sift, conservationScore,
                // Clinical
                gender, yobStart, yobEnd, sbpStart, sbpEnd, heightStart, heightEnd, weightStart, weightEnd,
                abdCircStart, abdCircEnd, glcStart, glcEnd);

        return service.query(coreQuery);
    }
}
