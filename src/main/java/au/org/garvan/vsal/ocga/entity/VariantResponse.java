package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "VariantResponse")
public class VariantResponse {

    private String type;
    private String reference;
    private List<String> names;
    private List<StudyEntry> studies;
    private String alternate;
    private String strand;
    //    private StructuralVariation sv;
//    private Map<String,List<String>> hgvs;
//    private Map<String,String> hgvs;
    //    private VariantAnnotation annotation

    private String id; // supposed to be dbSNP ID, but if null, it's filled in by ocga-generated trash
    private int end;
    private int start;
    private String chromosome;
    private int length;

    public VariantResponse() {
        // needed for JAXB
    }

    public VariantResponse(String type, String reference, List<String> names, List<StudyEntry> studies, String alternate,
                           String strand, String id, int end, int start, String chromosome, int length) {
        this.type = type;
        this.reference = reference;
        this.names = names;
        this.studies = studies;
        this.alternate = alternate;
        this.strand = strand;
        this.id = id;
        this.end = end;
        this.start = start;
        this.chromosome = chromosome;
        this.length = length;
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

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<StudyEntry> getStudies() {
        return studies;
    }

    public void setStudies(List<StudyEntry> studies) {
        this.studies = studies;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
