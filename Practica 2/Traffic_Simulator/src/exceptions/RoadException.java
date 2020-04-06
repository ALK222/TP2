package exceptions;

public class RoadException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4298467873659627912L;
	
	public RoadException() {
		super();
	}
	
	public RoadException(String message) {
		super(message);
	}
	
	public RoadException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RoadException(Throwable cause) {
		super(cause);
	}
	
	RoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
