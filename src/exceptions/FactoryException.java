package exceptions;

public class FactoryException extends Exception{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FactoryException() {
		super();
	}
	
	public FactoryException(String message) {
		super(message);
	}
	
	public FactoryException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FactoryException(Throwable cause) {
		super(cause);
	}
	
	FactoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
