package it.interop.dgc.verifier.enums;

public enum OperationTypeEnum {

    ADD("A"), 
    
    REMOVE("R");

    private String type;

    OperationTypeEnum(final String inType) {
        type = inType;
    }

    public String getType() {
        return type;
    }

}
