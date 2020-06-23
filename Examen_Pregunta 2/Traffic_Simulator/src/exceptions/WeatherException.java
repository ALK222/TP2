package exceptions;

public class WeatherException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public WeatherException() {
		super();
	}
	
	public WeatherException(String message) {
		super(message);
	}
	
	public WeatherException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public WeatherException(Throwable cause) {
		super(cause);
	}
	
	WeatherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
