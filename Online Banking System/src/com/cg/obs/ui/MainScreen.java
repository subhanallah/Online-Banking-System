package com.cg.obs.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.BankAdmin;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Service;
import com.cg.obs.bean.Transaction;
import com.cg.obs.exception.LowBalanceException;
import com.cg.obs.exception.NoTransactionsException;
import com.cg.obs.exception.WrongPayeeAccountException;
import com.cg.obs.service.AccountHolderService;
import com.cg.obs.service.AccountHolderServiceImpl;
import com.cg.obs.service.BankAdminService;
import com.cg.obs.service.BankAdminServiceImpl;
import com.cg.obs.util.Utility;

public class MainScreen {

	private static List<Integer> accounts;
	private static AccountHolderService accountHolderService = new AccountHolderServiceImpl();
	static BankAdminService bankAdminService = new BankAdminServiceImpl();
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"****************************************************************WELCOME TO ONLINE BANKING SYSTEM****************************************************************");
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

		boolean run = true;
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		while (run && choice != 3) {
			System.out.println("Select User type:");
			System.out.println("1. Press 1 for Account Holder");
			System.out.println("2. Press 2 for Bank Admin");
			System.out.println("3. Press 3 for Exit");
			System.out.println();
			choice = scanner.nextInt();
			if (!Utility.validateChoice(choice)) {
				System.out.println("Invalid Choice. Please Try Again.");
				continue;
			}
			switch (choice) {
			case 1:
				accountHolder();
				break;
			case 2:
				bankAdmin();
				break;
			case 3:
				System.out.println("Thank you for visiting us.");
			}

		}
		scanner.close();
	}

	private static void accountHolder() {

		String userName;
		String password;
		int flag1 = 1;
		int flag2 = 1;
		int count = 0;
		int accountId;
		System.out.println("\nWelcome Account Holder");
		System.out.println("Please enter your credentials for login:");
		do {
			do {
				System.out.print("UserName: ");
				userName = scanner.next();
				if (accountHolderService.validateUserName(userName))
					flag2 = 0;
				else {
					System.out.println("Invalid Username. Please try again.");
					System.out.println(4 - count + " attempts more.");
					count++;
				}
			} while (flag2 != 0 && count < 5);
			if (count == 5)
				return;
			flag2 = 1;
			count = 0;
			do {
				System.out.print("Password: ");
				password = scanner.next();
				accountId = accountHolderService.validatePassword(userName, password);
				if (accountId != 0) {
					flag2 = 0;
					System.out.println("Login Successful...");
				} else {
					System.out.println("Invalid Password. Please try again.");
					System.out.println(4 - count + " attempts more.");
					count++;
				}
			} while (flag2 != 0 && count < 5);
			flag1 = 0;
		} while (flag1 != 0 && count < 5);
		AccountHolder accountHolder = new AccountHolder(userName, password);
		// accountHolder.setAccountId(accountId);
		accountHolderHome(accountHolder);
	}

	private static void bankAdmin() {

		String adminName;
		String password;
		int flag1 = 1;
		int flag2 = 1;
		int count = 0;
		System.out.println("\nWelcome Bank Admin");
		System.out.println("Please enter your credentials for login:");
		do {
			do {
				System.out.print("AdminName: ");
				adminName = scanner.next();
				if (bankAdminService.validateAdminName(adminName))
					flag2 = 0;
				else {
					System.out.println("Invalid Admin name. Please try again.");
					System.out.println(4 - count + " attempts more.");
					count++;
				}
			} while (flag2 != 0 && count < 5);
			if (count == 5)
				return;
			flag2 = 1;
			count = 0;
			do {
				System.out.print("Password: ");
				password = scanner.next();
				if (bankAdminService.validatePassword(adminName, password)) {
					flag2 = 0;
					System.out.println("Login Successful...");
				} else {
					System.out.println("Invalid Password. Please try again.");
					System.out.println(4 - count + " attempts more.");
					count++;
				}

			} while (flag2 != 0 && count < 5);
			flag1 = 0;
		} while (flag1 != 0 && count < 5);
		BankAdmin bankAdmin = new BankAdmin(adminName, password);
		bankAdminHome(bankAdmin);
	}

	private static void bankAdminHome(BankAdmin bankAdmin) {
		System.out.println("\nWelcome " + bankAdmin.getAdminName() + "to the Online Banking System ADMIN PORTAL");
		System.out.println();
		int choice = 0;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		do {
			System.out.println("Press 1 for Creating New Account");
			System.out.println("Press 2 for Displaying transactions of all accounts");
			System.out.println("Press 3 for Log Out");

			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter Account Holder Name");
				String name = scanner.next();
				Scanner sc = new Scanner(System.in);
				sc.useDelimiter("\n");
				System.out.println("Enter Address");
				String address = sc.next();
				System.out.println("Enter Mobile Number");
				String mobileNo = scanner.next();
				System.out.println("Mobile: " + mobileNo);
				System.out.println("Enter Email-Id");
				String emailId = scanner.next();
				System.out.println("Enter Account Type");
				String accountType = scanner.next();
				System.out.println("Enter Pan Card Number");
				String panCardNo = scanner.next();
				panCardNo = panCardNo.toUpperCase();
				System.out.println("Enter opening balance");
				double openingBalance = scanner.nextDouble();
				Date today = new Date();
				Customer customer = new Customer(name, emailId, address, mobileNo, panCardNo);
				AccountMaster accountMaster = new AccountMaster(accountType, openingBalance, today);
				if (bankAdminService.createAccount(customer, accountMaster))
					System.out.println("Account created successfully....");
				else
					System.out.println("Error while creating account. Please try again....");
				break;
			case 2:
				do {
					System.out.println("Press 1 for Displaying transactions of a particular day");
					System.out.println("Press 2 for Displaying transactions in a duration");
					System.out.println("Press 3 to go back");
					choice = scanner.nextInt();
					List<Transaction> transactions;
					
					if(choice == 1) {
						System.out.println("Enter the date in the format dd/MM/yyyy");
						Date date;
						try {
							date = df.parse(scanner.next());
							transactions = bankAdminService.displayTransactions(date);
							System.out.println(transactions);
						} catch (ParseException e) {
							e.printStackTrace();
						} catch (NoTransactionsException e) {
							e.printStackTrace();
						}
						
					}
					else if(choice == 2) {
						System.out.println("Enter start date in the format dd/mm/yyyy: ");
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date startDate;
						try {
							startDate = sdf.parse(scanner.next());
							System.out.println("Enter end date in the format dd/mm/yyyy: ");
							Date endDate = sdf.parse(scanner.next());
							transactions = bankAdminService.displayTransactions(startDate, endDate);
							System.out.println(transactions);
						} catch (ParseException e) {
							e.printStackTrace();
						} catch (NoTransactionsException e) {
							e.printStackTrace();
						}
						
					}
					else {
						System.out.println("Going back");
					}
				}while(choice != 3);
				break;
			case 3:
				System.out.println("successfully logged out....");
				break;
			default:
				System.out.println("Invalid Input... Please try again...");
			}
		} while (choice != 3);

	}

	private static void accountHolderHome(AccountHolder accountHolder) {
		int choice = 0;

		System.out.println("\nWelcome " + accountHolder.getUserName() + " to the Online Banking System USER PORTAL");
		System.out.println();
		accounts = accountHolderService.getAssociatedAccount(accountHolder.getUserName());
		System.out.println("Your accounts:");
		Iterator<Integer> itr = accounts.iterator();
		int index = 1;
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		System.out.println();

		do {
			System.out.println("Press 1 for View Mini/Detailed statement");
			System.out.println("Press 2 for Change in address/mobile number");
			System.out.println("Press 3 for Request for cheque book");
			System.out.println("Press 4 for Track service request");
			System.out.println("Press 5 for Fund Transfer");
			System.out.println("Press 6 for Change password");
			System.out.println("Press 7 for Log Out");

			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				getTransactions();
				break;
			case 2:
				changeDetails();
				break;
			case 3:
				chequeBookRequest();
				break;
			case 4:
				trackServiceRequest(accountHolder);
				break;
			case 5:
				fundTransfer(accountHolder);
				break;
			case 6:
				changePassword(accountHolder);
				break;
			case 7:
				System.out.println("successfully logged out....");
				break;
			default:
				System.out.println("Invalid Input... Please try again...");
			}
		} while (choice != 7);
	}

	private static void trackServiceRequest(AccountHolder accountHolder) {
		List<Service> serviceRequests = accountHolderService.trackServiceRequest(accountHolder);
		System.out.println(serviceRequests);
		
	}

	private static void changePassword(AccountHolder accountHolder) {
		System.out.println("Enter current password: ");
		String oldPass = scanner.next();
		if (oldPass.equals(accountHolder.getPassword())) {
			System.out.println("Enter new password:");
			String newPass = scanner.next();
			int count = 0;
			while (count < 3) {
				System.out.println("Confirm password:");
				if(newPass.equals(scanner.next())) {
					count = 3;
					accountHolderService.changePass(accountHolder.getUserName(), newPass);
				}
				else count++;
			}
		} else
			System.out.println("Wrong password :-(");
	}

	private static void chequeBookRequest() {
		int count = 3;
		int numberOfPages = 0;
		while(count >= 0) {
			System.out.println("Enter number of pages you require (50,100,200)");
			numberOfPages = scanner.nextInt();
			
		}
		System.out.println("Enter index of account for which you want to get mini statement");
		Iterator<Integer> itr = accounts.iterator();
		int index = 1;
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		System.out.println();
		int accountIndex = scanner.nextInt();
		int accountNo;
		accountNo = accounts.get(accountIndex - 1);
		accountHolderService.chequeBookRequest(accountNo, numberOfPages);
		System.out.println("Request logged");
		
	}

	private static void changeDetails() {
		System.out.println("1.Change mobile number\n2.Change Address");
		int choice = scanner.nextInt();
		System.out.println("Enter index of account for which you want to get mini statement");
		Iterator<Integer> itr = accounts.iterator();
		int index = 1;
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		System.out.println();
		int accountIndex = scanner.nextInt();
		int accountNo;
		accountNo = accounts.get(accountIndex - 1);
		System.out.println(accountNo);
		if (choice == 1) {
			System.out.println("Enter new Mobile Number: ");
			String mobileNo = scanner.next();
			if (accountHolderService.changeMobileNo(accountNo, mobileNo))
				System.out.println("Mobile Number changed successfully");
			else
				System.out.println("Cannot change");
		} else if (choice == 2) {
			System.out.println("Enter new address: ");
			Scanner sc = new Scanner(System.in);
			sc.useDelimiter("\n");
			String address = sc.next();
			if (accountHolderService.changeAddress(accountNo, address))
				System.out.println("Address changed successfully");
			else
				System.out.println("Cannot change");
		} else {
			System.out.println("Invalid Input");
		}

	}

	private static void getTransactions() {
		System.out.println("1.Mini statement\n2.Detailed Statement");
		int choice = scanner.nextInt();
		System.out.println("Enter index of account for which you want to get mini statement");
		Iterator<Integer> itr = accounts.iterator();
		int index = 1;
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		System.out.println();

		int accountIndex = scanner.nextInt();
		int accountNo;
		try {
			accountNo = accounts.get(accountIndex - 1);
			System.out.println(accountNo);
			if (choice == 1) {
				List<Transaction> transactions = accountHolderService.getMiniStatement(accountNo);

				System.out.println(transactions);

			} else if (choice == 2) {

				Date startDate;
				Date endDate;
				int count = 1;
				while (count <= 5) {
					try {
						System.out.println("Enter start date in the format dd/mm/yyyy: ");
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						startDate = sdf.parse(scanner.next());
						System.out.println("Enter end date in the format dd/mm/yyyy: ");
						endDate = sdf.parse(scanner.next());
						List<Transaction> transactions = accountHolderService.getDetailedTransactions(accountNo,
								startDate, endDate);
						System.out.println(transactions);
						break;
					} catch (ParseException e) {
						System.out.println("You have not entered date in the required format.\n" + (5 - count)
								+ " attempts left.");
						count++;
					}
				}

			} else {
				System.out.println("Invalid Input");
			}
		} catch (NoTransactionsException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Error while getting associated account with the index:" + e.getMessage());
		}

	}

	private static void fundTransfer(AccountHolder accountHolder) {
		int choice;
		System.out.println();
		System.out.println("Select index of account for which you want to transfer");
		int index = 1;
		Iterator<Integer> itr = accounts.iterator();
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		int accountIndex = scanner.nextInt();
		int accountNo = accounts.get(accountIndex - 1);
		accountHolder.setAccountId(accountNo);
		do {

			System.out.println("1. Transfer in existing payee");
			System.out.println("2. Add new payee");
			System.out.println("3. Exit");

			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				List<Payee> payeeList = accountHolderService.getPayeeList(accountHolder.getAccountId());
				if (payeeList == null || payeeList.size() == 0)
					System.out.println("You have not added any payee yet. Please add a payee to transfer money.");
				else {
					System.out.println(payeeList);
					Payee payee = new Payee();
					System.out.println("Enter payee account number: ");
					payee.setPayeeAccountId(scanner.nextInt());
					System.out.println("Enter nick name of payee: ");
					payee.setNickName(scanner.next());
					payee.setAccountId(accountHolder.getAccountId());
					System.out.println(payee);
					System.out.println("Enter amount: ");
					double amount = scanner.nextDouble();
					try {
						if (accountHolderService.fundTransfer(accountHolder.getAccountId(), payee, amount))
							System.out.println("Transferred succesfully");
						else
							System.out.println("Can't Transfer");
					} catch (LowBalanceException e) {
						System.out.println(e.getMessage());
					} catch (WrongPayeeAccountException e) {
						System.out.println(e.getMessage());
					}
				}
				break;
			case 2:

				Payee payee = new Payee();
				payee.setAccountId(accountHolder.getAccountId());
				System.out.println("Add Payee Account Number: ");
				payee.setPayeeAccountId(scanner.nextInt());
				System.out.println("Enter payee nick name");
				payee.setNickName(scanner.next());
				if (accountHolderService.addPayee(payee))
					System.out.println("Successfully Added");
				else
					System.out.println("Can not be added");
				break;
			case 3:
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid Input... Please try again.");
			}

		} while (choice != 3);
	}
}
