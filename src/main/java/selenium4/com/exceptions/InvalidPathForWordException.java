package selenium4.com.exceptions;

public class InvalidPathForWordException extends InvalidPathForFilesException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3537395446306004298L;

	/**
	 * Pass the message that needs to be appended to the stack trace
	 * 
	 * @param message Details about the exception or custom message
	 */
	public InvalidPathForWordException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message Details about the exception or custom message
	 * @param cause   Pass the enriched stack trace or customised stack trace
	 */
	public InvalidPathForWordException(String message, Throwable cause) {
		super(message, cause);
	}
}
