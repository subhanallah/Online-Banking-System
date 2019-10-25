package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Service;
import com.cg.obs.bean.Transaction;
import com.cg.obs.db.ConnectionFactory;
import com.cg.obs.exception.NoTransactionsException;

public class AccountHolderDAOImpl implements AccountHolderDAO {

	private static Connection connection;
	public AccountHolderDAOImpl() {
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String selectUserName(String userName) {
		String SQL = "SELECT USERNAME FROM USER_TABLE WHERE USERNAME = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
			preparedStatement.setString(1, userName);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				
				return rs.getString("USERNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AccountHolder selectPassword(String userName, String password) {
		String SQL = "SELECT ACCOUNT_ID,USERNAME,PASSWORD FROM USER_TABLE WHERE USERNAME = ? AND PASSWORD = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				//System.out.println(rs.getString("USER_NAME"));
				AccountHolder acc = new AccountHolder();
				acc.setAccountId(rs.getInt("ACCOUNT_ID"));
				acc.setUserName(userName);
				acc.setPassword(rs.getString("PASSWORD"));
				
				return acc;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Payee> selectAllPayee(int accountId) {
		String SQL = "SELECT * FROM PAYEE_TABLE WHERE ACCOUNT_ID = ?";
		List<Payee> payeeList = new ArrayList<Payee>();
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, accountId);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Payee payee = new Payee();
				payee.setAccountId(accountId);
				payee.setPayeeAccountId(rs.getInt("PAYEE_ACCOUNT_ID"));
				payee.setNickName(rs.getString("NICK_NAME"));
				payeeList.add(payee);
				
			}
			return payeeList;
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:selectAllPayee: " + e.getMessage());
		}
		return null;
	}

	@Override
	public double getAmount(int accountId) {
		String SQL = "SELECT ACCOUNT_BALANCE FROM ACCOUNT_MASTER WHERE ACCOUNT_ID = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, accountId);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			return rs.getDouble("ACCOUNT_BALANCE");
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:getAmount: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public String selectPayee(int accountId, int payeeAccountId) {
		String SQL = "SELECT * FROM PAYEE_TABLE WHERE ACCOUNT_ID = ? AND PAYEE_ACCOUNT_ID = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, payeeAccountId);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			return rs.getString("NICK_NAME");
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:selectPayee: " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean withdraw(int accountId, double amount) {
		String SQL = "UPDATE ACCOUNT_MASTER SET ACCOUNT_BALANCE = ACCOUNT_BALANCE - ? WHERE ACCOUNT_ID = ?";
		String insertTransaction = "INSERT INTO TRANSACTIONS(TRANSACTION_ID, TRAN_DESCRIPTION, TRANSACTION_DATE, TRANSACTION_TYPE, TRAN_AMOUNT, ACCOUNT_ID) "
				+ "VALUES(TRANSACTION_ID_SEQ.NEXTVAL,?, SYSDATE, 'debit', ?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL);
				PreparedStatement preparedStatement2 = connection.prepareStatement(insertTransaction)){
			preparedStatement.setDouble(1, amount);
			preparedStatement.setInt(2, accountId);
			int rows = preparedStatement.executeUpdate();
			System.out.println("withdraw: " + rows);
			
			if(rows > 0) {
				preparedStatement2.setString(1, "Withdrawing amount="+amount);
				preparedStatement2.setDouble(2, amount);
				preparedStatement2.setInt(3, accountId);
				preparedStatement2.executeUpdate();
				return true;
			}
			else 
				return false;
			
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:withdraw: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean deposit(int accountId, double amount) {
		String SQL = "UPDATE ACCOUNT_MASTER SET ACCOUNT_BALANCE = ACCOUNT_BALANCE + ? WHERE ACCOUNT_ID = ?";
		String insertTransaction = "INSERT INTO TRANSACTIONS(TRANSACTION_ID, TRAN_DESCRIPTION, TRANSACTION_DATE, TRANSACTION_TYPE, TRAN_AMOUNT, ACCOUNT_ID) "
				+ "VALUES(TRANSACTION_ID_SEQ.CURRVAL,?, SYSDATE, 'credit', ?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL);
				PreparedStatement preparedStatement2 = connection.prepareStatement(insertTransaction)){
			preparedStatement.setDouble(1, amount);
			preparedStatement.setInt(2, accountId);
			int rows = preparedStatement.executeUpdate();
			System.out.println("deposit: " + rows);
			if(rows > 0) {
				preparedStatement2.setString(1, "Depositing amount="+amount);
				preparedStatement2.setDouble(2, amount);
				preparedStatement2.setInt(3, accountId);
				preparedStatement2.executeUpdate();
				return true;
			}
			else 
				return false;
			
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:deposit: " + e.getMessage());
		}
		return false;
	}

	@Override
	public void connectionCommit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean addPayee(Payee payee) {
		String SQL = "INSERT INTO PAYEE_TABLE VALUES(?,?,?)";
		System.out.println(payee.getAccountId());
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, payee.getAccountId());
			preparedStatement.setInt(2, payee.getPayeeAccountId());
			preparedStatement.setString(3, payee.getNickName());
			int rows = preparedStatement.executeUpdate();
			if(rows > 0)
				return true;
			else 
				return false;
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:addPayee: " + e.getMessage());
		}
		return false;
	}

	@Override
	public void addFundTransfer(Payee payee, double amount) {
		String SQL = "INSERT INTO FUND_TRANSFER(FUNDTRANSFER_ID, ACCOUNT_ID, PAYEE_ACCOUNT_ID, DATE_OF_TRANSFER, TRANSFER_AMOUNT) "
				+ "VALUES(FUNDTRANSFER_ID_SEQ.NEXTVAL, ?, ?, SYSDATE, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, payee.getAccountId());
			preparedStatement.setInt(2, payee.getPayeeAccountId());
			preparedStatement.setDouble(3, amount);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:addFundTransfer: " + e.getMessage());
		}
	}

	@Override
	public List<Integer> getAllAccounts(String userName) {
		String SQL = "SELECT ACCOUNT_ID FROM USER_TABLE WHERE USERNAME = '"+userName+"'";
		try(Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SQL);
			List<Integer> accounts = new ArrayList<Integer>();
			while(rs.next()) {
				accounts.add(rs.getInt("ACCOUNT_ID"));
			}
			return accounts;
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:getAllAccounts: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Transaction> selectTentransactions(int accountNo) throws NoTransactionsException {
		String SQL = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_ID = "+accountNo;
		try(Statement statement = connection.createStatement()){
			ResultSet rs = statement.executeQuery(SQL);
			int count = 1;
			List<Transaction> transactions = new ArrayList<Transaction>();
			while(rs.next() && count <= 10) {
				Transaction t = new Transaction();
				t.setAccountId(accountNo);
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
	public List<Transaction> selectTransactionsInDuration(int accountNo, java.util.Date startDate, java.util.Date endDate) throws NoTransactionsException {
		
		try{
			Date sDate = new Date(startDate.getTime());
			Date eDate = new Date(endDate.getTime());
			String SQL = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_ID = ? AND TRANSACTION_DATE BETWEEN ? AND ?";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setInt(1, accountNo);
			preparedStatement.setDate(2, sDate);
			preparedStatement.setDate(3, eDate);
			ResultSet rs = preparedStatement.executeQuery();
			List<Transaction> transactions = new ArrayList<Transaction>();
			while(rs.next()) {
				Transaction t = new Transaction();
				t.setAccountId(accountNo);
				
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

	@Override
	public int updateMobileNo(int accountNo, String mobileNo) {
		String SQL = "UPDATE CUSTOMER SET MOBILE_NO = ? WHERE ACCOUNT_ID = ? ";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, mobileNo);
			preparedStatement.setInt(2, accountNo);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:updateMobileNo: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public int updateAddress(int accountNo, String address) {
		String SQL = "UPDATE CUSTOMER SET ADDRESS = ? WHERE ACCOUNT_ID = ? ";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, address);
			preparedStatement.setInt(2, accountNo);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:updateAddress: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public int addRequest(int accountNo, int numberOfPages) {
		String SQL = "INSERT INTO SERVICE_TRACKER(SERVICE_ID, SERVICE_DESC, ACCOUNT_ID, RAISE_DATE, SERVICE_STATUS) "
				+ "VALUES(SERVICE_ID_SEQ.NEXTVAL, ?, ?, SYSDATE, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, "ChequeBook request");
			preparedStatement.setInt(2, accountNo);
			preparedStatement.setString(3, "new");
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:updateAddress: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public int updatePass(String userName, String newPass) {
		String SQL = "UPDATE USER_TABLE SET PASSWORD = ? WHERE USERNAME = ? ";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, newPass);
			preparedStatement.setString(2, userName);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:updateAddress: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public List<Service> trackServiceRequest(AccountHolder accountHolder) {
		String SQL = "select SERVICE_ID, SERVICE_DESC, s.ACCOUNT_ID, RAISE_DATE, SERVICE_STATUS " + 
				"from SERVICE_TRACKER s " + 
				"INNER JOIN USER_TABLE u " + 
				"on s.ACCOUNT_ID = u.ACCOUNT_ID " + 
				"where u.USERNAME = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, accountHolder.getUserName());
			ResultSet rs = preparedStatement.executeQuery();
			List<Service> serviceRequests = new ArrayList<Service>();
			while(rs.next()) {
				Service service = new Service();
				service.setAccountId(rs.getInt("ACCOUNT_ID"));
				service.setRaiseDate(rs.getDate("RAISE_DATE"));
				service.setServiceDesc(rs.getString("SERVICE_DESC"));				
				service.setServiceId(rs.getInt("SERVICE_ID"));
				service.setServiceStatus(rs.getString("SERVICE_STATUS"));
				serviceRequests.add(service);
			}
			return serviceRequests;
		} catch (SQLException e) {
			System.out.println("AccountHolderDAOImpl:trackServiceRequest: " + e.getMessage());
		}
		return null;
	}
	
	

}
