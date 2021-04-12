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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class POSTParamsJaxBean {
    @XmlElement public String chromosome;
    @XmlElement public String positionStart;
    @XmlElement public String positionEnd;
    @XmlElement public String refAllele;
    @XmlElement public String altAllele;
    @XmlElement public Boolean hom;
    @XmlElement public Boolean het;
    @XmlElement public String dataset;
    @XmlElement public List<String> dbSNP;
    @XmlElement public String type;
    @XmlElement public Integer limit;
    @XmlElement public Integer skip;
    @XmlElement public String jwt;
    @XmlElement public String samples;
    @XmlElement public Boolean conj;
    @XmlElement public Boolean selectSamplesByGT;
    @XmlElement public Boolean returnAnnotations;
    @XmlElement public Boolean pheno;
    @XmlElement public Boolean genelist;
    @XmlElement public Boolean hwe;
    @XmlElement public Boolean chi2;
    @XmlElement public String ref;
}