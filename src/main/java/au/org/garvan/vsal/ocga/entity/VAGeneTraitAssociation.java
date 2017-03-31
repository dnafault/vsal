package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VAGeneTraitAssociation")
public class VAGeneTraitAssociation {

    private String id;
    private String name;
    private String hpo;
    private float score;
    private int numberOfPubmeds;
    private String[] associationTypes;
    private String[] sources;
    private String source;

    public VAGeneTraitAssociation() {
        // needed for JAXB
    }

    public VAGeneTraitAssociation(String id, String name, String hpo, float score, int numberOfPubmeds, String[] associationTypes, String[] sources, String source) {
        this.id = id;
        this.name = name;
        this.hpo = hpo;
        this.score = score;
        this.numberOfPubmeds = numberOfPubmeds;
        this.associationTypes = associationTypes;
        this.sources = sources;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHpo() {
        return hpo;
    }

    public void setHpo(String hpo) {
        this.hpo = hpo;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getNumberOfPubmeds() {
        return numberOfPubmeds;
    }

    public void setNumberOfPubmeds(int numberOfPubmeds) {
        this.numberOfPubmeds = numberOfPubmeds;
    }

    public String[] getAssociationTypes() {
        return associationTypes;
    }

    public void setAssociationTypes(String[] associationTypes) {
        this.associationTypes = associationTypes;
    }

    public String[] getSources() {
        return sources;
    }

    public void setSources(String[] sources) {
        this.sources = sources;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
