package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VAPopulationFrequencies")
public class VAPopulationFrequencies {

    private String study;
    private String population;
    private String refAllele;
    private String altAllele;
    private float refAlleleFreq;
    private float altAlleleFreq;
    private float refHomGenotypeFreq;
    private float hetGenotypeFreq;
    private float altHomGenotypeFreq;

    public VAPopulationFrequencies() {
        // needed for JAXB
    }

    public VAPopulationFrequencies(String study, String population, String refAllele, String altAllele, float refAlleleFreq, float altAlleleFreq, float refHomGenotypeFreq, float hetGenotypeFreq, float altHomGenotypeFreq) {
        this.study = study;
        this.population = population;
        this.refAllele = refAllele;
        this.altAllele = altAllele;
        this.refAlleleFreq = refAlleleFreq;
        this.altAlleleFreq = altAlleleFreq;
        this.refHomGenotypeFreq = refHomGenotypeFreq;
        this.hetGenotypeFreq = hetGenotypeFreq;
        this.altHomGenotypeFreq = altHomGenotypeFreq;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
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

    public float getRefAlleleFreq() {
        return refAlleleFreq;
    }

    public void setRefAlleleFreq(float refAlleleFreq) {
        this.refAlleleFreq = refAlleleFreq;
    }

    public float getAltAlleleFreq() {
        return altAlleleFreq;
    }

    public void setAltAlleleFreq(float altAlleleFreq) {
        this.altAlleleFreq = altAlleleFreq;
    }

    public float getRefHomGenotypeFreq() {
        return refHomGenotypeFreq;
    }

    public void setRefHomGenotypeFreq(float refHomGenotypeFreq) {
        this.refHomGenotypeFreq = refHomGenotypeFreq;
    }

    public float getHetGenotypeFreq() {
        return hetGenotypeFreq;
    }

    public void setHetGenotypeFreq(float hetGenotypeFreq) {
        this.hetGenotypeFreq = hetGenotypeFreq;
    }

    public float getAltHomGenotypeFreq() {
        return altHomGenotypeFreq;
    }

    public void setAltHomGenotypeFreq(float altHomGenotypeFreq) {
        this.altHomGenotypeFreq = altHomGenotypeFreq;
    }
}
