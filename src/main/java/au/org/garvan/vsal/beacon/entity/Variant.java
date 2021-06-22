/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Dmitry Degrave
 * Copyright (c) 2019 Garvan Institute of Medical Research
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package au.org.garvan.vsal.beacon.entity;

import java.io.Serializable;

public class Variant implements Serializable {

    private static final long serialVersionUID = -8039686696076337074L;

    private String v;
    private String chr;
    private int start;
    private String ref;
    private String alt;
    private String rsid;
    private int ac;
    private Double af;
    private int nHomRef;
    private int nHet;
    private int nHomVar;
    private String type;
    private String cato;
    private String eigen;
    private String sift;
    private String polyPhen;
    private Float hrcAF;
    private Float gnomadAF;
    private Float gnomadAF_AFR;
    private Float gnomadAF_AMR;
    private Float gnomadAF_ASJ;
    private Float gnomadAF_EAS;
    private Float gnomadAF_FIN;
    private Float gnomadAF_NFE;
    private Float gnomadAF_OTHD;
    private String ensemblId;
    private String consequences;
    private String geneSymbol;
    private String clinvar;
    private String wasSplit;

    public Variant() {
        // needed for JAXB
    }

    public Variant(String v, String chr, int start, String ref, String alt, String rsid, int ac, Double af, int nHomRef, int nHet, int nHomVar, String type, String cato, String eigen, String sift, String polyPhen, Float hrcAF, Float gnomadAF, Float gnomadAF_AFR, Float gnomadAF_AMR, Float gnomadAF_ASJ, Float gnomadAF_EAS, Float gnomadAF_FIN, Float gnomadAF_NFE, Float gnomadAF_OTHD, String ensemblId, String consequences, String geneSymbol, String clinvar, String wasSplit) {
        this.v = v;
        this.chr = chr;
        this.start = start;
        this.ref = ref;
        this.alt = alt;
        this.rsid = rsid;
        this.ac = ac;
        this.af = af;
        this.nHomRef = nHomRef;
        this.nHet = nHet;
        this.nHomVar = nHomVar;
        this.type = type;
        this.cato = cato;
        this.eigen = eigen;
        this.sift = sift;
        this.polyPhen = polyPhen;
        this.hrcAF = hrcAF;
        this.gnomadAF = gnomadAF;
        this.gnomadAF_AFR = gnomadAF_AFR;
        this.gnomadAF_AMR = gnomadAF_AMR;
        this.gnomadAF_ASJ = gnomadAF_ASJ;
        this.gnomadAF_EAS = gnomadAF_EAS;
        this.gnomadAF_FIN = gnomadAF_FIN;
        this.gnomadAF_NFE = gnomadAF_NFE;
        this.gnomadAF_OTHD = gnomadAF_OTHD;
        this.ensemblId = ensemblId;
        this.consequences = consequences;
        this.geneSymbol = geneSymbol;
        this.clinvar = clinvar;
        this.wasSplit = wasSplit;
    }

    public Variant(String v, String chr, int start, String rsid, Double af, int nHet, int nHomVar) {
        this.v = v;
        this.chr = chr;
        this.start = start;
        this.rsid = rsid;
        this.af = af;
        this.nHet = nHet;
        this.nHomVar = nHomVar;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getRsid() {
        return rsid;
    }

    public void setRsid(String rsid) {
        this.rsid = rsid;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public Double getAf() {
        return af;
    }

    public void setAf(Double af) {
        this.af = af;
    }

    public int getnHomRef() {
        return nHomRef;
    }

    public void setnHomRef(int nHomRef) {
        this.nHomRef = nHomRef;
    }

    public int getnHet() {
        return nHet;
    }

    public void setnHet(int nHet) {
        this.nHet = nHet;
    }

    public int getnHomVar() {
        return nHomVar;
    }

    public void setnHomVar(int nHomVar) {
        this.nHomVar = nHomVar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCato() {
        return cato;
    }

    public void setCato(String cato) {
        this.cato = cato;
    }

    public String getEigen() {
        return eigen;
    }

    public void setEigen(String eigen) {
        this.eigen = eigen;
    }

    public String getSift() {
        return sift;
    }

    public void setSift(String sift) {
        this.sift = sift;
    }

    public String getPolyPhen() {
        return polyPhen;
    }

    public void setPolyPhen(String polyPhen) {
        this.polyPhen = polyPhen;
    }

    public Float getHrcAF() {
        return hrcAF;
    }

    public void setHrcAF(Float hrcAF) {
        this.hrcAF = hrcAF;
    }

    public Float getGnomadAF() {
        return gnomadAF;
    }

    public void setGnomadAF(Float gnomadAF) {
        this.gnomadAF = gnomadAF;
    }

    public Float getGnomadAF_AFR() {
        return gnomadAF_AFR;
    }

    public void setGnomadAF_AFR(Float gnomadAF_AFR) {
        this.gnomadAF_AFR = gnomadAF_AFR;
    }

    public Float getGnomadAF_AMR() {
        return gnomadAF_AMR;
    }

    public void setGnomadAF_AMR(Float gnomadAF_AMR) {
        this.gnomadAF_AMR = gnomadAF_AMR;
    }

    public Float getGnomadAF_ASJ() {
        return gnomadAF_ASJ;
    }

    public void setGnomadAF_ASJ(Float gnomadAF_ASJ) {
        this.gnomadAF_ASJ = gnomadAF_ASJ;
    }

    public Float getGnomadAF_EAS() {
        return gnomadAF_EAS;
    }

    public void setGnomadAF_EAS(Float gnomadAF_EAS) {
        this.gnomadAF_EAS = gnomadAF_EAS;
    }

    public Float getGnomadAF_FIN() {
        return gnomadAF_FIN;
    }

    public void setGnomadAF_FIN(Float gnomadAF_FIN) {
        this.gnomadAF_FIN = gnomadAF_FIN;
    }

    public Float getGnomadAF_NFE() {
        return gnomadAF_NFE;
    }

    public void setGnomadAF_NFE(Float gnomadAF_NFE) {
        this.gnomadAF_NFE = gnomadAF_NFE;
    }

    public Float getGnomadAF_OTHD() {
        return gnomadAF_OTHD;
    }

    public void setGnomadAF_OTHD(Float gnomadAF_OTHD) {
        this.gnomadAF_OTHD = gnomadAF_OTHD;
    }

    public String getEnsemblId() {
        return ensemblId;
    }

    public void setEnsemblId(String ensemblId) {
        this.ensemblId = ensemblId;
    }

    public String getConsequences() {
        return consequences;
    }

    public void setConsequences(String consequences) {
        this.consequences = consequences;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getClinvar() {
        return clinvar;
    }

    public void setClinvar(String clinvar) {
        this.clinvar = clinvar;
    }

    public String getWasSplit() {
        return wasSplit;
    }

    public void setWasSplit(String wasSplit) {
        this.wasSplit = wasSplit;
    }
}