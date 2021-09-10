import java.util.Random;
import java.util.Vector;

public class PizzaGuy extends Employee {
	private int deliveryCapacity;
	private boolean onlyOneDeliveryPermited = false;
	public BoundedQueue<PizzaDelivery> deliveries;
	public Vector<PizzaDelivery> deliveredPizzas;
	public PizzaDelivery[] delivery;

	public PizzaGuy(String name, BoundedQueue<PizzaDelivery> deliveries, Vector<PizzaDelivery> deliveredPizzas) {// Constructor
		super(name); 
		Random r = new Random();
		deliveryCapacity = r.nextInt(3) + 2; // random number between 2 to 4
		this.deliveries = deliveries;
		delivery = new PizzaDelivery[deliveryCapacity];// delivery capacity
		this.deliveredPizzas = deliveredPizzas;

	}

	private int addTip() {// adding tip
		int tip;
		tip = (int) (Math.random() * 15); // random number between 0 to 15
		return tip;
	}

	public void onlyOneDeliveryPermited(boolean isLesThanTen) { // changes only one delivery permited boolean
		onlyOneDeliveryPermited = isLesThanTen;
	}

	public void setDeliveryCapacity(int deliveryCapacity) {// setter
		this.deliveryCapacity = deliveryCapacity;
	}

	private double calculateDelivery(double distance, int tips) {// calculating delivery salary amount
		double amount = 3 + 4 * distance + tips;
		return amount;
	}

	public void run() {

		while (!isDayFinished()) { // if day is not finished
			if (onlyOneDeliveryPermited) { // set capacity to 1 if boolean is true
				this.setDeliveryCapacity(1);
			}

			for (int i = 0; i < deliveryCapacity; i++) { 
				PizzaDelivery d = deliveries.extract();
				deliveredPizzas.add(d); // adding to total delivered deliveries
				delivery[i] = d; // insert to delivery array (baggage)

			}
			for (int i = 0; i < deliveryCapacity; i++) {
				if (delivery[i] != null) { // if the delivery is not null
					int currentDriving = (int) delivery[i].getDistance();

				try {
					Thread.sleep(currentDriving * 1000); // sleep while driving
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					double amount = calculateDelivery(currentDriving, addTip()); // calculating amount of delivery
					calculateSalary(amount);
				}
			}
		}

	}

	private boolean isDayFinished() {// checking if day is over
		return deliveries.top() == null;
	}

	@Override
	public void calculateSalary(double input) { // calculate salary
		salary += input;

	}

}
