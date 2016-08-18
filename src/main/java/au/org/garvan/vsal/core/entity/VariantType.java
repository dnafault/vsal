package au.org.garvan.vsal.core.entity;

public enum VariantType {

    SNV("SNV"), MNV("MNV"), INDEL("INDEL"), SV("SV"), CNV("CNV");

    private final String type;

    VariantType(String type) {
        this.type = type;
    }

    public static VariantType fromString(String text) {
        if (text != null) {
            for (VariantType v : VariantType.values()) {
                if (text.equalsIgnoreCase(v.toString())) {
                    return v;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type;
    }
}

