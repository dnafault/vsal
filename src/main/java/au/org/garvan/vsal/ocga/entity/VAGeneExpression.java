package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VAGeneExpression")
public class VAGeneExpression {

    private String geneName;
    private String transcriptId;
    private String experimentalFactor;
    private String factorValue;
    private String experimentId;
    private String technologyPlatform;
    private String expression;
    private float pvalue;

    public VAGeneExpression() {
        // needed for JAXB
    }

    public VAGeneExpression(String geneName, String transcriptId, String experimentalFactor, String factorValue, String experimentId, String technologyPlatform, String expression, float pvalue) {
        this.geneName = geneName;
        this.transcriptId = transcriptId;
        this.experimentalFactor = experimentalFactor;
        this.factorValue = factorValue;
        this.experimentId = experimentId;
        this.technologyPlatform = technologyPlatform;
        this.expression = expression;
        this.pvalue = pvalue;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public void setTranscriptId(String transcriptId) {
        this.transcriptId = transcriptId;
    }

    public String getExperimentalFactor() {
        return experimentalFactor;
    }

    public void setExperimentalFactor(String experimentalFactor) {
        this.experimentalFactor = experimentalFactor;
    }

    public String getFactorValue() {
        return factorValue;
    }

    public void setFactorValue(String factorValue) {
        this.factorValue = factorValue;
    }

    public String getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }

    public String getTechnologyPlatform() {
        return technologyPlatform;
    }

    public void setTechnologyPlatform(String technologyPlatform) {
        this.technologyPlatform = technologyPlatform;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public float getPvalue() {
        return pvalue;
    }

    public void setPvalue(float pvalue) {
        this.pvalue = pvalue;
    }
}
