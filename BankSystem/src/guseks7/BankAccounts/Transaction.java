/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guseks7.BankAccounts;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Gustaf
 */
public class Transaction implements Serializable{
    private String myType;
    private double myAmount;
    private double mySaldo;
    private Date newDate;
            
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Transaction(double amount, double saldo, String type){
        myAmount = amount;
        mySaldo = saldo;
        myType = type;
        newDate = new Date();
    }
    @Override
    public String toString(){
        if(myType.equals("deposit")){
            return (dateFormat.format(newDate) + " " + myAmount + " " + mySaldo);
        }
        else {
            return (dateFormat.format(newDate) + " " + -myAmount + " " + mySaldo);
        }
        
    }
}
//myTransactions.add(dateFormat.format(newDate) + " " + amount + " " + saldo);
