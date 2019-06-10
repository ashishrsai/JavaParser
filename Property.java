package bussines;

public interface Property {

	double currency_convertor(double price);
	double getPrice();
	Person getOwner();
	String getName();
	void addOwner(Person person);
	
}
