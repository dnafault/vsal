package au.org.garvan.vsal.core.entity;

import au.org.garvan.vsal.beacon.entity.Chromosome;
import au.org.garvan.vsal.beacon.entity.Reference;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CoreQuery")
public class CoreQuery {

    private Chromosome chromosome;
    private Integer positionStart;
    private Integer positionEnd;
    private String refAllele;
    private String altAllele;
    private DatasetID datasetId;
    private List<String> dbSNP;
    private VariantType type;
    private Reference reference;
    private Integer limit;
    private Integer skip;
    private String jwt;
    private Boolean conj = false; // find variants that exist in all samples
    private Boolean returnAnnotations = false;
    private Boolean pheno = false;
    private List<String> samples;

    public CoreQuery() {
        // needed for JAXB
    }

    public CoreQuery(Chromosome chromosome, Integer positionStart, String altAllele, DatasetID datasetId, Reference reference, VariantType type) {
        this.chromosome = chromosome;
        this.positionStart = positionStart;
        this.positionEnd = positionStart;
        this.altAllele = altAllele;
        this.datasetId = datasetId;
        this.reference = reference;
        this.type = type;
    }

    public CoreQuery(Chromosome chromosome, Integer positionStart, Integer positionEnd, String refAllele, String altAllele, DatasetID datasetId, List<String> dbSNP, VariantType type, Reference reference, Integer limit, Integer skip, String jwt, Boolean conj, Boolean returnAnnotations, Boolean pheno, List<String> samples) {
        this.chromosome = chromosome;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.refAllele = refAllele;
        this.altAllele = altAllele;
        this.datasetId = datasetId;
        this.dbSNP = dbSNP;
        this.type = type;
        this.reference = reference;
        this.limit = limit;
        this.skip = skip;
        this.jwt = jwt;
        this.conj = conj;
        this.returnAnnotations = returnAnnotations;
        this.pheno = pheno;
        this.samples = samples;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public void setChromosome(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public Integer getPositionStart() {
        return positionStart;
    }

    public void setPositionStart(Integer positionStart) {
        this.positionStart = positionStart;
    }

    public Integer getPositionEnd() {
        return positionEnd;
    }

    public void setPositionEnd(Integer positionEnd) {
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

    public Boolean getConj() {
        return conj;
    }

    public void setConj(Boolean conj) {
        this.conj = conj;
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

    public List<String> getSamples() {
        return samples;
    }

    public void setSamples(List<String> samples) {
        this.samples = samples;
    }
}