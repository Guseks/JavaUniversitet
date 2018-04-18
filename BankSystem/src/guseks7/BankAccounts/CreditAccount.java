package guseks7.BankAccounts;

import java.io.Serializable;
import java.util.Date;

/**
 * This class handles Credit accounts and the functions associated with the account
 * It controls the interest depending on the current saldo, it also makes sure the regulations are followed 
 * when withdrawing money. The class is a subclass to the class Account
 *
 **  
 * @author guseks7
 *
 * Namn: Gustaf Ekström
 * Ltu-id: guseks-7
 */

public class CreditAccount extends Account implements Serializable {
	public CreditAccount(double newSaldo, int newAccountNumber, String newAccountType){
		super(newSaldo, newAccountNumber, newAccountType);
		interest = 0.5;
	}
	
	public boolean withdraw(double amount) {
		//Control if the operation is allowed
		if(saldo - amount >= -5000) {
		
			saldo = saldo - amount;
			//Create the information about the transaction, then store it
			
			myTransactions.add(new Transaction(amount, saldo, "withdraw"));
			
			//regulates the interest after the perfomed operation
			if(saldo < 0) {
				interest = 7;
			}
			else {
				interest = 0.5;
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	public void deposit(double amount) {
		
		saldo = saldo + amount;
		Date newDate = new Date();
		myTransactions.add(new Transaction(amount, saldo, "deposit"));
		//Regulating the interest to the correct value after the operation is performed
		if(saldo + amount > 0) {
			interest = 0.5;
		}
	}
	
	//Closes the CreditAccount
	public String closeAccount() {
		//Regulating the interest to make sure the interest is correct,
		//for when the interest is calculated when the account is closed
		if(saldo < 0) {
			interest = 7;
		}
		else {
			interest = 0.5;
		}
		return AccountNumber + " " + saldo +" " + AccountType +" " + interest +" " + calculateInterest() + ",";
	}
}
