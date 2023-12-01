package selenium4.com.exceptions;

/**
 * FrameworkException extends RuntimeException - because I want to terminate the
 * program when the Exception comes
 */
public class FrameworkException extends RuntimeException {

	/**
	 * https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
	 */
	private static final long serialVersionUID = -2206091500964520463L;

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}
}
