package guseks7.logic;

import java.util.*;
import guseks7.BankAccounts.*;

/**
 * A class that handles the customers of the bank. Stores their information about their accounts
 * and their personal information.
 * Provides a way to access the Bank Accounts and perform different actions.
 * Includes in this is checks to make sure the operations follow the 
 * limitations specified for a bank account
 * @author guseks7
 * 
 * Namn: Gustaf Ekström
 * Ltu-id: guseks-7
 *
 */
public class Customer {
	private String firstName;
	private String SurName;
	private String personalNumber;
	private ArrayList<Account> myAccounts = new ArrayList<Account>();

	public Customer(String aName, String aSurname, String apNo) {
		firstName = aName;
		SurName = aSurname;
		personalNumber = apNo;
	}

	public void setName(String newFirstName, String newSurName) {
		firstName = newFirstName;
		SurName = newSurName;		
	}
	public String getName() {
		return firstName +" " + SurName;
	}

	public String getInfo() {
		return getName() + " " + getPersonalNumber();
	}
	public String getAllInfo() {
		return firstName +" " + SurName +" " + personalNumber + ", " + getAccountsinfo(); 
	}
	
	//Creates a new Account of the specified type and adds it to the customers list of accounts
	public void createAccount(int AccountNumber, String accountType) {
		if(accountType == "Sparkonto") {
			myAccounts.add(new SavingsAccount(0, AccountNumber, accountType));
		}
		else if (accountType == "Kreditkonto") {
			myAccounts.add(new CreditAccount(0, AccountNumber, accountType));
			
		}
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	//A function that retrieves all the information about
	//the customers accounts
	public String getAccountsinfo() {
		String AccountInfo = "";
		
		//Loops over accounts the customer has
		//Adds information about each account into AccountInfo, 
		//which is later returned
		for(int i = 0; i < myAccounts.size(); i++) {
			String temp = myAccounts.get(i).getAccountInfo();
			if(AccountInfo.isEmpty()) {
				AccountInfo = AccountInfo + temp;
			}
			else {
				AccountInfo = AccountInfo +"," + temp;
			}
		}
		return AccountInfo;
	}
	
	public int numberOfAccounts() {
		return myAccounts.size();
	}
	
	
	//Makes a deposit to a specified account with the amount specified
	public void makeDeposit(int AccountNumber, double amount) {
		for(int i = 0; i < myAccounts.size(); i++) {
			//If the accountNumber of account i matches the specified,
			//deposit the specified amount
			if(myAccounts.get(i).getAccountNumber() == AccountNumber) {
				myAccounts.get(i).deposit(amount);
			}
		}
	}
	
	//Makes a withdrawal from a specified account. Checks if the withdrawal is possible.
	//Returns true or false depending on if the withdrawal was performed.
	public boolean makeWithdrawal(int AccountNumber, double amount) {
		boolean withdrawalDone = false;
		/* Loops over the customers accounts. If accountsnumber is the same, and the amount in that account
		 * allows for a withdrawal, the withdrawal of speciifed amount is done.
		 */
		for(int i = 0; i < myAccounts.size(); i++) {
			if(myAccounts.get(i).getAccountNumber() == AccountNumber) {
				withdrawalDone = myAccounts.get(i).withdraw(amount);
			}
		}
		if(withdrawalDone) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//A function that returns the accountNrs that the customer have. Used as a helpfunction to perform other tasks.
	public ArrayList <String> getAccountNrs() {
		ArrayList<String> help = new ArrayList<String>();
		for(int i = 0; i < myAccounts.size(); i++) {
			 help.add(""+ myAccounts.get(i).getAccountNumber());
		}
		return help;
	}
	
	//Returns info about the specified account
	public String getAccount(int accountId) {
		String returnString = "";
		for(int i = 0; i < myAccounts.size(); i++) {
			if(myAccounts.get(i).getAccountNumber() == accountId) {
				returnString = myAccounts.get(i).getAccountInfo();
			}
		}
		return returnString;
	}
	/*
	 * Retrieves the list of the performed transactions for the specified account
	 * Return this list of information to be presented by the similar function in BankLogic
	 */
	public ArrayList<Transaction> getTransactions(int accountId) {
		ArrayList<Transaction> help = new ArrayList<Transaction>();
		for(int i = 0; i < myAccounts.size(); i++) {
			if(myAccounts.get(i).getAccountNumber() == accountId) {
				help = myAccounts.get(i).getTransactions();
			}
		}
		return help;
	}
	
	public String closeAccounts() {
		String temp = "";
		for(int i = 0; i < myAccounts.size(); i++) {
			 temp = temp + myAccounts.get(i).closeAccount();
		}
		return temp;
	}
	
	//Closes the specified account, returns the information about the account to be printed when closed
	public String closeAccount(int accountId) {
		String temp = "";
		for(int i = 0; i < myAccounts.size(); i++) {
			if(myAccounts.get(i).getAccountNumber() == accountId) {
				String[] help = myAccounts.get(i).closeAccount().split(",");
				temp = help[0];
					
				}
				myAccounts.remove(i);
			}
		
		return temp;
	}
}
