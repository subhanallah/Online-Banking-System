package com.cg.obs.dao;

import java.util.Date;
import java.util.List;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transaction;
import com.cg.obs.exception.NoTransactionsException;

public interface BankAdminDAO {

	String selectAdminName(String adminName);

	String selectPassword(String adminName, String password);

	int addAccountMaster(AccountMaster accountMaster);

	boolean addCustomer(Customer customer);

	boolean addUser(AccountHolder user);

	int checkPan(String panCardNo);

	AccountHolder getUser(int oldAccountId);

	List<Transaction> getTransations(Date date) throws NoTransactionsException;

	List<Transaction> getTransations(Date startDate, Date endDate) throws NoTransactionsException;

}
