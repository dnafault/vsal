package au.org.garvan.vsal.core.service;

import au.org.garvan.vsal.beacon.entity.Error;
import au.org.garvan.vsal.core.entity.CoreVariant;
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

        if (q.getChromosome() == null && q.getDbSNP().isEmpty()) {
            Error errorResource = new Error("Incomplete Query", "Chromosome or dbSNP ID is required.");
            Long elapsed = (System.nanoTime() - start) / 1000000;
            return new CoreResponse(q, elapsed, errorResource);
        }

        // call to ClinData
        List<String> samples;
        if ( q.getGender() != null || q.getYobStart() != null || q.getYobEnd() != null ||
             q.getSbpStart() != null || q.getSbpEnd() != null || q.getHeightStart() != null || q.getHeightEnd() != null ||
             q.getWeightStart() != null || q.getWeightEnd() != null || q.getAbdCircStart() != null ||
             q.getAbdCircEnd() != null || q.getGlcStart() != null || q.getGlcEnd() != null ) {
            try {
                samples = new ClinDataCalls().getClinDataSamples(q);
                if (samples == null) {
                    Long elapsed = (System.nanoTime() - start) / 1000000;
                    return new CoreResponse(q, elapsed, null, 0, null, "No samples selected");
                }
            } catch (Exception e) {
                Error errorResource = new Error("ClinData Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / 1000000;
                return new CoreResponse(q, elapsed, errorResource);
            }
        }

        try {
            List<CoreVariant> vars = au.org.garvan.vsal.kudu.service.KuduCalls.variants(q);
            Long elapsed = (System.nanoTime() - start) / 1000000;
            return new CoreResponse(q, elapsed, vars, vars.size(), null, null);
        } catch (Exception e) {
            Error errorResource = new Error("VS Runtime Exception", e.getMessage());
            Long elapsed = (System.nanoTime() - start) / 1000000;
            return new CoreResponse(q, elapsed, errorResource);
        }
    }
}