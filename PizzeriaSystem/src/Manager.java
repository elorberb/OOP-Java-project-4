import java.util.Vector;

public class Manager extends Thread {
	private Queue<Call> managerLine;
	private Queue<Order> orders;
	private Queue<Call> callLine;
	private Vector<Employee> employees;
	private BoundedQueue<PizzaDelivery> deliveries;
	private InformationSystem pizzaSystem;
	private Vector<PizzaDelivery> deliveredOrders;
	private Vector<Order> allOrders;
	private int allCallsCount;

	public Manager(Queue<Call> managerLine, Queue<Order> orders, InformationSystem pizzaSystem, Queue<Call> callLine,
			BoundedQueue<PizzaDelivery> deliveries, Vector<Employee> employees, int allCallsCount,
			Vector<PizzaDelivery> deliveredOrders, Vector<Order> allOrders) { // constructor
		this.managerLine = managerLine;
		this.orders = orders;
		this.pizzaSystem = pizzaSystem;
		this.callLine = callLine;
		this.deliveries = deliveries;
		this.employees = employees;
		this.deliveredOrders = deliveredOrders;
		this.allCallsCount = allCallsCount;
		this.allOrders = allOrders;
	}

	private synchronized Order createOrder(Call c) { // creating order
		int numOfPizzas = c.getNumOfPizzas();
		double totalPrice = 15 * numOfPizzas;
		if (numOfPizzas > 20) {
			totalPrice *= 0.9;
		}
		String address = c.getAddress();
		long creditCard = c.getCreditCardNum();
		int arrivalTime = c.getArrivalTime();
		Order o = new Order(numOfPizzas, address, creditCard, arrivalTime, totalPrice);

		return o;
	}

	public void run() { 
		while (!isDayFinished()) {// while day is not finished
			checkDeliveries();// check the deliveries
			while (!managerLine.isEmpty()) {// if the manager line is not empty
				Call c = managerLine.extract();// get the call
				Order o = createOrder(c);// create the order
				allOrders.add(o);// add to total orders made
				double distance = convertAddress(o);
				o.setDistance(distance);
				pizzaSystem.insertOrder(o);// insert to pizza system
				System.out.println("New Order Arrived :" + o.getSerialNum());// pring order details
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkDeliveries();// check the deliveries
			}
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		stopWorkingDay();// stop working day
		printWorkingDayData();// Print working day data

	}

	private void checkDeliveries() { // checking if there are 10 deliveries left or less
		int deliveriesLeft = allCallsCount - deliveredOrders.size();
		if (deliveriesLeft <= 10) {
			for (Employee e : employees) {
				if (e instanceof PizzaGuy) {
					((PizzaGuy) e).onlyOneDeliveryPermited(true);
				}
			}
		}
	}

	private void printWorkingDayData() { // printing day data
		System.out.println("Total Employees Salary: " + calculateEmployeesSalary());
		System.out.println("Orders Delivered: " + deliveredOrders.size());
		System.out.println("Total Income: " + calculateIncome());

	}

	private boolean isDayFinished() { // checks if day is finished
		return deliveredOrders.size() == this.allCallsCount;

	}

	private double convertAddress(Order o) { // converting address to distance
		String address = o.getAddress();
		double distance = calculateDistance(address);
		return distance;

	}

	private double calculateDistance(String s) { // calculating the distance 
		int distance = countWordsUsingSplit(s);
		char c = s.charAt(0);
		distance += addDistanceByFirstLetter(c);
		return distance;
	}

	private double addDistanceByFirstLetter(char c) { // adding distance by letter
		if (c >= 'a' || c <= 'h') {
			return 0.5;
		}
		if (c >= 'i' || c <= 'p') {
			return 2;
		}
		if (c >= 'q' || c <= 'z') {
			return 7;
		}
		if (c >= '0' || c <= '9') {
			return c - '0';
		}
		return 0;
	}

	private int countWordsUsingSplit(String input) {// counting words
		if (input == null || input.isEmpty()) {
			return 0;
		}
		String[] words = input.split("\\s+");
		return words.length;
	}

	private void stopSchedulersWork() {// stopping schedulers
		orders.insert(null);

	}

	private void stopPizzaGuyWork() {// stopping pizza guys
		deliveries.insert(null);

	}

	private void stopWorkingDay() { // stopping working day
		stopPizzaGuyWork();
		stopSchedulersWork();
		stopKitchenWorkersWork();
		for (Employee emp : employees) {
			emp.isDayFinished = true;
		}
	}

	private void stopKitchenWorkersWork() {// stopping kitchen workers
		pizzaSystem.insertOrder(null);
	}

	private double calculateEmployeesSalary() {// calculating total employees salaries
		int sum = 0;
		for (Employee emp : employees) {
			sum += emp.getSalary();
		}
		return sum;
	}

	private double calculateIncome() {// calculating day income
		double totalPrice = 0;
		for (Order order : allOrders) {
			totalPrice += order.getPrice();
		}
		return totalPrice;
	}

}
