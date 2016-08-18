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
    public CoreResponse query(@QueryParam("chrom")   String chromosome,
                              @QueryParam("pos_start") Integer position_start,
                              @QueryParam("pos_end")   Integer position_end,
                              @QueryParam("ref_allele")  String ref_allele,
                              @QueryParam("alt_allele")  String alt_allele,
                              @QueryParam("dataset") String dataset,
                              //TODO: -> List<String> genes/rsIDs
                              @QueryParam("genes")  String genes,
                              @QueryParam("rsIDs")  String rsIDs,
                              @QueryParam("type")  String type,
                              @QueryParam("limit") Integer limit,
                              @QueryParam("skip") Integer skip,

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

        List<String> genesList = new ArrayList<>();
        List<String> rsIDsList = new ArrayList<>();
        genesList.add(genes);
        rsIDsList.add(rsIDs);

        CoreQuery coreQuery = CoreQueryUtils.getCoreQuery(chromosome, position_start, position_end, ref_allele,
                alt_allele, "hg19", dataset, genesList, rsIDsList, type, limit, skip,
                gender, yobStart, yobEnd, sbpStart, sbpEnd, heightStart, heightEnd, weightStart, weightEnd,
                abdCircStart, abdCircEnd, glcStart, glcEnd);

        return service.query(coreQuery);
    }
}
