package au.org.garvan.vsal.core.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CoreVariant")
public class CoreVariant {

    private String chromosome;
    private int start;
    private int end;
    private String rsId;

    private String alternate;
    private String reference;
    private String strand;
    private String type;

    public CoreVariant() {
        // needed for JAXB
    }

    public CoreVariant(String chromosome, int start, int end, String rsId, String alternate, String reference, String strand, String type) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.rsId = rsId;
        this.alternate = alternate;
        this.reference = reference;
        this.strand = strand;
        this.type = type;
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

    public String getRsId() {
        return rsId;
    }

    public void setRsId(String rsId) {
        this.rsId = rsId;
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
}