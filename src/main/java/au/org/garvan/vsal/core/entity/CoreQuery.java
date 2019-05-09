/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Dmitry Degrave
 * Copyright (c) 2019 Garvan Institute of Medical Research
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package au.org.garvan.vsal.core.entity;

import au.org.garvan.vsal.beacon.entity.Chromosome;
import au.org.garvan.vsal.beacon.entity.Reference;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CoreQuery")
public class CoreQuery {

    private Chromosome[] chromosome;
    private int[] positionStart;
    private int[] positionEnd;
    private String refAllele;
    private String altAllele;
    private DatasetID datasetId;
    private List<String> dbSNP;
    private VariantType type;
    private Reference reference;
    private Integer regions; // # of regions, i.e. == length of chromosome / positionStart / positionEnd
    private Integer limit;
    private Integer skip;
    private String jwt;
    private List<String> samples;
    private Boolean conj = false; // find variants that exist in all samples
    private Boolean selectSamplesByGT = false;
    private Boolean returnAnnotations = false;
    private Boolean pheno = false;
    private Boolean hwe = false;
    private Boolean chi2 = false;

    public CoreQuery() {
        // needed for JAXB
    }

    public CoreQuery(Chromosome[] chromosome, int[] positionStart, int[] positionEnd, String refAllele, String altAllele, DatasetID datasetId, VariantType type, Reference reference) {
        this.chromosome = chromosome;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.refAllele = refAllele;
        this.altAllele = altAllele;
        this.datasetId = datasetId;
        this.type = type;
        this.reference = reference;
    }

    public CoreQuery(Chromosome[] chromosome, int[] positionStart, int[] positionEnd, String refAllele, String altAllele, DatasetID datasetId, List<String> dbSNP, VariantType type, Reference reference, Integer regions, Integer limit, Integer skip, String jwt, List<String> samples, Boolean conj, Boolean selectSamplesByGT, Boolean returnAnnotations, Boolean pheno, Boolean hwe, Boolean chi2) {
        this.chromosome = chromosome;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.refAllele = refAllele;
        this.altAllele = altAllele;
        this.datasetId = datasetId;
        this.dbSNP = dbSNP;
        this.type = type;
        this.reference = reference;
        this.regions = regions;
        this.limit = limit;
        this.skip = skip;
        this.jwt = jwt;
        this.samples = samples;
        this.conj = conj;
        this.selectSamplesByGT = selectSamplesByGT;
        this.returnAnnotations = returnAnnotations;
        this.pheno = pheno;
        this.hwe = hwe;
        this.chi2 = chi2;
    }

    public Chromosome[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(Chromosome[] chromosome) {
        this.chromosome = chromosome;
    }

    public int[] getPositionStart() {
        return positionStart;
    }

    public void setPositionStart(int[] positionStart) {
        this.positionStart = positionStart;
    }

    public int[] getPositionEnd() {
        return positionEnd;
    }

    public void setPositionEnd(int[] positionEnd) {
        this.positionEnd = positionEnd;
    }

    public String getRefAllele() {
        return refAllele;
    }

    public void setRefAllele(String refAllele) {
        this.refAllele = refAllele;
    }

    public String getAltAllele() {
        return altAllele;
    }

    public void setAltAllele(String altAllele) {
        this.altAllele = altAllele;
    }

    public DatasetID getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(DatasetID datasetId) {
        this.datasetId = datasetId;
    }

    public List<String> getDbSNP() {
        return dbSNP;
    }

    public void setDbSNP(List<String> dbSNP) {
        this.dbSNP = dbSNP;
    }

    public VariantType getType() {
        return type;
    }

    public void setType(VariantType type) {
        this.type = type;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Integer getRegions() {
        return regions;
    }

    public void setRegions(Integer regions) {
        this.regions = regions;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public List<String> getSamples() {
        return samples;
    }

    public void setSamples(List<String> samples) {
        this.samples = samples;
    }

    public Boolean getConj() {
        return conj;
    }

    public void setConj(Boolean conj) {
        this.conj = conj;
    }

    public Boolean getSelectSamplesByGT() {
        return selectSamplesByGT;
    }

    public void setSelectSamplesByGT(Boolean selectSamplesByGT) {
        this.selectSamplesByGT = selectSamplesByGT;
    }

    public Boolean getReturnAnnotations() {
        return returnAnnotations;
    }

    public void setReturnAnnotations(Boolean returnAnnotations) {
        this.returnAnnotations = returnAnnotations;
    }

    public Boolean getPheno() {
        return pheno;
    }

    public void setPheno(Boolean pheno) {
        this.pheno = pheno;
    }

    public Boolean getHwe() {
        return hwe;
    }

    public void setHwe(Boolean hwe) {
        this.hwe = hwe;
    }

    public Boolean getChi2() {
        return chi2;
    }

    public void setChi2(Boolean chi2) {
        this.chi2 = chi2;
    }
}