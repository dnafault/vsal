package au.org.garvan.vsal.beacon.service;

import au.org.garvan.vsal.beacon.entity.*;
import au.org.garvan.vsal.beacon.rest.SSVSCalls;
import au.org.garvan.vsal.beacon.util.QueryUtils;
import au.org.garvan.vsal.core.entity.DatasetID;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * MGRB implementation of a beacon service.
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @version 1.0
 */
@RequestScoped
public class BeaconSSVS implements BeaconService {

    private Dataset dataset;
    private List<Dataset> datasets;
    private Query query;
    private List<Query> queries;
    private Beacon beacon;

    @PostConstruct
    public void init() {
        this.dataset = new Dataset("mgrb", "Garvan MGRB", "hg19", null, null);
        this.query = new Query("T", Chromosome.CHR1, 10491, Reference.HG19, "mgrb");
        this.queries = new ArrayList<>();
        this.queries.add(query);
        this.datasets = new ArrayList<>();
        this.datasets.add(dataset);
        this.beacon = new Beacon("Garvan Beacon ID", "Garvan Beacon", "Garvan Institute of Medical Research", "Garvan Beacon", "0.2",
                "https://sgc.garvan.org.au/vsal/beacon", "sgc@garvan.org.au", "", datasets, queries);
    }

    @Override
    public BeaconResponse query(String chrom, Integer pos, String allele, String ref, String dataset) {

        // required parameters are missing
        if (chrom == null || pos == null || allele == null || ref == null) {
            au.org.garvan.vsal.beacon.entity.Error errorResource = new au.org.garvan.vsal.beacon.entity.Error("Incomplete Query", "Required parameters are missing.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        }

        Query q = QueryUtils.getQuery(chrom, pos, allele, ref, dataset == null ? "MGRB" : dataset);

        // required parameters are incorrect
        if (q.getReference() == null || q.getReference() != Reference.HG19) {
            au.org.garvan.vsal.beacon.entity.Error errorResource = new au.org.garvan.vsal.beacon.entity.Error("Incorrect Query", "Reference: \'" + ref + "\' is incorrect. Accepted Reference: HG19");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        } else if (q.getChromosome() == null) {
            au.org.garvan.vsal.beacon.entity.Error errorResource = new au.org.garvan.vsal.beacon.entity.Error("Incorrect Query", "Chromosome: \'" + chrom + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        } else if (q.getPosition() == null) {
            au.org.garvan.vsal.beacon.entity.Error errorResource = new au.org.garvan.vsal.beacon.entity.Error("Incorrect Query", "Position: \'" + pos + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        } else if (q.getAllele() == null) {
            au.org.garvan.vsal.beacon.entity.Error errorResource = new au.org.garvan.vsal.beacon.entity.Error("Incorrect Query", "Allele: \'" + allele + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        } else if (DatasetID.fromString(q.getDatasetId()) == null) {
            au.org.garvan.vsal.beacon.entity.Error errorResource = new au.org.garvan.vsal.beacon.entity.Error("Incorrect Query", "Dataset: \'" + dataset + "\' is unknown.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        }

        try {
            q.setPosition(q.getPosition()+1);  // convert 0-based beacon protocol into 1-based VCF position
            BeaconResponseSSVS res = SSVSCalls.beacon(q);
            if (res == null)
                throw new RuntimeException("Response is null");
            q.setPosition(q.getPosition()-1);
            q.setDatasetId("MGRB"); // fixme: currently only MGRB is in prod
            List<Allele> alleles = new LinkedList<>();
            Boolean exists = false;
            Integer observed = 0;
            if (res.getVariants() != null && !res.getVariants().isEmpty()) {
                Variant v = res.getVariants().get(0);
                alleles.add(new Allele(q.getAllele(), v.getAf()));
                exists = v.getAf() > 0;
                observed = v.getAc();
            }
            Response responseResource = new Response(exists, observed, alleles, "Beacon coordinates are 0-based!", null);
            return new BeaconResponse(beacon.getId(), q, responseResource);
        } catch (Exception e) {
            au.org.garvan.vsal.beacon.entity.Error errorResource = new au.org.garvan.vsal.beacon.entity.Error("VS Runtime exception", e.getMessage());
            Response responseResource = new Response(null, null, null, null, errorResource);
            q.setPosition(q.getPosition()-1);
            return new BeaconResponse(beacon.getId(), q, responseResource);
        }
    }

    @Override
    public Beacon info() {
        return beacon;
    }
}