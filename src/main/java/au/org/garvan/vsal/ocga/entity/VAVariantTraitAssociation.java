package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VAVariantTraitAssociation")
public class VAVariantTraitAssociation {

    private ClinvarAnnotation[] clinvar;
    private GWASAnnotation[] gwas;
    private CosmicAnnotation[] cosmic;

    public VAVariantTraitAssociation() {
        // needed for JAXB
    }

    public VAVariantTraitAssociation(ClinvarAnnotation[] clinvar, GWASAnnotation[] gwas, CosmicAnnotation[] cosmic) {
        this.clinvar = clinvar;
        this.gwas = gwas;
        this.cosmic = cosmic;
    }

    public ClinvarAnnotation[] getClinvar() {
        return clinvar;
    }

    public void setClinvar(ClinvarAnnotation[] clinvar) {
        this.clinvar = clinvar;
    }

    public GWASAnnotation[] getGwas() {
        return gwas;
    }

    public void setGwas(GWASAnnotation[] gwas) {
        this.gwas = gwas;
    }

    public CosmicAnnotation[] getCosmic() {
        return cosmic;
    }

    public void setCosmic(CosmicAnnotation[] cosmic) {
        this.cosmic = cosmic;
    }
}

@XmlRootElement(name = "ClinvarAnnotation")
class ClinvarAnnotation {

    private String accession;
    private String clinicalSignificance;
    private String[] traits;
    private String[] geneNames;
    private String reviewStatus;

    public ClinvarAnnotation() {
        // needed for JAXB
    }

    public ClinvarAnnotation(String accession, String clinicalSignificance, String[] traits, String[] geneNames, String reviewStatus) {
        this.accession = accession;
        this.clinicalSignificance = clinicalSignificance;
        this.traits = traits;
        this.geneNames = geneNames;
        this.reviewStatus = reviewStatus;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getClinicalSignificance() {
        return clinicalSignificance;
    }

    public void setClinicalSignificance(String clinicalSignificance) {
        this.clinicalSignificance = clinicalSignificance;
    }

    public String[] getTraits() {
        return traits;
    }

    public void setTraits(String[] traits) {
        this.traits = traits;
    }

    public String[] getGeneNames() {
        return geneNames;
    }

    public void setGeneNames(String[] geneNames) {
        this.geneNames = geneNames;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}

@XmlRootElement(name = "GWASAnnotation")
class GWASAnnotation {

    private String snpIdCurrent;
    private String[] traits;
    private float riskAlleleFrequency;
    private String reportedGenes;

    public GWASAnnotation() {
        // needed for JAXB
    }

    public GWASAnnotation(String snpIdCurrent, String[] traits, float riskAlleleFrequency, String reportedGenes) {
        this.snpIdCurrent = snpIdCurrent;
        this.traits = traits;
        this.riskAlleleFrequency = riskAlleleFrequency;
        this.reportedGenes = reportedGenes;
    }

    public String getSnpIdCurrent() {
        return snpIdCurrent;
    }

    public void setSnpIdCurrent(String snpIdCurrent) {
        this.snpIdCurrent = snpIdCurrent;
    }

    public String[] getTraits() {
        return traits;
    }

    public void setTraits(String[] traits) {
        this.traits = traits;
    }

    public float getRiskAlleleFrequency() {
        return riskAlleleFrequency;
    }

    public void setRiskAlleleFrequency(float riskAlleleFrequency) {
        this.riskAlleleFrequency = riskAlleleFrequency;
    }

    public String getReportedGenes() {
        return reportedGenes;
    }

    public void setReportedGenes(String reportedGenes) {
        this.reportedGenes = reportedGenes;
    }
}

@XmlRootElement(name = "CosmicAnnotation")
class CosmicAnnotation {

    private String mutationId;
    private String primarySite;
    private String siteSubtype;
    private String primaryHistology;
    private String histologySubtype;
    private String sampleSource;
    private String tumourOrigin;
    private String geneName;
    private String mutationSomaticStatus;

    public CosmicAnnotation() {
        // needed for JAXB
    }

    public CosmicAnnotation(String mutationId, String primarySite, String siteSubtype, String primaryHistology, String histologySubtype, String sampleSource, String tumourOrigin, String geneName, String mutationSomaticStatus) {
        this.mutationId = mutationId;
        this.primarySite = primarySite;
        this.siteSubtype = siteSubtype;
        this.primaryHistology = primaryHistology;
        this.histologySubtype = histologySubtype;
        this.sampleSource = sampleSource;
        this.tumourOrigin = tumourOrigin;
        this.geneName = geneName;
        this.mutationSomaticStatus = mutationSomaticStatus;
    }

    public String getMutationId() {
        return mutationId;
    }

    public void setMutationId(String mutationId) {
        this.mutationId = mutationId;
    }

    public String getPrimarySite() {
        return primarySite;
    }

    public void setPrimarySite(String primarySite) {
        this.primarySite = primarySite;
    }

    public String getSiteSubtype() {
        return siteSubtype;
    }

    public void setSiteSubtype(String siteSubtype) {
        this.siteSubtype = siteSubtype;
    }

    public String getPrimaryHistology() {
        return primaryHistology;
    }

    public void setPrimaryHistology(String primaryHistology) {
        this.primaryHistology = primaryHistology;
    }

    public String getHistologySubtype() {
        return histologySubtype;
    }

    public void setHistologySubtype(String histologySubtype) {
        this.histologySubtype = histologySubtype;
    }

    public String getSampleSource() {
        return sampleSource;
    }

    public void setSampleSource(String sampleSource) {
        this.sampleSource = sampleSource;
    }

    public String getTumourOrigin() {
        return tumourOrigin;
    }

    public void setTumourOrigin(String tumourOrigin) {
        this.tumourOrigin = tumourOrigin;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getMutationSomaticStatus() {
        return mutationSomaticStatus;
    }

    public void setMutationSomaticStatus(String mutationSomaticStatus) {
        this.mutationSomaticStatus = mutationSomaticStatus;
    }
}