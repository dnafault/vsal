package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VAConsequenceTypes")
public class VAConsequenceTypes {

    private String geneName;
    private String ensemblGeneId;
    private String ensemblTranscriptId;
    private String strand;
    private String biotype;
    private int exonNumber;
    private String[] transcriptAnnotationFlags;
    private int cdnaPosition;
    private int cdsPosition;
    private String codon;
    private VAProtein proteinVariantAnnotation;
    private VASOTerms[] sequenceOntologyTerms;

    public VAConsequenceTypes() {
        // needed for JAXB
    }

    public VAConsequenceTypes(String geneName, String ensemblGeneId, String ensemblTranscriptId, String strand, String biotype, int exonNumber, String[] transcriptAnnotationFlags, int cdnaPosition, int cdsPosition, String codon, VAProtein proteinVariantAnnotation, VASOTerms[] sequenceOntologyTerms) {
        this.geneName = geneName;
        this.ensemblGeneId = ensemblGeneId;
        this.ensemblTranscriptId = ensemblTranscriptId;
        this.strand = strand;
        this.biotype = biotype;
        this.exonNumber = exonNumber;
        this.transcriptAnnotationFlags = transcriptAnnotationFlags;
        this.cdnaPosition = cdnaPosition;
        this.cdsPosition = cdsPosition;
        this.codon = codon;
        this.proteinVariantAnnotation = proteinVariantAnnotation;
        this.sequenceOntologyTerms = sequenceOntologyTerms;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getEnsemblGeneId() {
        return ensemblGeneId;
    }

    public void setEnsemblGeneId(String ensemblGeneId) {
        this.ensemblGeneId = ensemblGeneId;
    }

    public String getEnsemblTranscriptId() {
        return ensemblTranscriptId;
    }

    public void setEnsemblTranscriptId(String ensemblTranscriptId) {
        this.ensemblTranscriptId = ensemblTranscriptId;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public String getBiotype() {
        return biotype;
    }

    public void setBiotype(String biotype) {
        this.biotype = biotype;
    }

    public int getExonNumber() {
        return exonNumber;
    }

    public void setExonNumber(int exonNumber) {
        this.exonNumber = exonNumber;
    }

    public String[] getTranscriptAnnotationFlags() {
        return transcriptAnnotationFlags;
    }

    public void setTranscriptAnnotationFlags(String[] transcriptAnnotationFlags) {
        this.transcriptAnnotationFlags = transcriptAnnotationFlags;
    }

    public int getCdnaPosition() {
        return cdnaPosition;
    }

    public void setCdnaPosition(int cdnaPosition) {
        this.cdnaPosition = cdnaPosition;
    }

    public int getCdsPosition() {
        return cdsPosition;
    }

    public void setCdsPosition(int cdsPosition) {
        this.cdsPosition = cdsPosition;
    }

    public String getCodon() {
        return codon;
    }

    public void setCodon(String codon) {
        this.codon = codon;
    }

    public VAProtein getProteinVariantAnnotation() {
        return proteinVariantAnnotation;
    }

    public void setProteinVariantAnnotation(VAProtein proteinVariantAnnotation) {
        this.proteinVariantAnnotation = proteinVariantAnnotation;
    }

    public VASOTerms[] getSequenceOntologyTerms() {
        return sequenceOntologyTerms;
    }

    public void setSequenceOntologyTerms(VASOTerms[] sequenceOntologyTerms) {
        this.sequenceOntologyTerms = sequenceOntologyTerms;
    }
}