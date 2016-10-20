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
    private List<String> dbSNP;
    private VariantType type;
    private Reference reference;
    private Integer limit;
    private Integer skip;
    private Boolean count;

    // Stat
    private String maf;
    private String popMaf;
    private String popAltFrq;
    private String popRefFrq;

    // Annotations
    private String annotCT;
    private String annotHPO;
    private String annotGO;
    private String annotXref;
    private String annotBiotype;

    private String polyphen;
    private String sift;
    private String conservationScore;

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
                     DatasetID datasetId, List<String> genes, List<String> dbSNP, VariantType type, Reference reference,
                     Integer limit, Integer skip, Boolean count, String maf, String popMaf, String popAltFrq, String popRefFrq,
                     String annotCT, String annotHPO, String annotGO, String annotXref, String annotBiotype, String polyphen,
                     String sift, String conservationScore, Gender gender, Integer yobStart, Integer yobEnd, Integer sbpStart,
                     Integer sbpEnd, Float heightStart, Float heightEnd, Float weightStart, Float weightEnd, Integer abdCircStart,
                     Integer abdCircEnd, Float glcStart, Float glcEnd) {
        this.chromosome = chromosome;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.refAllele = refAllele;
        this.altAllele = altAllele;
        this.datasetId = datasetId;
        this.genes = genes;
        this.dbSNP = dbSNP;
        this.type = type;
        this.reference = reference;
        this.limit = limit;
        this.skip = skip;
        this.count = count;
        this.maf = maf;
        this.popMaf = popMaf;
        this.popAltFrq = popAltFrq;
        this.popRefFrq = popRefFrq;
        this.annotCT = annotCT;
        this.annotHPO = annotHPO;
        this.annotGO = annotGO;
        this.annotXref = annotXref;
        this.annotBiotype = annotBiotype;
        this.polyphen = polyphen;
        this.sift = sift;
        this.conservationScore = conservationScore;
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

    public Boolean getCount() {
        return count;
    }

    public void setCount(Boolean count) {
        this.count = count;
    }

    public String getMaf() {
        return maf;
    }

    public void setMaf(String maf) {
        this.maf = maf;
    }

    public String getPopMaf() {
        return popMaf;
    }

    public void setPopMaf(String popMaf) {
        this.popMaf = popMaf;
    }

    public String getPopAltFrq() {
        return popAltFrq;
    }

    public void setPopAltFrq(String popAltFrq) {
        this.popAltFrq = popAltFrq;
    }

    public String getPopRefFrq() {
        return popRefFrq;
    }

    public void setPopRefFrq(String popRefFrq) {
        this.popRefFrq = popRefFrq;
    }

    public String getAnnotCT() {
        return annotCT;
    }

    public void setAnnotCT(String annotCT) {
        this.annotCT = annotCT;
    }

    public String getAnnotHPO() {
        return annotHPO;
    }

    public void setAnnotHPO(String annotHPO) {
        this.annotHPO = annotHPO;
    }

    public String getAnnotGO() {
        return annotGO;
    }

    public void setAnnotGO(String annotGO) {
        this.annotGO = annotGO;
    }

    public String getAnnotXref() {
        return annotXref;
    }

    public void setAnnotXref(String annotXref) {
        this.annotXref = annotXref;
    }

    public String getAnnotBiotype() {
        return annotBiotype;
    }

    public void setAnnotBiotype(String annotBiotype) {
        this.annotBiotype = annotBiotype;
    }

    public String getPolyphen() {
        return polyphen;
    }

    public void setPolyphen(String polyphen) {
        this.polyphen = polyphen;
    }

    public String getSift() {
        return sift;
    }

    public void setSift(String sift) {
        this.sift = sift;
    }

    public String getConservationScore() {
        return conservationScore;
    }

    public void setConservationScore(String conservationScore) {
        this.conservationScore = conservationScore;
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
