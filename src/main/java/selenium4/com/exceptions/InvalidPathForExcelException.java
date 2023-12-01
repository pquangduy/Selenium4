package selenium4.com.exceptions;

public class InvalidPathForExcelException extends InvalidPathForFilesException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4705686229243826790L;

	/**
	 * Pass the message that needs to be appended to the stacktrace
	 * 
	 * @param message Details about the exception or custom message
	 */
	public InvalidPathForExcelException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message Details about the exception or custom message
	 * @param cause   Pass the enriched stacktrace or customised stacktrace
	 */
	public InvalidPathForExcelException(String message, Throwable cause) {
		super(message, cause);
	}
}
