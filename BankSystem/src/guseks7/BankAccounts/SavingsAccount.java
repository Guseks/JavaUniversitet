package guseks7.BankAccounts;

import java.util.*;
/**
 * This class handles Savings accounts and the functions associated with a Savings account
 * It includes controls to follow the specified regulations for the different operations. 
 * The class is a subclass to Account.
 * 
 **  
 * @author guseks7
 *
 * Namn: Gustaf Ekström
 * Ltu-id: guseks-7
 */

public class SavingsAccount extends Account {
	//Variables to control the interest regarding withdrawal of money
	private boolean firstWithdrawal;
	private double withdrawalInterest;
	public SavingsAccount(double newSaldo, int newAccountNumber, String newAccountType){
		super(newSaldo, newAccountNumber, newAccountType);
		interest = 1;
		firstWithdrawal = true;
		withdrawalInterest = 0.02;
	}
	
	
	
	//Functions that perform simple operations on the account
	
	public boolean withdraw(double amount) {
		//Checks if this is the first withdrawal
		if(firstWithdrawal) {
			if(saldo >= amount) {
				saldo = saldo - amount;
				//Code for generation info about each transaction
				Date newDate = new Date();
				myTransactions.add(new Transaction(amount, saldo, "withdraw"));
				firstWithdrawal = false;
				return true;
			}
			else {
				return false;
			}
		}
		//If its not the first withdrawal, checks to make sure the withdrawal is possible
		else if(saldo >= amount + (amount * withdrawalInterest)) {
			double newAmount = amount + (amount * withdrawalInterest);
			saldo = saldo - newAmount;
			Date newDate = new Date();
			myTransactions.add(new Transaction(amount, saldo, "withdraw"));
			return true;
		}
		else {
			return false;
		}
	}
	
	//Performs the task of depositing money into the account
	//Stores the performed transaction
	public void deposit(double amount) {
		saldo = saldo + amount;
		Date newDate = new Date();
		myTransactions.add(new Transaction(amount, saldo, "deposit"));
	}
	
	//Closes the SavingsAccount
	public String closeAccount() {
		return AccountNumber + " " + saldo +" " + AccountType +" " + interest +" " + calculateInterest() + ",";
	}
}
