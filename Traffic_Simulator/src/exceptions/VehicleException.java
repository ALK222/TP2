package exceptions;

public class VehicleException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1836085095259509945L;


	public VehicleException() {
		super();
	}
	
	public VehicleException(String message) {
		super(message);
	}
	
	public VehicleException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public VehicleException(Throwable cause) {
		super(cause);
	}
	
	VehicleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

