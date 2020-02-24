	package exceptions;

	public class JunctionException extends Exception{

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 000000003L;


		public JunctionException() {
			super();
		}
		
		public JunctionException(String message) {
			super(message);
		}
		
		public JunctionException(String message, Throwable cause) {
			super(message, cause);
		}
		
		public JunctionException(Throwable cause) {
			super(cause);
		}
		
		JunctionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
			super(message, cause, enableSuppression, writableStackTrace);
		}
	}
