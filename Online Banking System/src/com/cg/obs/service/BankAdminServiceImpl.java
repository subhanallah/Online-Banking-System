package com.cg.obs.service;

import java.util.Date;
import java.util.List;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transaction;
import com.cg.obs.dao.BankAdminDAO;
import com.cg.obs.dao.BankAdminDAOImpl;
import com.cg.obs.exception.NoTransactionsException;

public class BankAdminServiceImpl implements BankAdminService {

	private BankAdminDAO bankAdminDAO = new BankAdminDAOImpl();

	@Override
	public boolean validateAdminName(String adminName) {
		String result = bankAdminDAO.selectAdminName(adminName);
		if (result != null && result.equals(adminName))
			return true;
		else
			return false;
	}

	@Override
	public boolean validatePassword(String adminName, String password) {
		String result = bankAdminDAO.selectPassword(adminName, password);
		if (result != null && result.equals(password))
			return true;
		else
			return false;
	}

	@Override
	public boolean createAccount(Customer customer, AccountMaster accountMaster) {
		boolean result = false;
		int oldAccountId = bankAdminDAO.checkPan(customer.getPanCardNo());
		int accountID = bankAdminDAO.addAccountMaster(accountMaster);
		if (accountID != 0) {
			accountMaster.setAccountId(accountID);
			System.out.println("Mobile: " + customer.getMobileNo());
			customer.setAccountId(accountID);
			if (bankAdminDAO.addCustomer(customer)) {
				
				AccountHolder user = new AccountHolder();
				user.setAccountId(accountID);
				if(oldAccountId == 0) {
				String mobile = customer.getMobileNo();
				user.setUserName(customer.getCustomerName().substring(0, 4).toLowerCase()
						+ mobile.substring(mobile.length() - 4, mobile.length()));
				user.setPassword(mobile.substring(mobile.length() - 5, mobile.length()));
				user.setTransactionPassword(customer.getCustomerName().substring(0, 2).toLowerCase()
						+ mobile.substring(mobile.length() - 3, mobile.length() - 1));
				}
				else {
					 user = bankAdminDAO.getUser(oldAccountId);
					 user.setAccountId(accountID);
				}
				if (bankAdminDAO.addUser(user))
					result = true;
			}
		}
		return result;
	}

	@Override
	public List<Transaction> displayTransactions(Date date) throws NoTransactionsException {
		
		return bankAdminDAO.getTransations(date);
	}

	@Override
	public List<Transaction> displayTransactions(Date startDate, Date endDate) throws NoTransactionsException {
		
		return bankAdminDAO.getTransations(startDate, endDate);
	}

}
