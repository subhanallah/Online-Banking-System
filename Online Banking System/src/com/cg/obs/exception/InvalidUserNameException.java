package com.cg.obs.exception;

public class InvalidUserNameException extends Exception{
	
	public InvalidUserNameException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
