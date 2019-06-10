package bussines;

public class Main {

	public static void main(String[] args) {
		
		Person p1 = new Person("Jack", 26, 550, false);
		Person p2 = new Person("Mat", 25, 60000, false);
			System.out.println(p1.getName()+"\t"+p1.getBank().getBalance());
		Car c1 = new Car("ford", "1988", 540, 0);
			System.out.println(c1.getOwner().getName());
		House h1 = new House(3, 122, 7800, 0);
		

		p1.addProperty(c1);
		p1.addProperty(c1);
		
			System.out.println(c1.getOwner().     getName());
		
			
			
			
			
		c1.addOwner(p2);
			System.out.println(c1.getOwner().getName());
			System.out.println(p1.getName()+"\t"+p1.getBank().getBalance());
		
		
		
	}

}
