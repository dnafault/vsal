package au.org.garvan.vsal.core.entity;

public enum Gender {

    FEMALE("FEMALE"), MALE("MALE");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public static Gender fromString(String text) {
        if (text != null) {
            for (Gender v : Gender.values()) {
                if (text.equalsIgnoreCase(v.toString())) {
                    return v;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return gender;
    }
}
