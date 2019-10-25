package com.cg.obs.service;

import java.util.Date;
import java.util.List;

import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transaction;
import com.cg.obs.exception.NoTransactionsException;

public interface BankAdminService {

	boolean validateAdminName(String adminName);

	boolean validatePassword(String adminName, String password);

	boolean createAccount(Customer customer, AccountMaster accountMaster);

	List<Transaction> displayTransactions(Date date) throws NoTransactionsException;

	List<Transaction> displayTransactions(Date startDate, Date endDate) throws NoTransactionsException;

}
