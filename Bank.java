package bussines;

import java.util.ArrayList;

public class Bank {
	/*
	 * This class is created automatically by Person's class and 
	 * needs an initial outstanding and the person in refer
	 */
	
	Person Customer;
	double balance ;
	static int kk = 9;
	
	Bank (Person Customer, double balance) {
		this.Customer = Customer;
		this.balance = balance;
	}
	
	
	double getBalance() {
		deposit(87);
		return this.balance;
	}
	/*
	 * responsible to do withdraw and deposit
	 */
	void withdraw(double amount) {
		this.balance -= amount;
	}
	
	void deposit(double amount) {
		this.balance += amount;
	}
	
	
	
}
