package au.org.garvan.vsal.core.entity;

import au.org.garvan.vsal.ocga.entity.VariantAnnotation;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CoreVariant")
public class CoreVariant {

    private String chromosome;
    private int start;
    private String dbSNP;

    private String alternate;
    private String reference;
    private String type;

    private VariantAnnotation annotation;
    private List<CoreVariantStats> variantStats;

    public CoreVariant() {
        // needed for JAXB
    }

    public CoreVariant(String chromosome, int start, String dbSNP, String alternate, String reference, String type, VariantAnnotation annotation, List<CoreVariantStats> variantStats) {
        this.chromosome = chromosome;
        this.start = start;
        this.dbSNP = dbSNP;
        this.alternate = alternate;
        this.reference = reference;
        this.type = type;
        this.annotation = annotation;
        this.variantStats = variantStats;
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

    public String getDbSNP() {
        return dbSNP;
    }

    public void setDbSNP(String dbSNP) {
        this.dbSNP = dbSNP;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VariantAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(VariantAnnotation annotation) {
        this.annotation = annotation;
    }

    public List<CoreVariantStats> getVariantStats() {
        return variantStats;
    }

    public void setVariantStats(List<CoreVariantStats> variantStats) {
        this.variantStats = variantStats;
    }
}