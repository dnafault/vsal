package au.org.garvan.vsal.ocga.entity;

public class VariantRequestParams {

    private String ids;
    private String region;
    private String chromosome;
    private String gene;
    private String type;
    private String reference;
    private String alternate;
    private String studies;
    private String returnedStudies;
    private String returnedSamples;
    private String returnedFiles;
    private String files;
    private String filter;
    private String maf;
    private String mgf;
    private String missingAlleles;
    private String missingGenotypes;
    private Boolean annotationExists;
    private String genotype;
    private String polyphen;
    private String sift;
    private String alternate_frequency;
    private String reference_frequency;
    private String unknownGenotype;
    private Boolean samplesMetadata;
    private Boolean sort;
    private String groupBy;
    private Boolean histogram;
    private String interval;
    private Boolean merge;

    public VariantRequestParams() {
    }

    public VariantRequestParams(String ids, String region, String chromosome, String gene, String type, String reference, String alternate, String studies, String returnedStudies, String returnedSamples, String returnedFiles, String files, String filter, String maf, String mgf, String missingAlleles, String missingGenotypes, Boolean annotationExists, String genotype, String polyphen, String sift, String alternate_frequency, String reference_frequency, String unknownGenotype, Boolean samplesMetadata, Boolean sort, String groupBy, Boolean histogram, String interval, Boolean merge) {
        this.ids = ids;
        this.region = region;
        this.chromosome = chromosome;
        this.gene = gene;
        this.type = type;
        this.reference = reference;
        this.alternate = alternate;
        this.studies = studies;
        this.returnedStudies = returnedStudies;
        this.returnedSamples = returnedSamples;
        this.returnedFiles = returnedFiles;
        this.files = files;
        this.filter = filter;
        this.maf = maf;
        this.mgf = mgf;
        this.missingAlleles = missingAlleles;
        this.missingGenotypes = missingGenotypes;
        this.annotationExists = annotationExists;
        this.genotype = genotype;
        this.polyphen = polyphen;
        this.sift = sift;
        this.alternate_frequency = alternate_frequency;
        this.reference_frequency = reference_frequency;
        this.unknownGenotype = unknownGenotype;
        this.samplesMetadata = samplesMetadata;
        this.sort = sort;
        this.groupBy = groupBy;
        this.histogram = histogram;
        this.interval = interval;
        this.merge = merge;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getStudies() {
        return studies;
    }

    public void setStudies(String studies) {
        this.studies = studies;
    }

    public String getReturnedStudies() {
        return returnedStudies;
    }

    public void setReturnedStudies(String returnedStudies) {
        this.returnedStudies = returnedStudies;
    }

    public String getReturnedSamples() {
        return returnedSamples;
    }

    public void setReturnedSamples(String returnedSamples) {
        this.returnedSamples = returnedSamples;
    }

    public String getReturnedFiles() {
        return returnedFiles;
    }

    public void setReturnedFiles(String returnedFiles) {
        this.returnedFiles = returnedFiles;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getMaf() {
        return maf;
    }

    public void setMaf(String maf) {
        this.maf = maf;
    }

    public String getMgf() {
        return mgf;
    }

    public void setMgf(String mgf) {
        this.mgf = mgf;
    }

    public String getMissingAlleles() {
        return missingAlleles;
    }

    public void setMissingAlleles(String missingAlleles) {
        this.missingAlleles = missingAlleles;
    }

    public String getMissingGenotypes() {
        return missingGenotypes;
    }

    public void setMissingGenotypes(String missingGenotypes) {
        this.missingGenotypes = missingGenotypes;
    }

    public Boolean getAnnotationExists() {
        return annotationExists;
    }

    public void setAnnotationExists(Boolean annotationExists) {
        this.annotationExists = annotationExists;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
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

    public String getAlternate_frequency() {
        return alternate_frequency;
    }

    public void setAlternate_frequency(String alternate_frequency) {
        this.alternate_frequency = alternate_frequency;
    }

    public String getReference_frequency() {
        return reference_frequency;
    }

    public void setReference_frequency(String reference_frequency) {
        this.reference_frequency = reference_frequency;
    }

    public String getUnknownGenotype() {
        return unknownGenotype;
    }

    public void setUnknownGenotype(String unknownGenotype) {
        this.unknownGenotype = unknownGenotype;
    }

    public Boolean getSamplesMetadata() {
        return samplesMetadata;
    }

    public void setSamplesMetadata(Boolean samplesMetadata) {
        this.samplesMetadata = samplesMetadata;
    }

    public Boolean getSort() {
        return sort;
    }

    public void setSort(Boolean sort) {
        this.sort = sort;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public Boolean getHistogram() {
        return histogram;
    }

    public void setHistogram(Boolean histogram) {
        this.histogram = histogram;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Boolean getMerge() {
        return merge;
    }

    public void setMerge(Boolean merge) {
        this.merge = merge;
    }
}