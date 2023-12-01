package selenium4.com.exceptions;

//@SuppressWarnings("serial")
public class InvalidPathForFilesException extends FrameworkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3989105893385226497L;

	public InvalidPathForFilesException(String message) {
		super(message);
	}

	public InvalidPathForFilesException(String message, Throwable cause) {
		super(message, cause);
	}
}
