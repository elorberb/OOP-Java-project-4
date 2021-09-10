
abstract public class Employee implements Runnable{ // abstarct employee class
	protected String name;
	protected double salary;
	protected boolean isDayFinished = false;


	public Employee(String name) {// Constructor
		this.name = name;
		this.salary = 0;
	}

	public abstract void calculateSalary(double time);// calculate salary 


	public double getSalary() {
		return this.salary;
	}

	public String getName() {
		return name;
	}
	
	



}
