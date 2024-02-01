package exceptions;

public class IllegalInputParameters extends Exception {
	public IllegalInputParameters(String message) {
		super(message);
	}

	public IllegalInputParameters(String message, Throwable cause) {
		super(message, cause);
	}
}
