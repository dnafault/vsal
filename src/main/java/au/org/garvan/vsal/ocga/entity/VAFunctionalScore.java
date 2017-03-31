package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VAFunctionalScore")
public class VAFunctionalScore {

    private float score;
    private String source;
    private String description;

    public VAFunctionalScore() {
        // needed for JAXB
    }

    public VAFunctionalScore(float score, String source, String description) {
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
