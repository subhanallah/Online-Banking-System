package com.cg.obs.exception;

public class WrongPayeeAccountException extends Exception {
	public WrongPayeeAccountException(String message) {
		super(message);
	}
	@Override
	public String getMessage() {
		return "WrongPayeeAccountException: " + super.getMessage();
	}
}
