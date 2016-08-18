package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AlternateCoordinate")
public class AlternateCoordinate {

    private String chromosome;
    private Integer start;
    private Integer end;
    private String reference;
    private String alternate;
    private String type;

    public AlternateCoordinate() {
        // needed for JAXB
    }

    public AlternateCoordinate(String chromosome, Integer start, Integer end, String reference, String alternate, String type) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.reference = reference;
        this.alternate = alternate;
        this.type = type;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}