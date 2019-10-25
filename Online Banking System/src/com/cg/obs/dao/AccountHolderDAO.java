package com.cg.obs.dao;

import java.util.Date;
import java.util.List;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Service;
import com.cg.obs.bean.Transaction;
import com.cg.obs.exception.NoTransactionsException;

public interface AccountHolderDAO {

	String selectUserName(String userName);

	AccountHolder selectPassword(String userName, String password);

	List<Payee> selectAllPayee(int accountId);

	double getAmount(int accountId);

	String selectPayee(int accountId, int payeeAccountId);

	boolean withdraw(int accountId, double amount);

	boolean deposit(int accountId, double amount);

	boolean addPayee(Payee payee);

	void connectionCommit();

	void addFundTransfer(Payee payee, double amount);

	List<Integer> getAllAccounts(String userName);

	List<Transaction> selectTentransactions(int accountNo) throws NoTransactionsException;

	List<Transaction> selectTransactionsInDuration(int accountNo, Date startDate, Date endDate) throws NoTransactionsException;

	int updateMobileNo(int accountNo, String mobileNo);

	int updateAddress(int accountNo, String address);

	int addRequest(int accountNo, int numberOfPages);

	int updatePass(String userName, String newPass);

	List<Service> trackServiceRequest(AccountHolder accountHolder);

}
