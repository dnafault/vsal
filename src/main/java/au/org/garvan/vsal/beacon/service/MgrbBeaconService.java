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

import au.org.garvan.vsal.beacon.entity.Error;
import au.org.garvan.vsal.beacon.util.OcgaBeaconCalls;
import au.org.garvan.vsal.beacon.util.QueryUtils;
import au.org.garvan.vsal.beacon.entity.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * MGRB implementation of a beacon service.
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @author Miroslav Cupak (mirocupak@gmail.com)
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
        this.dataset = new Dataset("mgrb", "MGRB", "hg19", null, null);
        this.query = new Query("C/G", Chromosome.CHR1, 52065L, Reference.HG19, "mgrb");
        this.queries = new ArrayList<>();
        this.queries.add(query);
        this.datasets = new ArrayList<>();
        this.datasets.add(dataset);
        this.beacon = new Beacon("mgrb", "MGRB Beacon", "Garvan Institute of Medical Research", "MGRB Beacon", "0.2",
                "https://www.garvan.org.au", "beacon@garvan.org.au", "", datasets, queries);
    }

    @Override
    public BeaconResponse query(String chrom, Long pos, String allele, String ref, String dataset) {

        // required parameters are missing
        if (chrom == null || pos == null || allele == null || ref == null) {
            Error errorResource = new Error("Incomplete Query", "Required parameters are missing.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            BeaconResponse response = new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
            return response;
        }

        Query q = QueryUtils.getQuery(chrom, pos, allele, ref, dataset);

        // required parameters are incorrect
        if (q.getReference() == null || q.getReference() != Reference.HG19) {
            Error errorResource = new Error("Incorrect Query", "Reference: \'" + ref + "\' is incorrect. Accepted Reference: HG19");
            Response responseResource = new Response(null, null, null, null, errorResource);
            BeaconResponse response = new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
            return response;
        } else if (q.getChromosome() == null) {
            Error errorResource = new Error("Incorrect Query", "Chromosome: \'" + chrom + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            BeaconResponse response = new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
            return response;
        } else if (q.getPosition() == null) {
            Error errorResource = new Error("Incorrect Query", "Position: \'" + pos + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            BeaconResponse response = new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
            return response;
        } else if (q.getAllele() == null) {
            Error errorResource = new Error("Incorrect Query", "Allele: \'" + allele + "\' is incorrect.");
            Response responseResource = new Response(null, null, null, null, errorResource);
            BeaconResponse response = new BeaconResponse(beacon.getId(), QueryUtils.getQuery(chrom, pos, allele, ref, dataset), responseResource);
            return response;
        }

        // call to OpenCGA
        int n;
        OcgaBeaconCalls ocgac = new OcgaBeaconCalls();

        try {
            n = ocgac.ocgaBeaconQuery(q);
        } catch (Exception e) {
            Error errorResource = new Error("Runtime exception", e.getMessage());
            Response responseResource = new Response(null, null, null, null, errorResource);
            BeaconResponse response = new BeaconResponse(beacon.getId(), q, responseResource);
            return response;
        }

        Response responseResource = new Response(n>0?true:false, n, null, "bla!", null);
        BeaconResponse response = new BeaconResponse(beacon.getId(), q, responseResource);
        return response;
    }

    @Override
    public Beacon info() {
        return beacon;
    }

}
