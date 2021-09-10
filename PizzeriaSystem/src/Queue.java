import java.util.Vector;

public class Queue<T> {
	protected Vector<T> buffer;

	public Queue() {
		buffer = new Vector<T>();
	}

	public synchronized void insert(T item) {// insert to the queue
		buffer.add(item);
		this.notifyAll();
	}

	public synchronized T extract() {// extract from the queue
		while (buffer.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		T t = buffer.elementAt(0);
		buffer.remove(t);
		return t;
	}
			
	public synchronized T top() { // top method to check if day is over
		while (buffer.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return buffer.elementAt(0);
	}
	
	public int size() { // size of the queue
		return buffer.size();
	}
	
	public boolean isEmpty() { // checks if empty
		return buffer.isEmpty();
	}
	
	
	
}
