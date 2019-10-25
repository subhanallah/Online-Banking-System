package com.cg.obs.bean;

public class AccountHolder {

	private int accountId;
	private String userName;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String transactionPassword;
	private String lockStatus;

	public AccountHolder() {

	}

	
	public AccountHolder(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	

	public String getSecretAnswer() {
		return secretAnswer;
	}


	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}


	public String getSecretQuestion() {
		return secretQuestion;
	}


	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}


	public String getTransactionPassword() {
		return transactionPassword;
	}


	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}


	public String getLockStatus() {
		return lockStatus;
	}


	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}


	public AccountHolder(int accountId, String userName, String password) {
		super();
		this.accountId = accountId;
		this.userName = userName;
		this.password = password;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "AccountHolder [accountId=" + accountId + ", userName=" + userName + ", password=" + password
				+ ", secretQuestion=" + secretQuestion + ", secretAnswer=" + secretAnswer + ", transactionPassword="
				+ transactionPassword + ", lockStatus=" + lockStatus + "]\n";
	}
	
	
}
