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

import au.org.garvan.vsal.core.entity.DatasetID;

public class CoreQuery {

    private DatasetID  dataset;
    private Chromosome chromosome;
    private Integer positionStart;
    private Integer positionEnd;
    private Integer limit;
    private Integer skip;
    private Boolean annot;
    private Field   sortBy;
    private Boolean descend;
    private Boolean count;
    private Allele alt;
    private Allele ref;
    private Boolean beacon;
    private String jwt;

    public CoreQuery() {
        // needed for JAXB
    }

    public CoreQuery(DatasetID dataset, Chromosome chromosome, Integer positionStart, Integer positionEnd, Integer limit, Integer skip, Boolean annot, Field sortBy, Boolean descend, Boolean count, Allele alt, Allele ref, Boolean beacon, String jwt) {
        this.dataset = dataset;
        this.chromosome = chromosome;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.limit = limit;
        this.skip = skip;
        this.annot = annot;
        this.sortBy = sortBy;
        this.descend = descend;
        this.count = count;
        this.alt = alt;
        this.ref = ref;
        this.beacon = beacon;
        this.jwt = jwt;
    }

    public DatasetID getDataset() {
        return dataset;
    }

    public void setDataset(DatasetID dataset) {
        this.dataset = dataset;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public void setChromosome(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public Integer getPositionStart() {
        return positionStart;
    }

    public void setPositionStart(Integer positionStart) {
        this.positionStart = positionStart;
    }

    public Integer getPositionEnd() {
        return positionEnd;
    }

    public void setPositionEnd(Integer positionEnd) {
        this.positionEnd = positionEnd;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Boolean getAnnot() {
        return annot;
    }

    public void setAnnot(Boolean annot) {
        this.annot = annot;
    }

    public Field getSortBy() {
        return sortBy;
    }

    public void setSortBy(Field sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescend() {
        return descend;
    }

    public void setDescend(Boolean descend) {
        this.descend = descend;
    }

    public Boolean getCount() {
        return count;
    }

    public void setCount(Boolean count) {
        this.count = count;
    }

    public Allele getAlt() {
        return alt;
    }

    public void setAlt(Allele alt) {
        this.alt = alt;
    }

    public Allele getRef() {
        return ref;
    }

    public void setRef(Allele ref) {
        this.ref = ref;
    }

    public Boolean getBeacon() {
        return beacon;
    }

    public void setBeacon(Boolean beacon) {
        this.beacon = beacon;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}