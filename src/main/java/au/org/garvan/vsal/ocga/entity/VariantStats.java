package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement(name = "VariantStats")
public class VariantStats {

    private String refAllele;
    private String altAllele;

    private int refAlleleCount;
    private int altAlleleCount;

    private int missingAlleles;
    private int missingGenotypes;

    private float altAlleleFreq;
    private float refAlleleFreq;

    private float maf;
    private float mgf;

    private String mafAllele;
    private String mgfGenotype;

    private String variantType;

    private Map<String, Integer> genotypesCount;
    private Map<String, Float> genotypesFreq;

    public VariantStats() {
        // needed for JAXB
    }

    public VariantStats(String refAllele, String altAllele, int refAlleleCount, int altAlleleCount, int missingAlleles,
                        int missingGenotypes, float altAlleleFreq, float refAlleleFreq, float maf, float mgf, String mafAllele,
                        String mgfGenotype, String variantType, Map<String, Integer> genotypesCount, Map<String, Float> genotypesFreq) {
        this.refAllele = refAllele;
        this.altAllele = altAllele;
        this.refAlleleCount = refAlleleCount;
        this.altAlleleCount = altAlleleCount;
        this.missingAlleles = missingAlleles;
        this.missingGenotypes = missingGenotypes;
        this.altAlleleFreq = altAlleleFreq;
        this.refAlleleFreq = refAlleleFreq;
        this.maf = maf;
        this.mgf = mgf;
        this.mafAllele = mafAllele;
        this.mgfGenotype = mgfGenotype;
        this.variantType = variantType;
        this.genotypesCount = genotypesCount;
        this.genotypesFreq = genotypesFreq;
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

    public int getRefAlleleCount() {
        return refAlleleCount;
    }

    public void setRefAlleleCount(int refAlleleCount) {
        this.refAlleleCount = refAlleleCount;
    }

    public int getAltAlleleCount() {
        return altAlleleCount;
    }

    public void setAltAlleleCount(int altAlleleCount) {
        this.altAlleleCount = altAlleleCount;
    }

    public int getMissingAlleles() {
        return missingAlleles;
    }

    public void setMissingAlleles(int missingAlleles) {
        this.missingAlleles = missingAlleles;
    }

    public int getMissingGenotypes() {
        return missingGenotypes;
    }

    public void setMissingGenotypes(int missingGenotypes) {
        this.missingGenotypes = missingGenotypes;
    }

    public float getAltAlleleFreq() {
        return altAlleleFreq;
    }

    public void setAltAlleleFreq(float altAlleleFreq) {
        this.altAlleleFreq = altAlleleFreq;
    }

    public float getRefAlleleFreq() {
        return refAlleleFreq;
    }

    public void setRefAlleleFreq(float refAlleleFreq) {
        this.refAlleleFreq = refAlleleFreq;
    }

    public float getMaf() {
        return maf;
    }

    public void setMaf(float maf) {
        this.maf = maf;
    }

    public float getMgf() {
        return mgf;
    }

    public void setMgf(float mgf) {
        this.mgf = mgf;
    }

    public String getMafAllele() {
        return mafAllele;
    }

    public void setMafAllele(String mafAllele) {
        this.mafAllele = mafAllele;
    }

    public String getMgfGenotype() {
        return mgfGenotype;
    }

    public void setMgfGenotype(String mgfGenotype) {
        this.mgfGenotype = mgfGenotype;
    }

    public String getVariantType() {
        return variantType;
    }

    public void setVariantType(String variantType) {
        this.variantType = variantType;
    }

    public Map<String, Integer> getGenotypesCount() {
        return genotypesCount;
    }

    public void setGenotypesCount(Map<String, Integer> genotypesCount) {
        this.genotypesCount = genotypesCount;
    }

    public Map<String, Float> getGenotypesFreq() {
        return genotypesFreq;
    }

    public void setGenotypesFreq(Map<String, Float> genotypesFreq) {
        this.genotypesFreq = genotypesFreq;
    }
}
