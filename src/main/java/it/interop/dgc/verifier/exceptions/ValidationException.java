package it.interop.dgc.verifier.exceptions;

/**
 *
 * @author CPIERASC
 *
 */
public class ValidationException extends RuntimeException {

    /**
     * Serial.
     */
    private static final long serialVersionUID = 5632725723070077498L;

    public ValidationException(final String msg) {
        super(msg);
    }

    public ValidationException(final String msg, final Exception e) {
        super(msg, e);
    }

    public ValidationException(final Exception e) {
        super(e);
    }
}
