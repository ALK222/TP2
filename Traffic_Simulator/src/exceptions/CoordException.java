

package exceptions;

public class CoordException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 00000002L;


	public CoordException() {
		super();
	}
	
	public CoordException(String message) {
		super(message);
	}
	
	public CoordException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CoordException(Throwable cause) {
		super(cause);
	}
	
	CoordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
