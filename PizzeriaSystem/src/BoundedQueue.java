import java.util.Vector;

class BoundedQueue<T> extends Queue<T> {
	private int maxSize;

	public BoundedQueue() {// Constructor
		super();
		this.maxSize = 12;
	}

	public BoundedQueue(int size) {// Constructor with specific size
		buffer = new Vector<T>();
		this.maxSize = size;
	}

	public synchronized void insert(T item) {// insert method
		while (buffer.size() >= maxSize) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		buffer.add(item);
		notifyAll();
	}

}