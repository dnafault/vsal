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

public enum DatasetID {

    ASPREE("ASPREE"), MGRB("MGRB"), MITO("MITO"), NEURO("NEUROMUSCULAR"), AC("ACUTECARE"), BM("BM"), EE("EE"),
    ICCON("ICCON"), LD("LEUKODYSTROPHIES"), DEMO("DEMO");

    private final String dataset;

    DatasetID(String dataset) {
        this.dataset = dataset;
    }

    public static DatasetID fromString(String text) {
        if (text != null) {
            for (DatasetID v : DatasetID.values()) {
                if (text.equalsIgnoreCase(v.toString())) {
                    return v;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return dataset;
    }
}