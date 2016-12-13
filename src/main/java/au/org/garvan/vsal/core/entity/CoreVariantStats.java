package au.org.garvan.vsal.core.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CoreVariantStats")
public class CoreVariantStats {
    private int   altAlleleCount;
    private float altAlleleFreq;

    public CoreVariantStats() {
        // needed for JAXB
    }

    public CoreVariantStats(int altAlleleCount, float altAlleleFreq) {
        this.altAlleleCount = altAlleleCount;
        this.altAlleleFreq = altAlleleFreq;
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
}