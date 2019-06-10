package bussines;

public class House implements Property{

	int num_parking = 5;
	Person owner;
	int size;
	double price_dollar;
	
	
	/*
	 * there are some parameters needed to create an object for House class
	 */
	House(int num_parking,	int size, double price_dollar, int currency) {
		this.num_parking = num_parking;
		this.owner = new Person("Factory", 0, 0, true);
		this.owner.addProperty(this);
		this.size = size;
		this.price_dollar = currency_convertor(price_dollar);
	}

	@Override
	public double currency_convertor(double price) { // Euro to USD
		double rate = 1.01*85;
		Bike.farshad = 9.0;
		return price * 1.1;
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
		return "House";
	}

	
	public void addOwner(Person person) {
		
		if(this.owner.getAllProperties().contains(this))
			this.owner.getAllProperties().remove(this.owner.getAllProperties().indexOf(this));
		this.owner = person;
	}
	
	
	
	
}
