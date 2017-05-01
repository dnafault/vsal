package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "OcgaResponse")
public class OcgaResponse<T> {

    private String id;
    private int time;
    private int dbTime;
    private int numResults;
    private long numTotalResults;

    private String warningMsg;
    private String errorMsg;

    private String resultType;
    private List<T> result;

    public OcgaResponse() {
        // needed for JAXB
    }

    public OcgaResponse(String id, int time, int dbTime, int numResults, long numTotalResults, String warningMsg, String errorMsg, String resultType, List<T> result) {
        this.id = id;
        this.time = time;
        this.dbTime = dbTime;
        this.numResults = numResults;
        this.numTotalResults = numTotalResults;
        this.warningMsg = warningMsg;
        this.errorMsg = errorMsg;
        this.resultType = resultType;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDbTime() {
        return dbTime;
    }

    public void setDbTime(int dbTime) {
        this.dbTime = dbTime;
    }

    public int getNumResults() {
        return numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    public long getNumTotalResults() {
        return numTotalResults;
    }

    public void setNumTotalResults(long numTotalResults) {
        this.numTotalResults = numTotalResults;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}