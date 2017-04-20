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
    private List<Integer> dbTime;
    private List<Integer> total;
    private List<CoreVariant> variants;
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

    public CoreResponse(CoreQuery coreQuery, Long vsalTime, List<Integer> dbTime, List<Integer> total, List<CoreVariant> variants, Error error, String status) {
        this.coreQuery = coreQuery;
        this.vsalTime = vsalTime;
        this.dbTime = dbTime;
        this.total = total;
        this.variants = variants;
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

    public List<Integer> getDbTime() {
        return dbTime;
    }

    public void setDbTime(List<Integer> dbTime) {
        this.dbTime = dbTime;
    }

    public List<Integer> getTotal() {
        return total;
    }

    public void setTotal(List<Integer> total) {
        this.total = total;
    }

    public List<CoreVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<CoreVariant> variants) {
        this.variants = variants;
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
