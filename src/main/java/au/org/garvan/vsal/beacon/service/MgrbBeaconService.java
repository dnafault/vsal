/*
 * Copyright 2017 Dmitry Degrave
 * Copyright 2017 Garvan Institute of Medical Research
 *
 */
/*
 * The MIT License
 *
 * Copyright 2014 DNAstack.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package au.org.garvan.vsal.beacon.service;

import au.org.garvan.vsal.beacon.entity.*;
import au.org.garvan.vsal.beacon.entity.Error;
import au.org.garvan.vsal.beacon.util.QueryUtils;
import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.core.util.CoreQueryUtils;

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
public class MgrbBeaconService implements BeaconService {

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
            Error errorResource = new Error("Incomplete Query", "Required parameters are missing.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        }

        Query q = QueryUtils.getQuery(chrom, pos, allele, ref, dataset == null ? "MGRB" : dataset);

        // required parameters are incorrect
        if (q.getReference() == null || q.getReference() != Reference.HG19) {
            Error errorResource = new Error("Incorrect Query", "Reference: \'" + ref + "\' is incorrect. Accepted Reference: HG19");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        } else if (q.getChromosome() == null) {
            Error errorResource = new Error("Incorrect Query", "Chromosome: \'" + chrom + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        } else if (q.getPosition() == null) {
            Error errorResource = new Error("Incorrect Query", "Position: \'" + pos + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        } else if (q.getAllele() == null) {
            Error errorResource = new Error("Incorrect Query", "Allele: \'" + allele + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
        }

        CoreQuery coreQuery = CoreQueryUtils.getCoreQuery(q.getChromosome().toString(),
                q.getPosition()+1, // convert 0-based beacon protocol into 1-based VCF position
                q.getAllele(), q.getReference().toString(), q.getDataset_id(), "SNV");

        try {
            int ac = 0;
            List<Allele> alleles = new LinkedList<>();
            for (CoreVariant cv : au.org.garvan.vsal.kudu.service.KuduCalls.variants(coreQuery)) {
                ac += cv.getAc();
                alleles.add(new Allele(cv.getA(), (double)cv.getAf()));
            }
            Response responseResource = new Response(ac>0, ac, alleles, "Beacon coordinates are 0-based!", null);
            return new BeaconResponse(beacon.getId(), q, responseResource);
        } catch (Exception e) {
            Error errorResource = new Error("VS Runtime exception", e.getMessage());
            Response responseResource = new Response(null, null, null, null, errorResource);
            return new BeaconResponse(beacon.getId(), q, responseResource);
        }
    }

    @Override
    public Beacon info() {
        return beacon;
    }

}
