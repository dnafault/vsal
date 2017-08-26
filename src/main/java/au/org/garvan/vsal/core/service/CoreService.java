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

    private static final int NANO_TO_MILLI = 1000000;

    @PostConstruct
    public void init() {
    }

    public CoreResponse query(CoreQuery q) {

        final long start = System.nanoTime();

        if (q.getChromosome() == null && q.getDbSNP().isEmpty()) {
            Error errorResource = new Error("Incomplete Query", "Chromosome or dbSNP ID is required.");
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
        CoreResponse res = null;

        if ( q.getGender() != null || q.getYobStart() != null || q.getYobEnd() != null ||
             q.getSbpStart() != null || q.getSbpEnd() != null || q.getHeightStart() != null || q.getHeightEnd() != null ||
             q.getWeightStart() != null || q.getWeightEnd() != null || q.getAbdCircStart() != null ||
             q.getAbdCircEnd() != null || q.getGlcStart() != null || q.getGlcEnd() != null ) {
            try {
                samples = new ClinDataCalls().getClinDataSamples(q);
                if (samples == null || samples.isEmpty()) {
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    res = new CoreResponse(q, elapsed, 0, null, 0, null, "No samples selected");
                } else {
                    List<CoreVariant> vars = au.org.garvan.vsal.kudu.service.KuduCalls.variantsInVirtualCohort(q, samples);
                    Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                    res = new CoreResponse(q, elapsed, samples.size(), vars, vars.size(), null, null);
                }
            } catch (Exception e) {
                Error errorResource = new Error("VS Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, errorResource);
            }
        } else {
            try {
                List<CoreVariant> vars = au.org.garvan.vsal.kudu.service.KuduCalls.variants(q);
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, 0, vars, vars.size(), null, null);
            } catch (Exception e) {
                Error errorResource = new Error("VS Runtime Exception", e.getMessage());
                Long elapsed = (System.nanoTime() - start) / NANO_TO_MILLI;
                res = new CoreResponse(q, elapsed, errorResource);
            }
        }

        return res;
    }
}