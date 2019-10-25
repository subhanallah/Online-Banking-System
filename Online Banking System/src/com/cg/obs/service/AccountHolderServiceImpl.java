package com.cg.obs.service;

import java.util.Date;
import java.util.List;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Service;
import com.cg.obs.bean.Transaction;
import com.cg.obs.dao.AccountHolderDAO;
import com.cg.obs.dao.AccountHolderDAOImpl;
import com.cg.obs.exception.LowBalanceException;
import com.cg.obs.exception.NoTransactionsException;
import com.cg.obs.exception.WrongPayeeAccountException;

public class AccountHolderServiceImpl implements AccountHolderService {

	private AccountHolderDAO accountHolderDAO = new AccountHolderDAOImpl();
	/*
	@Override
	public boolean login(AccountHolder accountHolder) throws InvalidUserNameException, InvalidPasswordException {
		
		try {
		accountHolderDAO.selectUserName(accountHolder.getUserName());
		if(accountHolder.getPassword() != accountHolderDAO.selectPassword(accountHolder.getUserName())) {
			throw new InvalidPasswordException("Password does not match");
		}
		else 
			return true;
		}
		catch(InvalidUserNameException ex) {
			throw ex;
		}
		catch(InvalidPasswordException ex) {
			throw ex;
		}
		
	}*/

	@Override
	public boolean validateUserName(String userName) {
		String result = accountHolderDAO.selectUserName(userName);
		if(result != null && result.equals(userName))
			return true;
		else
			return false;
	}

	@Override
	public int validatePassword(String userName, String password) {
		AccountHolder result = accountHolderDAO.selectPassword(userName, password);
		if(result != null && result.getAccountId() != 0 && result.getPassword().equals(password))
			return result.getAccountId();
		else
			return 0;
	}

	@Override
	public List<Payee> getPayeeList(int accountId) {
		
		return accountHolderDAO.selectAllPayee(accountId);
	}

	@Override
	public boolean fundTransfer(int accountId, Payee payee, double amount) throws LowBalanceException, WrongPayeeAccountException {
		double availableBalance = accountHolderDAO.getAmount(accountId);
		System.out.println("Available balance: "+ availableBalance);
		String checkPayee = accountHolderDAO.selectPayee(accountId, payee.getPayeeAccountId());
		System.out.println("Nuck Name: " + checkPayee);
		if(checkPayee == null || !payee.getNickName().equals(checkPayee) ){
			throw new WrongPayeeAccountException("You entered wrong payee account");
		}
		if(availableBalance < amount)
			throw new LowBalanceException("You do not have sufficient balance.");
		else {
			if(accountHolderDAO.withdraw(accountId, amount)) {
				System.out.println("Check1");
				if(accountHolderDAO.deposit(payee.getPayeeAccountId(), amount)) {
					System.out.println("check2");
					accountHolderDAO.addFundTransfer(payee, amount);
					accountHolderDAO.connectionCommit();
					return true;
				}
				
			}
		}
		return false;
	}

	@Override
	public boolean addPayee(Payee payee) {
		if(accountHolderDAO.addPayee(payee)) {
			accountHolderDAO.connectionCommit();
			return true;
		}
		return false;
	}

	@Override
	public List<Integer> getAssociatedAccount(String userName) {
		
		return accountHolderDAO.getAllAccounts(userName);
	}

	@Override
	public List<Transaction> getMiniStatement(int accountNo) throws NoTransactionsException {
		
		return accountHolderDAO.selectTentransactions(accountNo);
	}

	@Override
	public List<Transaction> getDetailedTransactions(int accountNo, Date startDate, Date endDate) throws NoTransactionsException {
		return accountHolderDAO.selectTransactionsInDuration(accountNo, startDate, endDate);
	}

	@Override
	public boolean changeMobileNo(int accountNo, String mobileNo) {
		int result = accountHolderDAO.updateMobileNo(accountNo, mobileNo);
		if(result == 1) {
			accountHolderDAO.connectionCommit();
			return true;}
		else
			return false;
	}

	@Override
	public boolean changeAddress(int accountNo, String address) {
		int result = accountHolderDAO.updateAddress(accountNo, address);
		if(result == 1)
		{
			accountHolderDAO.connectionCommit();
			return true;}
		else
			return false;
	}

	@Override
	public boolean chequeBookRequest(int accountNo, int numberOfPages) {
		int result = accountHolderDAO.addRequest(accountNo, numberOfPages);
		if(result == 1)
		{
			accountHolderDAO.connectionCommit();
			return true;}
		else
			return false;
	}

	@Override
	public boolean changePass(String userName, String newPass) {
		int result = accountHolderDAO.updatePass(userName, newPass);
		if(result >= 1)
		{
			accountHolderDAO.connectionCommit();
			return true;}
		else
			return false;
	}

	@Override
	public List<Service> trackServiceRequest(AccountHolder accountHolder) {
		List<Service> serviceRequests = accountHolderDAO.trackServiceRequest(accountHolder);
		return serviceRequests;
	}

}
