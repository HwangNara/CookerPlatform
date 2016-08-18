package cooker.core.debug;

public class CookerException extends Exception {

	private static final long serialVersionUID = 4331300479407363601L;

	public CookerException(){
		
	}
	
	public CookerException(String message){
		super(message);
	}
	
	public CookerException(String format, Object... args){
		super(String.format(format, args));
	}
}
