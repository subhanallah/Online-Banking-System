package com.cg.obs.bean;

import java.sql.Date;

public class Service {
	private int serviceId;
	private String serviceDesc;
	private int accountId;
	private Date raiseDate;
	private String serviceStatus;
	@Override
	public String toString() {
		return "Service [serviceId=" + serviceId + ", serviceDesc=" + serviceDesc + ", accountId=" + accountId
				+ ", raiseDate=" + raiseDate + ", serviceStatus=" + serviceStatus + "]\n";
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Date getRaiseDate() {
		return raiseDate;
	}
	public void setRaiseDate(Date raiseDate) {
		this.raiseDate = raiseDate;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
}
