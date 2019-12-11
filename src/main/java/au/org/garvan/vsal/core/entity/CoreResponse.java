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

package au.org.garvan.vsal.core.entity;

import au.org.garvan.vsal.beacon.entity.Error;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * CoreResponse.
 */
@XmlRootElement(name = "CoreResponse")
public class CoreResponse {

    private CoreQuery coreQuery;
    private Long vsalTimeMs;  // ms
    private Long dbTimeMs;    // ms
    private int vcSize; // virtual cohort size
    private List<CoreVariant> v;
    private int total;  // total # variants
    private List<String> sampleIDs;
    private String pheno;
    private String genelist;
    private Error error;
    private String status;

    public CoreResponse() {
        // needed for JAXB
    }

    public CoreResponse(CoreQuery coreQuery, Long vsalTimeMs, Error error) {
        this.coreQuery = coreQuery;
        this.vsalTimeMs = vsalTimeMs;
        this.error = error;
    }

    public CoreResponse(CoreQuery coreQuery, Long vsalTimeMs, Long dbTimeMs, int vcSize, List<CoreVariant> v, int total, List<String> sampleIDs, String pheno, String genelist, Error error, String status) {
        this.coreQuery = coreQuery;
        this.vsalTimeMs = vsalTimeMs;
        this.dbTimeMs = dbTimeMs;
        this.vcSize = vcSize;
        this.v = v;
        this.total = total;
        this.sampleIDs = sampleIDs;
        this.pheno = pheno;
        this.genelist = genelist;
        this.error = error;
        this.status = status;
    }

    public CoreQuery getCoreQuery() {
        return coreQuery;
    }

    public void setCoreQuery(CoreQuery coreQuery) {
        this.coreQuery = coreQuery;
    }

    public Long getVsalTimeMs() {
        return vsalTimeMs;
    }

    public void setVsalTimeMs(Long vsalTimeMs) {
        this.vsalTimeMs = vsalTimeMs;
    }

    public Long getDbTimeMs() {
        return dbTimeMs;
    }

    public void setDbTimeMs(Long dbTimeMs) {
        this.dbTimeMs = dbTimeMs;
    }

    public int getVcSize() {
        return vcSize;
    }

    public void setVcSize(int vcSize) {
        this.vcSize = vcSize;
    }

    public List<CoreVariant> getV() {
        return v;
    }

    public void setV(List<CoreVariant> v) {
        this.v = v;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getSampleIDs() {
        return sampleIDs;
    }

    public void setSampleIDs(List<String> sampleIDs) {
        this.sampleIDs = sampleIDs;
    }

    public String getPheno() {
        return pheno;
    }

    public void setPheno(String pheno) {
        this.pheno = pheno;
    }

    public String getGenelist() {
        return genelist;
    }

    public void setGenelist(String genelist) {
        this.genelist = genelist;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}