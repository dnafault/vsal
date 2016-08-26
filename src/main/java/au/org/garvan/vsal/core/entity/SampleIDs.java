package au.org.garvan.vsal.core.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SampleIDs")
public class SampleIDs {

    private List<String> sampleIds;

    public SampleIDs() {
        // needed for JAXB
    }

    public SampleIDs(List<String> sampleIds) {
        this.sampleIds = sampleIds;
    }

    public List<String> getSampleIds() {
        return sampleIds;
    }

    public void setSampleIds(List<String> sampleIds) {
        this.sampleIds = sampleIds;
    }
}