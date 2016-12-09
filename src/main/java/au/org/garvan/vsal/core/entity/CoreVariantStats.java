package au.org.garvan.vsal.core.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CoreVariantStats")
public class CoreVariantStats {
    private int   altAlleleCount;
    private float altAlleleFreq;
    private float maf;

    public CoreVariantStats() {
        // needed for JAXB
    }

    public CoreVariantStats(int altAlleleCount, float altAlleleFreq, float maf) {
        this.altAlleleCount = altAlleleCount;
        this.altAlleleFreq = altAlleleFreq;
        this.maf = maf;
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

    public float getMaf() {
        return maf;
    }

    public void setMaf(float maf) {
        this.maf = maf;
    }
}