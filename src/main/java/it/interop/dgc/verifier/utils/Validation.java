package it.interop.dgc.verifier.utils;
 

import it.interop.dgc.verifier.exceptions.ValidationException;



/**
 * 
 * @author CPIERASC
 *
 */
public final class Validation {

	private Validation() {
	}
    
    /**
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
