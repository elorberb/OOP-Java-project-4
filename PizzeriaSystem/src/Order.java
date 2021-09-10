
public class Order  {
	private int numOfPizzas;
	private String address;
	private long creditCardNum;
	private double price;
	private int arrivalTime;
	private double distance;
	private static int countedSerial=0;
	private int serialNum;

	
	public Order(int numOfPizzas, String address, long creditCard,int arrivalTime, double price) {// Constructor
		this.numOfPizzas = numOfPizzas;
		this.address = address;
		this.creditCardNum = creditCard;
		this.price = price;
		this.arrivalTime = arrivalTime;
		this.serialNum = createSerialNumber();
		
	}
	
	private synchronized int createSerialNumber() { // creating serial num method
		countedSerial++;
		return countedSerial;
	}

	public double getPrice() {// get price
		return price;
	}

	public synchronized int getSerialNum() {// get serial
		return serialNum;
	}


	public void setDistance(double distance) {// set distance
		this.distance = distance;
	}
	
	public double getDistance() {// get distance
		return this.distance;
	}

	public int getNumOfPizzas() {// get number of pizzas
		return numOfPizzas;
	}

	public String getAddress() {// get address
		return  new String(address);
	}

	public long getCreditCardNum() {// get credit card
		return creditCardNum;
	}
	
	public int getOrderTimeArrival() {// get order arrival time
		return arrivalTime;
	}
	
	
	public String toString() {// to string 
		return "Order [numOfPizzas=" + numOfPizzas + ", address=" + address + ", creditCardNum=" + creditCardNum
				+ ", price=" + price + ", distance=" + distance + ", serialNum="
				+ serialNum + "]";
	}

}
