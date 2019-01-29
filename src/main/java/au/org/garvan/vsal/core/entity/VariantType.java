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

public enum VariantType {

    SNV((byte)83), MNV((byte)77), INS((byte)105), DEL((byte)100), INDEL((byte)73), SV((byte)115), COMPLEX((byte)99);

    private final Byte type;

    VariantType(Byte type) {
        this.type = type;
    }

    public static VariantType fromByte(Byte type) {
        if (type != null)
            switch (type) {
                case 83  : return VariantType.SNV;    // S
                case 77  : return VariantType.MNV;    // M
                case 105 : return VariantType.INS;    // i
                case 100 : return VariantType.DEL;    // d
                case 73  : return VariantType.INDEL;  // I
                case 115 : return VariantType.SV;     // s
                case 99  : return VariantType.COMPLEX;// c
            }
        return null;
    }

    public Byte toByte() {
        return type;
    }

    /**
     * A method can be used instead of valueOf() to simplify client's API
     * @param text String to convert to VariantType, case insensitive
     * @return VariantType
     */
    public static VariantType fromString(String text) {
        if (text != null)
            switch (text.toUpperCase()) {
                case "SNV"  : return VariantType.SNV;
                case "SNP"  : return VariantType.SNV;
                case "MNV"  : return VariantType.MNV;
                case "INS"  : return VariantType.INS;
                case "DEL"  : return VariantType.DEL;
                case "INDEL": return VariantType.INDEL;
                case "SV"   : return VariantType.SV;
                case "COMPLEX"  : return VariantType.COMPLEX;
            }
        return null;
    }
}