package com.cg.obs.exception;

public class LowBalanceException extends Exception {
	public LowBalanceException(String message) {
		super(message);
	}
	@Override
	public String getMessage() {
		return "LowBalanceException: "+super.getMessage();
	}
}
