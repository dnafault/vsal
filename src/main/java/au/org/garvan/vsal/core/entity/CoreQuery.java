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
    private List<String> genes;
    private List<String> rsIDs;
    private VariantType type;
    private Reference reference;
    private Integer limit;
    private Integer skip;

    // Clinical data
    private Gender gender;
    private Integer yobStart;
    private Integer yobEnd;
    private Integer sbpStart;
    private Integer sbpEnd;
    private Float heightStart;
    private Float heightEnd;
    private Float weightStart;
    private Float weightEnd;
    private Integer abdCircStart;
    private Integer abdCircEnd;
    private Float glcStart;
    private Float glcEnd;

    public CoreQuery() {
        // needed for JAXB
    }

    public CoreQuery(Chromosome chromosome, Integer positionStart, Integer positionEnd, String refAllele, String altAllele,
                     DatasetID datasetId, List<String> genes, List<String> rsIDs, VariantType type, Reference reference,
                     Integer limit, Integer skip, Gender gender, Integer yobStart, Integer yobEnd, Integer sbpStart,
                     Integer sbpEnd, Float heightStart, Float heightEnd, Float weightStart, Float weightEnd,
                     Integer abdCircStart, Integer abdCircEnd, Float glcStart, Float glcEnd) {
        this.chromosome = chromosome;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.refAllele = refAllele;
        this.altAllele = altAllele;
        this.datasetId = datasetId;
        this.genes = genes;
        this.rsIDs = rsIDs;
        this.type = type;
        this.reference = reference;
        this.limit = limit;
        this.skip = skip;
        this.gender = gender;
        this.yobStart = yobStart;
        this.yobEnd = yobEnd;
        this.sbpStart = sbpStart;
        this.sbpEnd = sbpEnd;
        this.heightStart = heightStart;
        this.heightEnd = heightEnd;
        this.weightStart = weightStart;
        this.weightEnd = weightEnd;
        this.abdCircStart = abdCircStart;
        this.abdCircEnd = abdCircEnd;
        this.glcStart = glcStart;
        this.glcEnd = glcEnd;
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

    public List<String> getGenes() {
        return genes;
    }

    public void setGenes(List<String> genes) {
        this.genes = genes;
    }

    public List<String> getRsIDs() {
        return rsIDs;
    }

    public void setRsIDs(List<String> rsIDs) {
        this.rsIDs = rsIDs;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getYobStart() {
        return yobStart;
    }

    public void setYobStart(Integer yobStart) {
        this.yobStart = yobStart;
    }

    public Integer getYobEnd() {
        return yobEnd;
    }

    public void setYobEnd(Integer yobEnd) {
        this.yobEnd = yobEnd;
    }

    public Integer getSbpStart() {
        return sbpStart;
    }

    public void setSbpStart(Integer sbpStart) {
        this.sbpStart = sbpStart;
    }

    public Integer getSbpEnd() {
        return sbpEnd;
    }

    public void setSbpEnd(Integer sbpEnd) {
        this.sbpEnd = sbpEnd;
    }

    public Float getHeightStart() {
        return heightStart;
    }

    public void setHeightStart(Float heightStart) {
        this.heightStart = heightStart;
    }

    public Float getHeightEnd() {
        return heightEnd;
    }

    public void setHeightEnd(Float heightEnd) {
        this.heightEnd = heightEnd;
    }

    public Float getWeightStart() {
        return weightStart;
    }

    public void setWeightStart(Float weightStart) {
        this.weightStart = weightStart;
    }

    public Float getWeightEnd() {
        return weightEnd;
    }

    public void setWeightEnd(Float weightEnd) {
        this.weightEnd = weightEnd;
    }

    public Integer getAbdCircStart() {
        return abdCircStart;
    }

    public void setAbdCircStart(Integer abdCircStart) {
        this.abdCircStart = abdCircStart;
    }

    public Integer getAbdCircEnd() {
        return abdCircEnd;
    }

    public void setAbdCircEnd(Integer abdCircEnd) {
        this.abdCircEnd = abdCircEnd;
    }

    public Float getGlcStart() {
        return glcStart;
    }

    public void setGlcStart(Float glcStart) {
        this.glcStart = glcStart;
    }

    public Float getGlcEnd() {
        return glcEnd;
    }

    public void setGlcEnd(Float glcEnd) {
        this.glcEnd = glcEnd;
    }
}
