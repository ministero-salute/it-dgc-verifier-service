package it.interop.dgc.verifier.exceptions;

/**
 *
 * @author CPIERASC
 *
 */
public class BusinessException extends RuntimeException {

    /**
     * Serial.
     */
    private static final long serialVersionUID = 5632725723070077498L;

    public BusinessException(final String msg) {
        super(msg);
    }

    public BusinessException(final String msg, final Exception e) {
        super(msg, e);
    }

    public BusinessException(final Exception e) {
        super(e);
    }
}
