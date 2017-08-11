package au.org.garvan.vsal.core.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CoreVariant")
public class CoreVariant {

    private String  c; // chromosome
    private Integer s; // start
    private String rs; // dbSNP

    private String a; // alternate
    private String r; // reference
    private String t; // type

    // cohort-wide alt allele stats
    private Integer     ac; // alt allele count
    private Float       af; // alt allele freq
    private Integer   homc; // alt allele hom count
    private Integer   hetc; // alt allele het count

    // virtual cohort-wide alt allele stats
    private Integer     vac; // alt allele count
    private Float       vaf; // alt allele freq
    private Integer   vhomc; // alt allele hom count
    private Integer   vhetc; // alt allele het count

    public CoreVariant() {
        // needed for JAXB
    }

    public CoreVariant(String c, Integer s, String rs, String a, String r, String t, Integer ac, Float af, Integer homc, Integer hetc, Integer vac, Float vaf, Integer vhomc, Integer vhetc) {
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
        this.vac = vac;
        this.vaf = vaf;
        this.vhomc = vhomc;
        this.vhetc = vhetc;
    }

    // Chapter 30 "Object Equality", "Programming in Scala" 3rd ed
    private boolean canEqual(Object o) {
        return o instanceof CoreVariant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!this.canEqual(o)) return false;
        CoreVariant that = (CoreVariant) o;
        if (!c.equals(that.c)) return false;
        if (!s.equals(that.s)) return false;
        if (!a.equals(that.a)) return false;
        return r.equals(that.r);
    }

    @Override
    public int hashCode() {
        int result = c.hashCode();
        result = 31 * result + s.hashCode();
        result = 31 * result + a.hashCode();
        result = 31 * result + r.hashCode();
        return result;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
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

    public Integer getAc() {
        return ac;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }

    public Float getAf() {
        return af;
    }

    public void setAf(Float af) {
        this.af = af;
    }

    public Integer getHomc() {
        return homc;
    }

    public void setHomc(Integer homc) {
        this.homc = homc;
    }

    public Integer getHetc() {
        return hetc;
    }

    public void setHetc(Integer hetc) {
        this.hetc = hetc;
    }

    public Integer getVac() {
        return vac;
    }

    public void setVac(Integer vac) {
        this.vac = vac;
    }

    public Float getVaf() {
        return vaf;
    }

    public void setVaf(Float vaf) {
        this.vaf = vaf;
    }

    public Integer getVhomc() {
        return vhomc;
    }

    public void setVhomc(Integer vhomc) {
        this.vhomc = vhomc;
    }

    public Integer getVhetc() {
        return vhetc;
    }

    public void setVhetc(Integer vhetc) {
        this.vhetc = vhetc;
    }
}