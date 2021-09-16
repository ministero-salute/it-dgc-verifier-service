package it.interop.dgc.verifier.exceptions;

/**
 * 
 * @author CPIERASC
 *
 *	Eccezione di validazione.
 */
public class ValidationException extends RuntimeException {

	/**
	 * Seriale.
	 */
	private static final long serialVersionUID = 5632725723070077498L;

	/**
	 * Costruttore.
	 * 
	 * @param msg	messaggio
	 */
	public ValidationException(final String msg) {
		super(msg);
	}
	
	/**
	 * Costruttore.
	 * 
	 * @param msg	messaggio
	 * @param e		eccezione
	 */
	public ValidationException(final String msg, final Exception e) {
		super(msg, e);
	}
	
	/**
	 * Costruttore.
	 * 
	 * @param e	eccezione.
	 */
	public ValidationException(final Exception e) {
		super(e);
	}
	
}
