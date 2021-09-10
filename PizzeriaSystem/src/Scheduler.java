import java.util.Vector;

public class Scheduler extends Employee {
	private Queue<Order> orders;
	private InformationSystem pizzaSystem;

	public Scheduler(String name, Queue<Order> orders, InformationSystem pizzaSystem) {// Constructor
		super(name);
		this.orders = orders;
		this.pizzaSystem = pizzaSystem;

	}

	public void run() {
		while (!isDayFinished()) {// while day is not finished

			Order o = orders.extract();// extracting order from orders queue
			double distance = convertAddress(o);// calculating distance
			o.setDistance(distance);// setting distance
			double workingTime = calculateWorkingTime(distance);// calculating working time
			calculateSalary(workingTime);// calculating salary
			try {
				Thread.sleep((long) workingTime * 1000);// thread sleeps while working
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pizzaSystem.insertOrder(o);// inserting to pizza system
			System.out.println("New Order Arrived :" + o.getSerialNum());// printing order serial

		}

	}

	private boolean isDayFinished() {// checks if day is over
		return orders.top() == null;
	}

	private double calculateWorkingTime(double distance) {// calculating working time
		return distance * 0.25;
	}

	private double convertAddress(Order o) {// converting address to distance
		String address = o.getAddress();
		double distance = calculateDistance(address);
		return distance;

	}

	private double calculateDistance(String s) {// calculating distance
		double distance = countWordsUsingSplit(s);
		char c = s.charAt(0);
		distance += addDistanceByFirstLetter(c);
		return distance;
	}

	private double addDistanceByFirstLetter(char c) {// adding distance by letter
		if (c >= 'a' && c <= 'h') {
			return 0.5;
		}
		if (c >= 'i' && c <= 'p') {
			return 2;
		}
		if (c >= 'q' && c <= 'z') {
			return 7;
		}
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		return 0;
	}

	private int countWordsUsingSplit(String input) {// count words
		if (input == null || input.isEmpty()) {
			return 0;
		}
		String[] words = input.split("\\s+");
		return words.length;
	}

	@Override
	public void calculateSalary(double workingTime) {// calculating salary
		salary += workingTime * 4;

	}

}
