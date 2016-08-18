package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "StudyEntry")
public class StudyEntry {

    private List<String> format;
    private List<FileEntry> files;
    private List<LinkedList<String>> samplesData;
    private String studyId;
    private List<AlternateCoordinate> secondaryAlternates;
    private Map<String, VariantStats> stats;

    public StudyEntry() {
        // needed for JAXB
    }

    public StudyEntry(List<String> format, List<FileEntry> files, List<LinkedList<String>> samplesData, String studyId, List<AlternateCoordinate> secondaryAlternates, Map<String, VariantStats> stats) {
        this.format = format;
        this.files = files;
        this.samplesData = samplesData;
        this.studyId = studyId;
        this.secondaryAlternates = secondaryAlternates;
        this.stats = stats;
    }

    public List<String> getFormat() {
        return format;
    }

    public void setFormat(List<String> format) {
        this.format = format;
    }

    public List<FileEntry> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntry> files) {
        this.files = files;
    }

    public List<LinkedList<String>> getSamplesData() {
        return samplesData;
    }

    public void setSamplesData(List<LinkedList<String>> samplesData) {
        this.samplesData = samplesData;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public List<AlternateCoordinate> getSecondaryAlternates() {
        return secondaryAlternates;
    }

    public void setSecondaryAlternates(List<AlternateCoordinate> secondaryAlternates) {
        this.secondaryAlternates = secondaryAlternates;
    }

    public Map<String, VariantStats> getStats() {
        return stats;
    }

    public void setStats(Map<String, VariantStats> stats) {
        this.stats = stats;
    }
}