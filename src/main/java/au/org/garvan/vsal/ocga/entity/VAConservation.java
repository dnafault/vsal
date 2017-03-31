package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VAConservation")
public class VAConservation {

    private float score;
    private String source;
    private String description;

    public VAConservation() {
        // needed for JAXB
    }

    public VAConservation(float score, String source, String description) {
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
