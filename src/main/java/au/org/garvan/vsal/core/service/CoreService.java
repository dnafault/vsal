package au.org.garvan.vsal.core.service;

//import au.org.garvan.vsal.beacon.entity.*;
import au.org.garvan.vsal.beacon.entity.Error;
import au.org.garvan.vsal.beacon.rest.OcgaCalls;
//import au.org.garvan.vsal.beacon.util.QueryUtils;

import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreResponse;
//import au.org.garvan.vsal.core.util.CoreQueryUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import java.util.List;

/**
 * VSAL core service.
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @version 1.0
 */
@RequestScoped
public class CoreService {

    @PostConstruct
    public void init() {
    }

    public CoreResponse query(CoreQuery q) {

        // required parameters are missing
        if (q.getChromosome() == null) {
            Error errorResource = new Error("Incomplete Query", "Chromosome is missed.");
            return new CoreResponse(q, null, errorResource);
        }

        // call to ClinData
        List<String> samples=null;
        if ( q.getGender() != null || q.getYobStart() != null || q.getYobEnd() != null ||
             q.getSbpStart() != null || q.getSbpEnd() != null || q.getHeightStart() != null || q.getHeightEnd() != null ||
             q.getWeightStart() != null || q.getWeightEnd() != null || q.getAbdCircStart() != null ||
             q.getAbdCircEnd() != null || q.getGlcStart() != null || q.getGlcEnd() != null ) {
            //  TODO:   samples = // Rest call to ClinData
        }

        // call to OpenCGA
        OcgaCalls ocgac = new OcgaCalls();

        try {
            return new CoreResponse(q, ocgac.ocgaFindQuery(q, samples), null);
        } catch (Exception e) {
            Error errorResource = new Error("Runtime exception", e.getMessage());
            return new CoreResponse(q, null, errorResource);
        }
    }

}
