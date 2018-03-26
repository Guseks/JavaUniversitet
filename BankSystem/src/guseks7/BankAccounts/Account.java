package guseks7.BankAccounts;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class handles bank accounts and the functions associated with an account
 * It is a super class that contains variables and functions that are shared by the classes CreditAccount
 * and SavingsAccount. It also contains variables to store all the transactions made on a specific account.
 * The transactions are added into this container in the subclasses when a transaction is performed.
 **  
 * @author guseks7
 *
 * Namn: Gustaf Ekström
 * Ltu-id: guseks-7
 */

public abstract class Account {
	
	//The variables for an account, Shared for all account types.
	protected double saldo;
	protected double interest;
	protected int AccountNumber;
	protected String AccountType;
	protected ArrayList<Transaction> myTransactions = new ArrayList<Transaction>();
	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Account(double newSaldo, int newAccountNumber, String newAccountType){
		saldo = newSaldo;
		AccountNumber = newAccountNumber;
		AccountType = newAccountType;
	}
/*
 * Abstract methods, implemented in the subclasses
 */
	abstract public boolean withdraw(double amount);
	abstract public String closeAccount();
	abstract public void deposit(double amount);
	
	
	//Functions that return private variables
	//Information used by customer and BankLogic to perform tasks
	
		public int getAccountNumber() {
			return AccountNumber;
		}
		public double getSaldo() {
			return saldo;
		}
		//Returns a string of accountInfo, in a suitable format to be used by the class Customer to present
		//the data in the desired way
		public String getAccountInfo() {
			return AccountNumber + " " + saldo +" " + AccountType +" " + interest;
		}
		
		public double calculateInterest() {
			return saldo*interest/100;
		}
		
		public ArrayList<Transaction> getTransactions() {
			return myTransactions;			
		}
		
}
