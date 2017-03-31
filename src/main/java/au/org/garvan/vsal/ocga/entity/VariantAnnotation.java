package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VariantAnnotation")
public class VariantAnnotation {

    private String chromosome;
    private int start;
    private String reference;
    private String alternate;
    private String ancestralAllele;
    private String id;
    private VAXrefs[] xrefs;

    private String displayConsequenceType;
    private VAConsequenceTypes[] consequenceTypes;
    private VAPopulationFrequencies[] populationFrequencies;

    private String minorAllele;
    private float minorAlleleFreq;

    private VAConservation[] conservation;
    private VAGeneExpression[] geneExpression;
    private VAGeneTraitAssociation[] geneTraitAssociation;
    private VAGeneDrugInteraction[] geneDrugInteraction;
    private VAVariantTraitAssociation variantTraitAssociation;
    private VAFunctionalScore[] functionalScore;
//    private String[] additionalAttributes;

    public VariantAnnotation() {
        // needed for JAXB
    }

    public VariantAnnotation(String chromosome, int start, String reference, String alternate, String ancestralAllele, String id, VAXrefs[] xrefs, String displayConsequenceType, VAConsequenceTypes[] consequenceTypes, VAPopulationFrequencies[] populationFrequencies, String minorAllele, float minorAlleleFreq, VAConservation[] conservation, VAGeneExpression[] geneExpression, VAGeneTraitAssociation[] geneTraitAssociation, VAGeneDrugInteraction[] geneDrugInteraction, VAVariantTraitAssociation variantTraitAssociation, VAFunctionalScore[] functionalScore) {
        this.chromosome = chromosome;
        this.start = start;
        this.reference = reference;
        this.alternate = alternate;
        this.ancestralAllele = ancestralAllele;
        this.id = id;
        this.xrefs = xrefs;
        this.displayConsequenceType = displayConsequenceType;
        this.consequenceTypes = consequenceTypes;
        this.populationFrequencies = populationFrequencies;
        this.minorAllele = minorAllele;
        this.minorAlleleFreq = minorAlleleFreq;
        this.conservation = conservation;
        this.geneExpression = geneExpression;
        this.geneTraitAssociation = geneTraitAssociation;
        this.geneDrugInteraction = geneDrugInteraction;
        this.variantTraitAssociation = variantTraitAssociation;
        this.functionalScore = functionalScore;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public String getAncestralAllele() {
        return ancestralAllele;
    }

    public void setAncestralAllele(String ancestralAllele) {
        this.ancestralAllele = ancestralAllele;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VAXrefs[] getXrefs() {
        return xrefs;
    }

    public void setXrefs(VAXrefs[] xrefs) {
        this.xrefs = xrefs;
    }

    public String getDisplayConsequenceType() {
        return displayConsequenceType;
    }

    public void setDisplayConsequenceType(String displayConsequenceType) {
        this.displayConsequenceType = displayConsequenceType;
    }

    public VAConsequenceTypes[] getConsequenceTypes() {
        return consequenceTypes;
    }

    public void setConsequenceTypes(VAConsequenceTypes[] consequenceTypes) {
        this.consequenceTypes = consequenceTypes;
    }

    public VAPopulationFrequencies[] getPopulationFrequencies() {
        return populationFrequencies;
    }

    public void setPopulationFrequencies(VAPopulationFrequencies[] populationFrequencies) {
        this.populationFrequencies = populationFrequencies;
    }

    public String getMinorAllele() {
        return minorAllele;
    }

    public void setMinorAllele(String minorAllele) {
        this.minorAllele = minorAllele;
    }

    public float getMinorAlleleFreq() {
        return minorAlleleFreq;
    }

    public void setMinorAlleleFreq(float minorAlleleFreq) {
        this.minorAlleleFreq = minorAlleleFreq;
    }

    public VAConservation[] getConservation() {
        return conservation;
    }

    public void setConservation(VAConservation[] conservation) {
        this.conservation = conservation;
    }

    public VAGeneExpression[] getGeneExpression() {
        return geneExpression;
    }

    public void setGeneExpression(VAGeneExpression[] geneExpression) {
        this.geneExpression = geneExpression;
    }

    public VAGeneTraitAssociation[] getGeneTraitAssociation() {
        return geneTraitAssociation;
    }

    public void setGeneTraitAssociation(VAGeneTraitAssociation[] geneTraitAssociation) {
        this.geneTraitAssociation = geneTraitAssociation;
    }

    public VAGeneDrugInteraction[] getGeneDrugInteraction() {
        return geneDrugInteraction;
    }

    public void setGeneDrugInteraction(VAGeneDrugInteraction[] geneDrugInteraction) {
        this.geneDrugInteraction = geneDrugInteraction;
    }

    public VAVariantTraitAssociation getVariantTraitAssociation() {
        return variantTraitAssociation;
    }

    public void setVariantTraitAssociation(VAVariantTraitAssociation variantTraitAssociation) {
        this.variantTraitAssociation = variantTraitAssociation;
    }

    public VAFunctionalScore[] getFunctionalScore() {
        return functionalScore;
    }

    public void setFunctionalScore(VAFunctionalScore[] functionalScore) {
        this.functionalScore = functionalScore;
    }
}
