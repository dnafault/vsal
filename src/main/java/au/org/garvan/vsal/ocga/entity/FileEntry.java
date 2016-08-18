package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement(name = "FileEntry")
public class FileEntry {

    private String fileId;
    private String call;
    private Map<String,String> attributes;

    public FileEntry() {
        // needed for JAXB
    }

    public FileEntry(String fileId, String call, Map<String, String> attributes) {
        this.fileId = fileId;
        this.call = call;
        this.attributes = attributes;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}