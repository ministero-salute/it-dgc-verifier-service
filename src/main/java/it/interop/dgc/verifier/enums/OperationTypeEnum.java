package it.interop.dgc.verifier.enums;

/**
 * Enum per l'identificazione delle azioni da eseguire nel calcolo del diff tra due CRL.
 */
public enum OperationTypeEnum {

	/**
	 * Aggiungi il target.
	 */
    ADD("A"), 
    
    /**
     * Rimuovi il target.
     */
    REMOVE("R");

    /**
     * Type.
     */
    private String type;

    /**
     * Costruttore.
     * 
     * @param inType	descrizione tipo operazione
     */
    OperationTypeEnum(final String inType) {
        type = inType;
    }

    /**
     * Getter type.
     * 
     * @return type
     */
    public String getType() {
        return type;
    }

}
