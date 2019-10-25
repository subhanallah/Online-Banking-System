package com.cg.obs.bean;

import java.sql.Date;

public class Transaction {
	private int transactionId;
	private String tranDescription;
	private Date dateofTransaction;
	private String transactionType;
	private double tranAmount;
	private int accountId;
	
	
	public int getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}


	public String getTranDescription() {
		return tranDescription;
	}


	public void setTranDescription(String tranDescription) {
		this.tranDescription = tranDescription;
	}


	public Date getDateofTransaction() {
		return dateofTransaction;
	}


	public void setDateofTransaction(Date dateofTransaction) {
		this.dateofTransaction = dateofTransaction;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public double getTranAmount() {
		return tranAmount;
	}


	public void setTranAmount(double tranAmount) {
		this.tranAmount = tranAmount;
	}


	public int getAccountId() {
		return accountId;
	}


	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}


	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", tranDescription=" + tranDescription
				+ ", dateofTransaction=" + dateofTransaction + ", transactionType=" + transactionType + ", tranAmount="
				+ tranAmount + ", accountId=" + accountId + "]\n";
	}
	
	
}
