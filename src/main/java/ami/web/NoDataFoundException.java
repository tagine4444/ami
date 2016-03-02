package ami.web;

public class NoDataFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8213843128594335559L;

	public NoDataFoundException() {
		super();
	}
	public NoDataFoundException(String message) {
		super(message);
	}
}
