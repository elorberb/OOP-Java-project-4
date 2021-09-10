import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Pizzeria {
	private InformationSystem pizzaSystem;
	private Manager manager;
	private Queue<Call> callsLine;
	private Queue<Call> managerLine;
	private Queue<Order> orders;
	private Vector<PizzaDelivery> deliveredPizzas;
	private BoundedQueue<PizzaDelivery> deliveries;
	private Vector<Employee> employees; // vector of all of the employees.
	private Vector<Order> allOrders; // 
	private int workingTime;
	private int numOfpizzaGuys;

	public Pizzeria(String file, int workingTime, int numOfpizzaGuys) {// Constructor
		this.numOfpizzaGuys = numOfpizzaGuys;
		this.workingTime = workingTime;
		callsLine = new Queue<Call>();
		managerLine = new Queue<Call>();
		orders = new Queue<Order>();
		pizzaSystem = new InformationSystem();
		deliveries = new BoundedQueue<PizzaDelivery>();
		deliveredPizzas = new Vector<PizzaDelivery>();
		employees = new Vector<Employee>();
		allOrders = new Vector<Order>();
		buildEmployeesV();
		Vector<Call> allCalls = readCalls(file);
		int allDayCalls = allCalls.size();
		manager = new Manager(managerLine, orders, pizzaSystem, callsLine, deliveries, employees, allDayCalls,
				deliveredPizzas, allOrders);
		insertCallsToLine(allCalls);
		updateClerkCalls(allDayCalls);
		Vector<Thread> employeesThreads = createThreadsV(); // creating the employees threads.
		startThreads(employeesThreads);
		startManagerDay(manager);

	}

	private void addClerks() { // adding clerks to employees vector
		Vector<Call> callsAnswered = new Vector<Call>();
		Clerk clerk1 = new Clerk("yuli", callsLine, orders, managerLine, callsAnswered, allOrders);
		Clerk clerk2 = new Clerk("itay", callsLine, orders, managerLine, callsAnswered, allOrders);
		Clerk clerk3 = new Clerk("gili", callsLine, orders, managerLine, callsAnswered, allOrders);
		employees.add(clerk1);
		employees.add(clerk2);
		employees.add(clerk3);

	}

	private void addKitchenWorkers() {// adding kitchen workers to employees vector
		KitchenWorker KitchenWorker1 = new KitchenWorker(workingTime, "dani", pizzaSystem, deliveries);
		KitchenWorker KitchenWorker2 = new KitchenWorker(workingTime, "evya", pizzaSystem, deliveries);
		KitchenWorker KitchenWorker3 = new KitchenWorker(workingTime, "jordan", pizzaSystem, deliveries);
		employees.add(KitchenWorker1);
		employees.add(KitchenWorker2);
		employees.add(KitchenWorker3);

	}

	private void addSchedulers() {// adding schedulers to employees vector
		Vector<Order> ordersMade = new Vector<Order>();
		Scheduler scheduler1 = new Scheduler("Alon", orders, pizzaSystem);
		Scheduler scheduler2 = new Scheduler("Hassid", orders, pizzaSystem);
		employees.add(scheduler1);
		employees.add(scheduler2);
	}

	private Vector<Call> readCalls(String file) { // reads from the calls file.
		BufferedReader br = null;
		Vector<Call> callsV = new Vector<Call>();
		try {
			br = new BufferedReader(new FileReader(file));// create new buffer reader and file reader
			String line;
			line = br.readLine();// reading lines from file
			while ((line = br.readLine()) != null) {// runs until line is null
				String temp[] = line.split("\\t");// splitting string by tab
				Call c = newCall(temp); // new call
				callsV.add(c);
			}

		} catch (IOException e) {// catching io exception3
			e.printStackTrace();
		} finally {
			try {
				br.close();// closing buffer reader
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return callsV; // return vector with all the calls
	}
	
	private Call newCall(String temp[]) { // creating new call from the file calls
		int creditCardNum = Integer.parseInt(temp[0]);
		int numOfPizzas = Integer.parseInt(temp[1]);
		int arrivalTime = Integer.parseInt(temp[2]);
		double callDuration = Double.parseDouble(temp[3]);
		String address = new String(temp[4]);
		Call c = new Call(creditCardNum, numOfPizzas, arrivalTime, callDuration, address, callsLine);
		return c;
		
	}

	private void addPizzaGuys() { // adding the Pizza Guys to the employees
		// vector
		for (int i = 0; i < this.numOfpizzaGuys; i++) {
			PizzaGuy pg = new PizzaGuy("Itay " + i, deliveries, deliveredPizzas);
			employees.add(pg);
		}
	}

	private void insertCallsToLine(Vector<Call> c) {// insert calls to calls line and start threads
		for (Call call : c) {
			Thread t = new Thread(call);
			t.start();
		}
	}

	private void buildEmployeesV() { // creating vector of the employees
		addClerks();
		addKitchenWorkers();
		addSchedulers();
		addPizzaGuys();

	}

	private Vector<Thread> createThreadsV() {// creating threads for employees vector
		Vector<Thread> tv = new Vector<Thread>();
		for (Employee emp : employees) {
			Thread t = new Thread(emp);
			tv.add(t);
		}
		return tv;
	}

	private void startThreads(Vector<Thread> threads) { // starting thread
		for (Thread t : threads) {
			t.start();
		}
	}

	private void updateClerkCalls(int allDayCalls) {// updating clerks how many calls for the day

		for (Employee e : employees) {
			if (e instanceof Clerk) {
				((Clerk) e).setAllDayCalls(allDayCalls);
			}
		}

	}

	private void startManagerDay(Manager m) {// starting manager day
		Thread t = new Thread(m);
		t.start();

	}

}
