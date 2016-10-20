package au.org.garvan.vsal.core.entity;

import au.org.garvan.vsal.ocga.entity.VariantStats;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CoreVariant")
public class CoreVariant {

    private String chromosome;
    private int start;
    private int end;
    private String dbSNP;

    private String alternate;
    private String reference;
    private String strand;
    private String type;

    private List<VariantStats> variantStats;

    public CoreVariant() {
        // needed for JAXB
    }

    public CoreVariant(String chromosome, int start, int end, String dbSNP, String alternate, String reference,
                       String strand, String type, List<VariantStats> variantStats) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.dbSNP = dbSNP;
        this.alternate = alternate;
        this.reference = reference;
        this.strand = strand;
        this.type = type;
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

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
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

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<VariantStats> getVariantStats() {
        return variantStats;
    }

    public void setVariantStats(List<VariantStats> variantStats) {
        this.variantStats = variantStats;
    }
}