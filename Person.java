package bussines;

import java.util.ArrayList;
import java.util.List;

public class Person {

	String name;
	int age;
	List<Property> properties;
	Bank bank;
	boolean factory;
	/*
	 * once the class is created a bank account is also created for that person
	 */
	Person(String name, int age, double amount, boolean factory) {
		this.name = name;
		this.factory = factory;
		this.age = age;
		this.properties = new ArrayList<Property> ();
		this.bank = new Bank(this, amount);
		
	}
	
	String getName() {
		if(this.name != null)
			return this.name;
		else
			return null;
	}
	
	void addProperty(Property property) {
		
		if(this.factory) {
			this.properties.add(property);
			property.addOwner(this);
		}
		else {
			//constraint: check if the person is not under age.
			
			if(property instanceof House)
				if(this.age>12 && property.getPrice() < this.bank.getBalance()) {
					this.properties.add(property);
					property.addOwner(this);
					this.bank.withdraw(property.getPrice());
				}
				
			if(property instanceof Car) 
				//System.out.println(this.name+"\t"+this.age+"\t"+property.getPrice()+"\t"+this.bank.getBalance());
				if(this.age>18  &&  property.getPrice() < this.bank.getBalance()) {
					this.properties.add(property);
					property.addOwner(this);
					this.bank.withdraw(property.getPrice());
				}
				 
			if(property instanceof Bike) 
				if(property.getPrice() < this.bank.getBalance()) {
					this.properties.add(property);
					property.addOwner(this);
					this.bank.withdraw(property.getPrice());
			}
		}
		////////////////////////////////////////
	}
	
	List<Property> getAllProperties() {
		return this.properties;
	}
	
	Bank getBank() {
		return this.bank;
	}
	
}
