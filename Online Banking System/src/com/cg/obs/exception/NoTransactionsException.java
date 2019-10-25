package com.cg.obs.exception;

public class NoTransactionsException extends Exception{
	public NoTransactionsException(String message) {
		super(message);
	}
	@Override
	public String getMessage() {
		return "NoTransactionException: "+super.getMessage()+"\n";
	}
}
