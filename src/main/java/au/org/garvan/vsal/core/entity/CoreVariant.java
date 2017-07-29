package au.org.garvan.vsal.core.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CoreVariant")
public class CoreVariant {

    private String c; // chromosome
    private int s; // start
    private String rs; // dbSNP

    private String a; // alternate
    private String r; // reference
    private String t; // type

    // cohort-wide alt allele stats
    private int   ac; // cohort-wide alt allele count
    private float af; // cohort-wide alt allele freq
    private int   homc; // cohort-wide alt allele hom count
    private int   hetc; // cohort-wide alt allele het count

    public CoreVariant() {
        // needed for JAXB
    }

    public CoreVariant(String c, int s, String rs, String a, String r, String t, int ac, float af, int homc, int hetc) {
        this.c = c;
        this.s = s;
        this.rs = rs;
        this.a = a;
        this.r = r;
        this.t = t;
        this.ac = ac;
        this.af = af;
        this.homc = homc;
        this.hetc = hetc;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public float getAf() {
        return af;
    }

    public void setAf(float af) {
        this.af = af;
    }

    public int getHomc() {
        return homc;
    }

    public void setHomc(int homc) {
        this.homc = homc;
    }

    public int getHetc() {
        return hetc;
    }

    public void setHetc(int hetc) {
        this.hetc = hetc;
    }
}