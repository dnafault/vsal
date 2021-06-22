package au.org.garvan.vsal.beacon.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ssvs-beacon-response")
public class BeaconResponseSSVS {

    private CoreQuery coreQuery;
    private Long ssvsTimeMs;  // ms
    private List<Variant> variants;
    private Long total;  // total # variants
    private Error error;

    public BeaconResponseSSVS() {
        // needed for JAXB
    }

    public BeaconResponseSSVS(CoreQuery coreQuery, Long ssvsTimeMs, List<Variant> variants, Long total, Error error) {
        this.coreQuery = coreQuery;
        this.ssvsTimeMs = ssvsTimeMs;
        this.variants = variants;
        this.total = total;
        this.error = error;
    }

    public CoreQuery getCoreQuery() {
        return coreQuery;
    }

    public void setCoreQuery(CoreQuery coreQuery) {
        this.coreQuery = coreQuery;
    }

    public Long getSsvsTimeMs() {
        return ssvsTimeMs;
    }

    public void setSsvsTimeMs(Long ssvsTimeMs) {
        this.ssvsTimeMs = ssvsTimeMs;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}