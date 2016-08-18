package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "QueryResponse")
public class QueryResponse<T> {

    // see org.opencb.datastore.core.QueryResponse for data structure

    private String apiVersion;
    private String warning;
    private String error;

    private Map<String, Object> queryOptions;
    private List<OcgaResponse<T>> response;

    public QueryResponse() {
        // needed for JAXB
    }

    public QueryResponse(String apiVersion, String warning, String error, Map<String, Object> queryOptions, List<OcgaResponse<T>> response) {
        this.apiVersion = apiVersion;
        this.warning = warning;
        this.error = error;
        this.queryOptions = queryOptions;
        this.response = response;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, Object> getQueryOptions() {
        return queryOptions;
    }

    public void setQueryOptions(Map<String, Object> queryOptions) {
        this.queryOptions = queryOptions;
    }

    public List<OcgaResponse<T>> getResponse() {
        return response;
    }

    public void setResponse(List<OcgaResponse<T>> response) {
        this.response = response;
    }
}
