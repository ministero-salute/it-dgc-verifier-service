package it.interop.dgc.verifier.enums;
 
/**
 * 
 * @author vincenzoingenito
 * 
 * Enum per identificare il tipo di notifica.
 */
public enum NotifyTypeEnum {

    /**
     * Campo servizio.
     */
    REVOCA("R"),  
    
    /**
     * Campo start time.
     */
    UNDO_REVOCA("U");
    
     
    /**
     * Nome campo.
     */
    private String name;

  
    /**
     * Costruttore.
     */
    NotifyTypeEnum(final String inName) {
        name = inName; 
    }

    public String getName() {
        return name;
    }

  
     
}