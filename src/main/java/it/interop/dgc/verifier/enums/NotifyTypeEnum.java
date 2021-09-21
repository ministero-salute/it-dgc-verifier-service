package it.interop.dgc.verifier.enums;

public enum NotifyTypeEnum {
    REVOCA("R"),

    UNDO_REVOCA("U");

    private String name;

    NotifyTypeEnum(final String inName) {
        name = inName;
    }

    public String getName() {
        return name;
    }
}
