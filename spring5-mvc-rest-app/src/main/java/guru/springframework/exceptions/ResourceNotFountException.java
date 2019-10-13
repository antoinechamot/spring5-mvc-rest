package guru.springframework.exceptions;

public class ResourceNotFountException extends RuntimeException{

	
	
	public ResourceNotFountException() {
		
	}
	
	public ResourceNotFountException(String message) {
		super(message);
	}
	
	public ResourceNotFountException(String message,Throwable cause) {
		super(message,cause);
	}
	
	public ResourceNotFountException(Throwable cause) {
		super(cause);
	}
	
	
	public ResourceNotFountException(String message,Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
	}
	
	
}
