import java.util.Vector;

import javax.sql.rowset.Joinable;

public class Clerk extends Employee {
	private int pizzaBasePrice;
	private Queue<Call> callLine;
	private Queue<Order> orders;
	private Queue<Call> managerLine;
	private Vector<Call> callsAnswered;
	private Vector<Order> allOrders;
	private int dayCalls;

	public Clerk(String name, Queue<Call> callLine, Queue<Order> orders, Queue<Call> managerLine,
			Vector<Call> callsAnswered, Vector<Order> allOrders) {// Constructor
		super(name);
		this.dayCalls = 0;
		this.pizzaBasePrice = 25;
		this.callLine = callLine;
		this.orders = orders;
		this.managerLine = managerLine;
		this.callsAnswered = callsAnswered;
		this.allOrders = allOrders;

	}

	public void setAllDayCalls(int allDayCalls) {// set all day calls
		this.dayCalls = allDayCalls;
	}

	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (!isDayFinished()) {// while day is not over
			Call c = callLine.extract();// extracting call from call line
			callsAnswered.add(c);// adding to calls that have been answered
			calculateSalary(0);
			try {
				Thread.sleep((long) (c.getCallDuration()) * 1000);// sleeps while call in on
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (c.getNumOfPizzas() < 10) {// if number of pizzas in call is under 10

				Order o = createOrder(c);// create order and insert in to orders queue
				allOrders.add(o);
				orders.insert(o);
			}

			else {// else move the call to manager line
				try {
					Thread.sleep(500);

				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				managerLine.insert(c);
			}
		}
		callLine.insert(null);
	}

	public Vector<Call> getCallsAnswered() {// get call answered vector
		return callsAnswered;
	}

	private synchronized Order createOrder(Call c) { // creating order
		int numOfPizzas = c.getNumOfPizzas();
		double totalPrice = pizzaBasePrice * numOfPizzas;
		String address = c.getAddress();
		long creditCard = c.getCreditCardNum();
		int arrivalTime = c.getArrivalTime();
		Order o = new Order(numOfPizzas, address, creditCard, arrivalTime, totalPrice);
		return o;
	}

	private boolean isDayFinished() {// checks if day is over
		return callsAnswered.size() == dayCalls || callLine.top() == null;

	}

	public void calculateSalary(double amount) {// calculate salary
		this.salary += 2;

	}

}
