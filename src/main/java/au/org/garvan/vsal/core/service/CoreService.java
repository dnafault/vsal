package au.org.garvan.vsal.core.service;

import au.org.garvan.vsal.beacon.entity.Error;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.core.rest.OcgaCalls;
import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreResponse;
import au.org.garvan.vsal.core.rest.ClinDataCalls;

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

        final long start = System.nanoTime();

        // required parameters are missing
        if (q.getChromosome() == null && q.getGenes().isEmpty() && q.getDbSNP().isEmpty()) {
            Error errorResource = new Error("Incomplete Query", "Chromosome or Gene or dbSNP ID is required.");
            Long elapsed = (System.nanoTime() - start) / 1000000;
            return new CoreResponse(q, null, null, errorResource, elapsed);
        }

        // call to ClinData
        List<String> samples = null;
        if ( q.getGender() != null || q.getYobStart() != null || q.getYobEnd() != null ||
             q.getSbpStart() != null || q.getSbpEnd() != null || q.getHeightStart() != null || q.getHeightEnd() != null ||
             q.getWeightStart() != null || q.getWeightEnd() != null || q.getAbdCircStart() != null ||
             q.getAbdCircEnd() != null || q.getGlcStart() != null || q.getGlcEnd() != null ) {
            try {
                samples = new ClinDataCalls().getClinDataSamples(q);
            } catch (Exception e) {
                Error errorResource = new Error("ClinData Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / 1000000;
                return new CoreResponse(q, null, null, errorResource, elapsed);
            }
        }

        // call to OpenCGA
        OcgaCalls ocgac = new OcgaCalls();

        try {
            List<Integer> total = null;
            if (q.getCount() != null && q.getCount()) {
                total =  ocgac.CountVariants(q, samples);
            }
            List<CoreVariant> vars = ocgac.ocgaFindVariants(q, samples);
            Long elapsed = (System.nanoTime() - start) / 1000000;
            return new CoreResponse(q, vars, total, null, elapsed);
        } catch (Exception e) {
            Error errorResource = new Error("VS Runtime Exception", e.getMessage());
            Long elapsed = (System.nanoTime() - start) / 1000000;
            return new CoreResponse(q, null, null, errorResource, elapsed);
        }
    }
}