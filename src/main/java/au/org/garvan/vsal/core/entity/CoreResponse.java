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
    private List<CoreVariant> variants;
    private List<Long> total;
    private Error error;

    public CoreResponse() {
        // needed for JAXB
    }

    public CoreResponse(CoreQuery coreQuery, List<CoreVariant> variants, List<Long> total, Error error) {
        this.coreQuery = coreQuery;
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

    public List<CoreVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<CoreVariant> variants) {
        this.variants = variants;
    }

    public List<Long> getTotal() {
        return total;
    }

    public void setTotal(List<Long> total) {
        this.total = total;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
