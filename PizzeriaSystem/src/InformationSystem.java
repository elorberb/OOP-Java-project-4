import java.util.Vector;

public class InformationSystem {
	private Vector<Order> farD; // far orders
	private Vector<Order> shortD; // short orders
	private Vector<Order> meduimD; // meduim orders

	public InformationSystem() {// Constructor
		farD = new Vector<Order>();
		meduimD = new Vector<Order>();
		shortD = new Vector<Order>();

	}

	public synchronized void insertOrder(Order o) {// inserting order to the system
		if (o == null) {
			shortD.add(o);
			this.notifyAll();
			return;
		} else if (o.getDistance() <= 3) {// checking to which distance category to insert
			shortD.add(o);

		} else if (o.getDistance() > 3 && o.getDistance() <= 8) {
			meduimD.add(o);

		} else if (o.getDistance() > 8) {
			farD.add(o);
		}
		this.notifyAll();
	}

	public synchronized Order extractOrder() {// extracting order from the system
		if (!shortD.isEmpty()) {
			Order o1 = shortD.elementAt(0);
			shortD.remove(o1);
			return o1;
		} else if (!meduimD.isEmpty()) {
			Order o2 = meduimD.elementAt(0);
			meduimD.remove(o2);
			return o2;
		} else if (!farD.isEmpty()) {
			Order o3 = farD.elementAt(0);
			farD.remove(o3);
			return o3;
		} else {
			return null;
		}

	}

}
