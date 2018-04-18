package guseks7.logic;

import java.util.*;
import guseks7.BankAccounts.Transaction;
import java.io.Serializable;
/**
 * A class that handles the operations of the Bank. This is the interface that the user 
 * of the system uses. Each function signals if the procedure has worked ok. 
 * @author guseks7
 * 
 * Namn: Gustaf Ekström
 * Ltu-id: guseks-7
 *
 */

public class BankLogic implements Serializable {
	private ArrayList<Customer> myCustomers = new ArrayList<Customer>();
	private static int lastAssignedNumber = 1000;


/* Creates a customer with the specified name and personal number,
 * with the condition that a customer with the same personal number doesn't exist.
 */
	public boolean createCustomer(String name, String Surname, String pNo) {
		boolean pNoIsUnique = true;
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				pNoIsUnique = false;
			}
		}
		if(pNoIsUnique) {
			Customer newCustomer = new Customer(name, Surname, pNo);
			myCustomers.add(newCustomer);
			return true;
		}
		else {
			return false;
		}



	}

	/*Gets information about all customers and returns a string containing 
	 * the name and personal number of each customer. 
	 * Returns the information in a ArrayList containing Strings
	 */
	public ArrayList<String> getAllCustomers(){
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < myCustomers.size(); i++) {
			temp.add(myCustomers.get(i).getInfo());
		}
		return temp;
	}

	/*Gets information about a specified customer, identified by personal number.
	 * Returns the information in a ArrayList containing several Strings.
	 */
	public ArrayList<String> getCustomer(String pNo){
		boolean customerExist = false;
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				customerExist = true;

				String part1 = myCustomers.get(i).getInfo();
				temp.add(part1);
				String AccountInfo = myCustomers.get(i).getAccountsinfo();
				if(!AccountInfo.isEmpty()) {
					String[] helpString = AccountInfo.split(",");
					for(int j = 0; j < helpString.length; j++) {
						temp.add(helpString[j]);
					}
				}

			}

		}
		if(customerExist) {
			return temp;
		}
		else {
			return null;
		}
	}

	/*
	 * Creates a SavingsAccount for the specified customer. Makes sure to create a unique account by 
	 * increasing a constant variable. Returns the account number of the newly created account if successful, 
	 * and -1 if unsuccessful.
	 */
	public int createSavingsAccount(String pNo) {
		boolean AccountCreated = false;
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				myCustomers.get(i).createAccount(lastAssignedNumber+1, "Sparkonto");
				lastAssignedNumber++;
				AccountCreated = true;
			}
		}
		if(AccountCreated) {
			return lastAssignedNumber;
		}
		else {
			return -1;
		}
	}
	
	/*
	 * Creates a CreditAccount for the specified customer. Makes sure to create a unique account by 
	 * increasing a constant variable. Returns the account number of the newly created account if successful, 
	 * and -1 if unsuccessful.
	 */
	public int createCreditAccount(String pNo) {
		boolean AccountCreated = false;
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				myCustomers.get(i).createAccount(lastAssignedNumber+1, "Kreditkonto");
				lastAssignedNumber++;
				AccountCreated = true;
			}
		}
		if(AccountCreated) {
			return lastAssignedNumber;
		}
		else {
			return -1;
		}
	}

	/* 
	 * Changes the name of the specified customer. Takes the new first name and surname as input.
	 * return true or false depending on if it was successful.
	 */
	public  boolean changeCustomerName(String name, String surname, String pNo) {
		boolean nameChanged = false;
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				myCustomers.get(i).setName(name, surname);
				nameChanged = true;
			}
		}
		return nameChanged;
	}

	/*
	 * Removes the specified customer from the list of the banks customers.
	 * While doing so returns all the information about the customer.
	 * If the customer was removed the ArrayList of Strings that is returned
	 * contains info about the customer.
	 * If something went wrong it is empty.
	 */
	public ArrayList<String> deleteCustomer(String pNo){
		boolean deleted = false;
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				temp.add(myCustomers.get(i).getInfo());
				String help = myCustomers.get(i).closeAccounts();
				if(!help.isEmpty()) {
					String[] helpString = help.split(",");
                                    for (String helpString1 : helpString) {
                                        temp.add(helpString1);
                                    }
				}
				myCustomers.remove(i);
				deleted = true;
				break;

			}
		}
		if(deleted) {
			return temp;
		}
		else {
			return null;
		}
	}

	/*
	 * Gets the information about the specified account, if it belongs to the specified customer.
	 * Calls a similar function inside the customer class to retrieve the information 
	 * from the SavingsAccount object
	 */
	public String getAccount(String pNo, int accountId) {
		boolean accountFound = false;
		String returnString = "";
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				ArrayList<String> help = myCustomers.get(i).getAccountNrs();
				for(int j = 0; j < help.size(); j++) {
					if(accountId == Integer.parseInt(help.get(j))){
						returnString = myCustomers.get(i).getAccount(accountId);
						accountFound = true;
					}
				}
			}
		}
		if(accountFound) {
			return returnString;
		}
		else {
			return null;
		}

	}
	
	public ArrayList<String> getAccountNrs(String pNo){
		ArrayList<String> help = new ArrayList<String>();
		for(Customer c : myCustomers) {
			if(c.getPersonalNumber().equals(pNo)) {
				for(String number : c.getAccountNrs()) {
					help.add(number);
				}
			}
		}
		return help;
	}
	/*
	 * A function that returns all the transactions made on a specified account 
	 * belonging to the specified customer. Returns the information if the operation is succesful, 
	 * or null if the account is not found
	 */
	public ArrayList<Transaction> getTransactions(String pNo, int accountId){
		ArrayList<Transaction> temp = new ArrayList<Transaction>();
		boolean done = false;
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				ArrayList<String> help = myCustomers.get(i).getAccountNrs();
				for(int j = 0; j < help.size(); j++) {
					if(accountId == Integer.parseInt(help.get(j))){
						temp = myCustomers.get(i).getTransactions(accountId);
						done = true;
					}
				}
			}
		}
		if(done) {
			return temp;
		}
		else {
			return null;
		}
		
	}

	/*
	 * A function that makes a deposit of the specified amount into the specified account, 
	 * if the account belongs to the specified customer. Returns true if successful and false if something went wrong.
	 */
	public boolean deposit(String pNo, int accountId, double amount) {
		boolean depositDone = false;
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				ArrayList<String> help = myCustomers.get(i).getAccountNrs();
				for(int j = 0; j < help.size(); j++) {
					if(accountId == Integer.parseInt(help.get(j))){
						myCustomers.get(i).makeDeposit(accountId, amount);
						depositDone = true;
					

					}
				}
			}
		}
            return depositDone;
	}

	/*
	 * A function that makes a withdrawal of the specified amount from the specified account, 
	 * if the account belongs to the specified customer and contains enough money. Returns true if successful and false if something went wrong.
	 */
	public boolean withdraw(String pNo, int accountId, double amount) {
		boolean withdrawalDone = false;
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				ArrayList<String> help = myCustomers.get(i).getAccountNrs();
				for(int j = 0; j < help.size(); j++) {
					if(accountId == Integer.parseInt(help.get(j))){
						withdrawalDone = myCustomers.get(i).makeWithdrawal(accountId, amount);
						
					
					}
				}
			}
		}
            return withdrawalDone;
	}
        
        public void setLastAssigned(int newValue){
            lastAssignedNumber = newValue;
        }
        public int getLastAssigned(){
            return lastAssignedNumber;
        }
	
	/*
	 * Closes the specified account, if it exists and belongs to the specified customer.
	 */
	public String closeAccount(String pNo, int accountId) {
		boolean accountClosed = false;
		String helpString = "";
		for(int i = 0; i < myCustomers.size(); i++) {
			if(myCustomers.get(i).getPersonalNumber().equals(pNo)) {
				
				//Gets all the account numbers. Used to make sure the account specified 
				//belongs to the customer.
				ArrayList<String> help = myCustomers.get(i).getAccountNrs();
				for(int j = 0; j < help.size(); j++) {
					if(accountId == Integer.parseInt(help.get(j))){
						helpString = myCustomers.get(i).closeAccount(accountId);
						accountClosed = true;
						break;
						
					}
				}
			}
		}
		if(accountClosed) {
			return helpString;
		}
		else {
			return null;
		}
	}

}



