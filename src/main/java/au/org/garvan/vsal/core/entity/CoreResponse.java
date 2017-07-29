package au.org.garvan.vsal.core.entity;

import au.org.garvan.vsal.beacon.entity.Error;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * CoreResponse.
 */
@XmlRootElement(name = "CoreResponse")
public class CoreResponse {

    private CoreQuery coreQuery;
    private Long vsalTime;  // ms
    private List<CoreVariant> v;
    private int total;
    private Error error;
    private String status;

    public CoreResponse() {
        // needed for JAXB
    }

    public CoreResponse(CoreQuery coreQuery, Long vsalTime, Error error) {
        this.coreQuery = coreQuery;
        this.vsalTime = vsalTime;
        this.error = error;
    }

    public CoreResponse(CoreQuery coreQuery, Long vsalTime, List<CoreVariant> variants, int total, Error error, String status) {
        this.coreQuery = coreQuery;
        this.vsalTime = vsalTime;
        this.v = variants;
        this.total = total;
        this.error = error;
        this.status = status;
    }

    public CoreQuery getCoreQuery() {
        return coreQuery;
    }

    public void setCoreQuery(CoreQuery coreQuery) {
        this.coreQuery = coreQuery;
    }

    public Long getVsalTime() {
        return vsalTime;
    }

    public void setVsalTime(Long vsalTime) {
        this.vsalTime = vsalTime;
    }

    public List<CoreVariant> getV() {
        return v;
    }

    public void setV(List<CoreVariant> v) {
        this.v = v;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
