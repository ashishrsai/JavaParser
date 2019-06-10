package bussines;

public class Car implements Property{

	String name;
	Person owner;
	String model;
	double price_dollar;
	 
	/*
	 * dgfg
	 */
	Car(String name, String model, double price_dollar, int currency) {
		this.name = name;
		this.model = model;
		this.owner = new Person("Factory", 0, 0, true);
		this.owner.addProperty(this);
		if(currency == 1)
			this.price_dollar = price_dollar;
		else 
			this.price_dollar = currency_convertor(price_dollar);
	}

	@Override
	public double currency_convertor(double price) { // Euro to USD
		return price * 10.1;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return this.price_dollar;
	}



	@Override
	public Person getOwner() {
		return this.owner;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Car";
	}

	public void addOwner(Person person) {
		
		if(this.owner.getAllProperties().contains(this))
			this.owner.getAllProperties().remove(this.owner.getAllProperties().indexOf(this));
		this.owner = person;
	}
	
	
	
	
}
