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
    private Error error;

    public CoreResponse() {
        // needed for JAXB
    }

    public CoreResponse(CoreQuery coreQuery, List<CoreVariant> variants, Error error) {
        this.coreQuery = coreQuery;
        this.variants = variants;
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

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoreResponse)) return false;

        CoreResponse that = (CoreResponse) o;

        if (coreQuery != null ? !coreQuery.equals(that.coreQuery) : that.coreQuery != null) return false;
        if (variants != null ? !variants.equals(that.variants) : that.variants != null) return false;
        return error != null ? error.equals(that.error) : that.error == null;

    }

    @Override
    public int hashCode() {
        int result = coreQuery != null ? coreQuery.hashCode() : 0;
        result = 31 * result + (variants != null ? variants.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }
}
