package com.cg.obs.exception;

public class InvalidPasswordException extends Exception{
	
	public InvalidPasswordException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
}
