package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VAProtein")
public class VAProtein {

    private String uniprotAccession;
    private String uniprotName;
    private String uniprotVariantId;
    private int position;
    private String reference;
    private String alternate;
    private String functionalDescription;

    private SubstitutionScores[] substitutionScores;
    private String[] keywords;
    private VAProteinFeatures[] features;

    public VAProtein() {
        // needed for JAXB
    }

    public VAProtein(String uniprotAccession, String uniprotName, String uniprotVariantId, int position, String reference, String alternate, String functionalDescription, SubstitutionScores[] substitutionScores, String[] keywords, VAProteinFeatures[] features) {
        this.uniprotAccession = uniprotAccession;
        this.uniprotName = uniprotName;
        this.uniprotVariantId = uniprotVariantId;
        this.position = position;
        this.reference = reference;
        this.alternate = alternate;
        this.functionalDescription = functionalDescription;
        this.substitutionScores = substitutionScores;
        this.keywords = keywords;
        this.features = features;
    }

    public String getUniprotAccession() {
        return uniprotAccession;
    }

    public void setUniprotAccession(String uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
    }

    public String getUniprotName() {
        return uniprotName;
    }

    public void setUniprotName(String uniprotName) {
        this.uniprotName = uniprotName;
    }

    public String getUniprotVariantId() {
        return uniprotVariantId;
    }

    public void setUniprotVariantId(String uniprotVariantId) {
        this.uniprotVariantId = uniprotVariantId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public String getFunctionalDescription() {
        return functionalDescription;
    }

    public void setFunctionalDescription(String functionalDescription) {
        this.functionalDescription = functionalDescription;
    }

    public SubstitutionScores[] getSubstitutionScores() {
        return substitutionScores;
    }

    public void setSubstitutionScores(SubstitutionScores[] substitutionScores) {
        this.substitutionScores = substitutionScores;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public VAProteinFeatures[] getFeatures() {
        return features;
    }

    public void setFeatures(VAProteinFeatures[] features) {
        this.features = features;
    }
}


@XmlRootElement(name = "SubstitutionScores")
class SubstitutionScores {

    private float score;
    private String source;
    private String description;

    public SubstitutionScores() {
        // needed for JAXB
    }

    public SubstitutionScores(float score, String source, String description) {
        this.score = score;
        this.source = source;
        this.description = description;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

@XmlRootElement(name = "VAProteinFeatures")
class VAProteinFeatures {

    private String id;
    private int start;
    private int end;
    private String type;
    private String description;

    public VAProteinFeatures() {
        // needed for JAXB
    }

    public VAProteinFeatures(String id, int start, int end, String type, String description) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}