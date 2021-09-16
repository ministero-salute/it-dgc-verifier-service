package it.interop.dgc.verifier.utils;
 

import it.interop.dgc.verifier.exceptions.ValidationException;



/**
 * 
 * @author CPIERASC
 *
 *	Validazione.
 */
public final class Validation {

	/**
	 * Costruttore.
	 */
	private Validation() {
		//Questo metodo è lasciato intenzionalmente vuoto.
	}
    
    /**
     * Metodo per verificare che lo sha256 in esadecimale sia valido.
     * 
     * @param sha256
     * @return 
     */
    public static void isValidVersion(Long version) {  
        if(version!=null && version<0) {
            throw new ValidationException("La version deve essere un numero maggiore o uguale a 0.");
        }
 
    }

    /**
	 * Metodo che valida un oggetto.
	 * 
	 * @param check
	 * @param msg
	 * 
	 */
    public static void mustBeTrue(final Boolean check, final String msg) {
        if (!check) {
			throw new ValidationException(msg);
        }
    }

}
