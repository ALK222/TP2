package exceptions;

public class StrategyException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 123456789L;


	public StrategyException() {
		super();
	}
	
	public StrategyException(String message) {
		super(message);
	}
	
	public StrategyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public StrategyException(Throwable cause) {
		super(cause);
	}
	
	StrategyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
