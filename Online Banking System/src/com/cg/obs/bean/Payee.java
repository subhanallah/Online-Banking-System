package com.cg.obs.bean;

public class Payee {
	private int accountId;
	private int payeeAccountId;
	private String nickName;
	
	public Payee() {
	}
	public Payee(int accountId, int payeeAccountId, String nickName) {
		super();
		this.accountId = accountId;
		this.payeeAccountId = payeeAccountId;
		this.nickName = nickName;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getPayeeAccountId() {
		return payeeAccountId;
	}
	public void setPayeeAccountId(int payeeAccountId) {
		this.payeeAccountId = payeeAccountId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Override
	public String toString() {
		return "Payee [accountId=" + accountId + ", payeeAccountId=" + payeeAccountId + ", nickName=" + nickName + "]\n";
	}
	
}
