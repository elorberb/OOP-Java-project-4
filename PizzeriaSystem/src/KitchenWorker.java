
public class KitchenWorker extends Employee {
	private InformationSystem pizzaSystem;
	private BoundedQueue<PizzaDelivery> deliveries;
	private double workingTime;

	public KitchenWorker(double workingTime, String name, InformationSystem pizzaSystem,
			BoundedQueue<PizzaDelivery> deliveries) {// Constructor
		super(name);
		this.workingTime = workingTime;
		this.pizzaSystem = pizzaSystem;
		this.deliveries = deliveries;
	}

	public void run() {

		while (!isDayFinished) {// while day is not finished
			Order o = pizzaSystem.extractOrder();// extracting order from pizza system
			if (o != null) {
				int numOfPizzas = o.getNumOfPizzas();
				calculateSalary(numOfPizzas);// calculating salary
				double totalWorkingTime = calculateTotalWorkingTime(numOfPizzas);// calculating working time
				try {
					Thread.sleep((long) (totalWorkingTime * 1000));// thread sleep while working
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PizzaDelivery d = new PizzaDelivery(o.getAddress(), o.getDistance(), o.getNumOfPizzas());// creating new delivery
				deliveries.insert(d);// insert to deliveries queue
				printChefInfo(o);// printing chef info
			}

		}
	}

	private double calculateTotalWorkingTime(double numOfPizzas) {// calculate working time
		return this.workingTime * numOfPizzas;
	}

	private void printChefInfo(Order o) { // print chef info
		System.out.println("The Shef name is: " + this.name);
		System.out.println("Current shef salary: " + this.salary);
		System.out.println("Order details: " + o);
	}

	@Override
	public void calculateSalary(double numOfPizzas) {// calculate salary
		this.salary += numOfPizzas * 2;

	}
}
