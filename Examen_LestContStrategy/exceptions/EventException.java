package exceptions;

public class EventException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventException() {
		super();
	}
	
	public EventException(String message) {
		super(message);
	}
	
	public EventException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EventException(Throwable cause) {
		super(cause);
	}
	
	EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}