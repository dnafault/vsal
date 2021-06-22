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

public enum Field {

    FIELD01("v"), FIELD02("chr"), FIELD03("start"), FIELD04("ref"), FIELD05("alt"),
    FIELD06("rsid"), FIELD07("ac"), FIELD08("af"), FIELD09("nHomRef"), FIELD10("nHet"),
    FIELD11("nHomVar"), FIELD12("type"), FIELD13("cato"), FIELD14("eigen"), FIELD15("sift"),
    FIELD16("polyPhen"), FIELD17("hrcAF"), FIELD18("gnomadAF"), FIELD19("gnomadAF_AFR"), FIELD20("gnomadAF_AMR"),
    FIELD21("gnomadAF_ASJ"), FIELD22("gnomadAF_EAS"), FIELD23("gnomadAF_FIN"), FIELD24("gnomadAF_NFE"), FIELD25("gnomadAF_OTHD"),
    FIELD26("ensemblId"), FIELD27("consequences"), FIELD28("geneSymbol"), FIELD29("clinvar"), FIELD30("wasSplit");

    private final String field;

    Field(String field) {
        this.field = field;
    }

    public static Field fromString(String text) {
        if (text != null) {
            for (Field b : Field.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    return b;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return field;
    }
}