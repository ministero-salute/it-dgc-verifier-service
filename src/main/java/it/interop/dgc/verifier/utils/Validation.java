package it.interop.dgc.verifier.utils;

import it.interop.dgc.verifier.exceptions.DgcaBusinessRulesResponseException;
import it.interop.dgc.verifier.exceptions.ValidationException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author CPIERASC
 *
 */
public final class Validation {

    private Validation() {}

    /**
     *
     * @param sha256
     * @return
     */
    public static void isValidVersion(Long version) {
        if (version != null && version < 0) {
            throw new DgcaBusinessRulesResponseException(
                HttpStatus.BAD_REQUEST,
                "0x004",
                "La version deve essere un numero maggiore o uguale a 0.",
                null,
                null
            );
        }
    }
    
    /**
    *
    * @param sha256
    * @return
    */
   public static void isValidChunk(Integer chunk) {
       if (chunk != null && chunk < 0) {
           throw new DgcaBusinessRulesResponseException(
               HttpStatus.BAD_REQUEST,
               "0x004",
               "Il chunk deve essere un numero maggiore o uguale a 0.",
               null,
               null
           );
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
