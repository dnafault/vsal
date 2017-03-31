package au.org.garvan.vsal.ocga.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VASOTerms")
public class VASOTerms {

    private String accession;
    private String name;

    public VASOTerms() {
        // needed for JAXB
    }

    public VASOTerms(String accession, String name) {
        this.accession = accession;
        this.name = name;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
