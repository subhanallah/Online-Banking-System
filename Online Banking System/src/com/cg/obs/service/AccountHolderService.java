package com.cg.obs.service;

import java.util.Date;
import java.util.List;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Service;
import com.cg.obs.bean.Transaction;
import com.cg.obs.exception.InvalidPasswordException;
import com.cg.obs.exception.InvalidUserNameException;
import com.cg.obs.exception.LowBalanceException;
import com.cg.obs.exception.NoTransactionsException;
import com.cg.obs.exception.WrongPayeeAccountException;

public interface AccountHolderService {
	
	//public boolean login(AccountHolder accountHolder) throws InvalidUserNameException, InvalidPasswordException;

	public boolean validateUserName(String userName);
	
	public int validatePassword(String userName, String password);

	public List<Payee> getPayeeList(int accountId);

	public boolean fundTransfer(int accountId, Payee payee, double amount) throws LowBalanceException, WrongPayeeAccountException;

	public boolean addPayee(Payee payee);

	public List<Integer> getAssociatedAccount(String userName);

	public List<Transaction> getMiniStatement(int accountNo) throws NoTransactionsException;

	public List<Transaction> getDetailedTransactions(int accountNo, Date startDate, Date endDate) throws NoTransactionsException;

	public boolean changeMobileNo(int accountNo, String mobileNo);

	public boolean changeAddress(int accountNo, String address);

	public boolean chequeBookRequest(int accountNo, int numberOfPages);

	public boolean changePass(String userName, String newPass);

	public List<Service> trackServiceRequest(AccountHolder accountHolder);
	
	
}
