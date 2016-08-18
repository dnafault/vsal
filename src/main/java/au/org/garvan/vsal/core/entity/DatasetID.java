package au.org.garvan.vsal.core.entity;

public enum DatasetID {

    ASPREE("ASPREE");

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
