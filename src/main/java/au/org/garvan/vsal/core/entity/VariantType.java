package au.org.garvan.vsal.core.entity;

public enum VariantType {

    SNV((byte)0), MNV((byte)1), INDEL((byte)2), SV((byte)3), CNV((byte)4);

    private final Byte type;

    VariantType(Byte type) {
        this.type = type;
    }

    public static VariantType fromByte(Byte type) {
        if (type != null)
            switch (type) {
                case 0 : return VariantType.SNV;
                case 1 : return VariantType.MNV;
                case 2 : return VariantType.INDEL;
                case 3 : return VariantType.SV;
                case 4 : return VariantType.CNV;
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
                case "INDEL": return VariantType.INDEL;
                case "SV"   : return VariantType.SV;
                case "CNV"  : return VariantType.CNV;
            }
        return null;
    }
}

