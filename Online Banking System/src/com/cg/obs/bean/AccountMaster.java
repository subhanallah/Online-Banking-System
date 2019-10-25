package com.cg.obs.bean;

import java.util.Date;

public class AccountMaster {
	private int accountId;
	private String accountType;
	private double openingBalance;
	private Date openDate;
	public AccountMaster(String accountType, double openingBalance, Date openDate) {
		super();
		
		this.accountType = accountType;
		this.openingBalance = openingBalance;
		this.openDate = openDate;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	@Override
	public String toString() {
		return "AccountMaster [accountId=" + accountId + ", accountType=" + accountType + ", openingBalance="
				+ openingBalance + ", openDate=" + openDate + "]";
	}
	
	
}
