

package exceptions;

public class SimulatorException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 00000003L;


	public SimulatorException() {
		super();
	}
	
	public SimulatorException(String message) {
		super(message);
	}
	
	public SimulatorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SimulatorException(Throwable cause) {
		super(cause);
	}
	
	SimulatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}