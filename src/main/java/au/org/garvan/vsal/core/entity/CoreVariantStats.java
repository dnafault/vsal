package au.org.garvan.vsal.core.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement(name = "CoreVariantStats")
public class CoreVariantStats {
    private int   altAlleleCount;
    private float altAlleleFreq;
    private Map<String, Integer> genotypesCount;

    public CoreVariantStats() {
        // needed for JAXB
    }

    public CoreVariantStats(int altAlleleCount, float altAlleleFreq, Map<String, Integer> genotypesCount) {
        this.altAlleleCount = altAlleleCount;
        this.altAlleleFreq = altAlleleFreq;
        this.genotypesCount = genotypesCount;
    }

    public int getAltAlleleCount() {
        return altAlleleCount;
    }

    public void setAltAlleleCount(int altAlleleCount) {
        this.altAlleleCount = altAlleleCount;
    }

    public float getAltAlleleFreq() {
        return altAlleleFreq;
    }

    public void setAltAlleleFreq(float altAlleleFreq) {
        this.altAlleleFreq = altAlleleFreq;
    }

    public Map<String, Integer> getGenotypesCount() {
        return genotypesCount;
    }

    public void setGenotypesCount(Map<String, Integer> genotypesCount) {
        this.genotypesCount = genotypesCount;
    }
}