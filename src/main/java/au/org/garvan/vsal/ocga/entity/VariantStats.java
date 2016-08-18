package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VariantStats")
public class VariantStats {

    private String chromosome;
    private int position;
    private String referenceAllele;
    private String alternateAlleles;
    private String variantType;
    private float maf;
    private float mgf;
    private String mafAllele;
    private String mgfGenotype;
    private int numMissingAlleles;
    private int numMissingGenotypes;
    private int numMendelErrors;
    private float percentCasesDominant;
    private float percentControlsDominant;
    private float percentCasesRecessive;
    private float percentControlsRecessive;

    public VariantStats() {
        // needed for JAXB
    }

    public VariantStats(String chromosome, int position, String referenceAllele, String alternateAlleles, String variantType, float maf, float mgf, String mafAllele, String mgfGenotype, int numMissingAlleles, int numMissingGenotypes, int numMendelErrors, float percentCasesDominant, float percentControlsDominant, float percentCasesRecessive, float percentControlsRecessive) {
        this.chromosome = chromosome;
        this.position = position;
        this.referenceAllele = referenceAllele;
        this.alternateAlleles = alternateAlleles;
        this.variantType = variantType;
        this.maf = maf;
        this.mgf = mgf;
        this.mafAllele = mafAllele;
        this.mgfGenotype = mgfGenotype;
        this.numMissingAlleles = numMissingAlleles;
        this.numMissingGenotypes = numMissingGenotypes;
        this.numMendelErrors = numMendelErrors;
        this.percentCasesDominant = percentCasesDominant;
        this.percentControlsDominant = percentControlsDominant;
        this.percentCasesRecessive = percentCasesRecessive;
        this.percentControlsRecessive = percentControlsRecessive;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getReferenceAllele() {
        return referenceAllele;
    }

    public void setReferenceAllele(String referenceAllele) {
        this.referenceAllele = referenceAllele;
    }

    public String getAlternateAlleles() {
        return alternateAlleles;
    }

    public void setAlternateAlleles(String alternateAlleles) {
        this.alternateAlleles = alternateAlleles;
    }

    public String getVariantType() {
        return variantType;
    }

    public void setVariantType(String variantType) {
        this.variantType = variantType;
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

    public int getNumMissingAlleles() {
        return numMissingAlleles;
    }

    public void setNumMissingAlleles(int numMissingAlleles) {
        this.numMissingAlleles = numMissingAlleles;
    }

    public int getNumMissingGenotypes() {
        return numMissingGenotypes;
    }

    public void setNumMissingGenotypes(int numMissingGenotypes) {
        this.numMissingGenotypes = numMissingGenotypes;
    }

    public int getNumMendelErrors() {
        return numMendelErrors;
    }

    public void setNumMendelErrors(int numMendelErrors) {
        this.numMendelErrors = numMendelErrors;
    }

    public float getPercentCasesDominant() {
        return percentCasesDominant;
    }

    public void setPercentCasesDominant(float percentCasesDominant) {
        this.percentCasesDominant = percentCasesDominant;
    }

    public float getPercentControlsDominant() {
        return percentControlsDominant;
    }

    public void setPercentControlsDominant(float percentControlsDominant) {
        this.percentControlsDominant = percentControlsDominant;
    }

    public float getPercentCasesRecessive() {
        return percentCasesRecessive;
    }

    public void setPercentCasesRecessive(float percentCasesRecessive) {
        this.percentCasesRecessive = percentCasesRecessive;
    }

    public float getPercentControlsRecessive() {
        return percentControlsRecessive;
    }

    public void setPercentControlsRecessive(float percentControlsRecessive) {
        this.percentControlsRecessive = percentControlsRecessive;
    }
}
