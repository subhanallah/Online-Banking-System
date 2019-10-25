package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transaction;
import com.cg.obs.db.ConnectionFactory;
import com.cg.obs.exception.NoTransactionsException;

public class BankAdminDAOImpl implements BankAdminDAO{
	static Connection connection;
	public BankAdminDAOImpl() {
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String selectAdminName(String adminName) {
		String SQL = "SELECT ADMIN_NAME FROM BANK_ADMIN WHERE ADMIN_NAME = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
			preparedStatement.setString(1, adminName);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString("ADMIN_NAME"));
				return rs.getString("ADMIN_NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String selectPassword(String adminName, String password) {
		String SQL = "SELECT ADMIN_NAME,PASSWORD FROM BANK_ADMIN WHERE ADMIN_NAME = ? AND PASSWORD = ?";

		try (
				PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
			preparedStatement.setString(1, adminName);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString("ADMIN_NAME"));
				return rs.getString("PASSWORD");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public int addAccountMaster(AccountMaster accountMaster) {
		String SQL = "INSERT INTO ACCOUNT_MASTER(ACCOUNT_ID, ACCOUNT_TYPE, ACCOUNT_BALANCE, OPEN_DATE) VALUES(ACCOUNT_ID_SEQ.NEXTVAL,?,?,SYSDATE)";
		String select = "SELECT ACCOUNT_ID_SEQ.CURRVAL FROM DUAL";
		try(
				PreparedStatement preparedStatement = connection.prepareStatement(SQL);
				Statement statement = connection.createStatement()){
			
			preparedStatement.setString(1, accountMaster.getAccountType());
			preparedStatement.setDouble(2, accountMaster.getOpeningBalance());
			preparedStatement.execute();
			ResultSet rs = statement.executeQuery(select);
			if (rs.next()) {
				int accountId = rs.getInt("CURRVAL");
				return accountId;
			}
		} catch (SQLException e) {
			System.out.println("BankAdminDAOImpl:addAccountMaster:"+e.getMessage());
		}
		return 0;
	}

	@Override
	public boolean addCustomer(Customer customer) {
		String SQL = "INSERT INTO CUSTOMER(ACCOUNT_ID, CUSTOMER_NAME, MOBILE_NO, EMAIL, ADDRESS, PANCARD) VALUES(?,?,?,?,?,?)";
		try(
				PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			
			preparedStatement.setInt(1, customer.getAccountId());
			preparedStatement.setString(2, customer.getCustomerName());
			preparedStatement.setString(3, customer.getMobileNo());
			preparedStatement.setString(4, customer.getEmail());
			preparedStatement.setString(5, customer.getAddress());
			preparedStatement.setString(6, customer.getPanCardNo());
			int rows = preparedStatement.executeUpdate();
			if(rows > 0)
				return true;
			else 
				return false;
		} catch (SQLException e) {
			System.out.println("BankAdminDAOImpl:addCustomer:"+e.getMessage());
		}
		return false;
	}

	@Override
	public boolean addUser(AccountHolder user) {
		
		String SQL = "INSERT INTO USER_TABLE(ACCOUNT_ID, USERNAME, PASSWORD, TRANSACTION_PASSWORD) VALUES(?,?,?,?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, user.getAccountId());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getTransactionPassword());
			int rows = preparedStatement.executeUpdate();
			connection.commit();
			if(rows > 0)
				return true;
			else 
				return false;
		} catch (SQLException e) {
			System.out.println("BankAdminDAOImpl:addUser:"+e.getMessage());
		}
		return false;
	}

	@Override
	public int checkPan(String panCardNo) {
		String SQL = "SELECT ACCOUNT_ID FROM CUSTOMER WHERE PANCARD = '"+panCardNo+"'";
		try(Statement statement = connection.createStatement()){
			ResultSet rs = statement.executeQuery(SQL);
			rs.next();
			return rs.getInt("ACCOUNT_ID");
		} catch (SQLException e) {
			System.out.println("BankAdminDAOImpl:checkPan:"+e.getMessage());
		}
		return 0;
	}

	@Override
	public AccountHolder getUser(int oldAccountId) {
		System.out.println(oldAccountId);
		String SQL = "SELECT * FROM USER_TABLE WHERE ACCOUNT_ID = "+oldAccountId;
		try(Statement statement = connection.createStatement()){
			ResultSet rs = statement.executeQuery(SQL);
			if(rs.next()) {
			AccountHolder user = new AccountHolder();
			user.setUserName(rs.getString("USERNAME"));
			user.setLockStatus(rs.getString("LOCK_STATUS"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setSecretAnswer(rs.getString("SECRET_ANSWER"));
			user.setTransactionPassword(rs.getString("TRANSACTION_PASSWORD"));
			user.setSecretQuestion(rs.getString("SECRET_QUESTION"));
			return user;}
		} catch (SQLException e) {
			System.out.println("BankAdminDAOImpl:getUser:"+e.getMessage());
		}
		return null;
	}

	@Override
	public List<Transaction> getTransations(java.util.Date date) throws NoTransactionsException {
		String SQL = "SELECT * FROM TRANSACTIONS WHERE TO_DATE(TRANSACTION_DATE) = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			Date sDate = new Date(date.getTime());
			preparedStatement.setDate(1, sDate);
			ResultSet rs = preparedStatement.executeQuery();
			int count = 1;
			List<Transaction> transactions = new ArrayList<Transaction>();
			while(rs.next() && count <= 10) {
				Transaction t = new Transaction();
				t.setAccountId(rs.getInt("ACCOUNT_ID"));
				t.setDateofTransaction(rs.getDate("TRANSACTION_DATE"));
				t.setTranAmount(rs.getDouble("TRAN_AMOUNT"));
				t.setTranDescription(rs.getString("TRAN_DESCRIPTION"));
				t.setTransactionId(rs.getInt("TRANSACTION_ID"));
				t.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				transactions.add(t);
				count++;
			}
			if(transactions == null || transactions.size() == 0)
				throw new NoTransactionsException("No Transactions Found For This Account.");
			return transactions;
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:selectTentransactions: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Transaction> getTransations(java.util.Date startDate, java.util.Date endDate) throws NoTransactionsException {
		try{
			Date sDate = new Date(startDate.getTime());
			Date eDate = new Date(endDate.getTime());
			String SQL = "SELECT * FROM TRANSACTIONS WHERE TRANSACTION_DATE BETWEEN ? AND ?";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setDate(1, sDate);
			preparedStatement.setDate(2, eDate);
			ResultSet rs = preparedStatement.executeQuery();
			List<Transaction> transactions = new ArrayList<Transaction>();
			while(rs.next()) {
				Transaction t = new Transaction();
				t.setAccountId(rs.getInt("ACCOUNT_ID"));
				
				t.setDateofTransaction(rs.getDate("TRANSACTION_DATE"));
				
				t.setTranAmount(rs.getDouble("TRAN_AMOUNT"));
				
				t.setTranDescription(rs.getString("TRAN_DESCRIPTION"));
				
				t.setTransactionId(rs.getInt("TRANSACTION_ID"));
				t.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				transactions.add(t);
			}
			if(transactions == null || transactions.size() == 0)
				throw new NoTransactionsException("No Transactions Found For This Account.");
			return transactions;
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:selectTransactionsInDuration: " + e.getMessage());
		}
		return null;
	}

}
