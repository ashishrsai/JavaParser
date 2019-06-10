package bussines;

public class Bike implements Property{

	
	String name;
	Person owner;
	double price_dollar;
	static double farshad = 1.01*85;
	Bike(String name,  double price_dollar, int currency) {
		
		this.name = name;
		this.owner = new Person("Factory", 0, 0, true);
		this.owner.addProperty(this);
		if(currency == 1)
			this.price_dollar = price_dollar;
		else 
			this.price_dollar = currency_convertor(price_dollar);
	}
	
	
	@Override
	public double currency_convertor(double price) { // Euro to USD
		
		double rate = 44.9;
		farshad = rate;
		rate--;
		if(farshad > 10)
			System.out.println("soememm"); 
		for(int i=0;i<89;i++) {
			/*
			 * dskjdlfkjklfg
			 */
			farshad++;
		}
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
