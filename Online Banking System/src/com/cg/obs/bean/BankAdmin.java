package com.cg.obs.bean;

public class BankAdmin {

	private String adminName;
	private String password;

	public BankAdmin() {

	}

	public BankAdmin(String adminName, String password) {
		this.adminName = adminName;
		this.password = password;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
