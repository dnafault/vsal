package au.org.garvan.vsal.beacon.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ssvs-beacon-response")
public class BeaconResponseSSVS {

    private String allele;
    private Double af;
    private Integer ac;

    public BeaconResponseSSVS() {
        // needed for JAXB
    }

    public BeaconResponseSSVS(String allele, Double af, Integer ac) {
        this.allele = allele;
        this.af = af;
        this.ac = ac;
    }

    public String getAllele() {
        return allele;
    }

    public void setAllele(String allele) {
        this.allele = allele;
    }

    public Double getAf() {
        return af;
    }

    public void setAf(Double af) {
        this.af = af;
    }

    public Integer getAc() {
        return ac;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }
}