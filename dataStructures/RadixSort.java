package radixx;

import java.util.NoSuchElementException;
import java.lang.Math;


public class RadixSort {
	
	public static void main (String[]args)
	{
		int[] testArr = {54, 25, 330, 81, 11, 1, 3, 40, 64, 55, 39};
		
		int[] toPrint = Radix.sort(testArr);
		
		System.out.println("Array is:");
		for (int i = 0; i < toPrint.length; ++i)
		{
			System.out.println(toPrint[i]);
		}
		
		System.out.println("Array descending is:");
		for (int i = toPrint.length-1; i >= 0; --i)
		{
			System.out.println(toPrint[i]);
		}
		
	}
}

class Queue<T>
{
	private int length;
	private Node<T> front;
	private Node<T> rear;
	
	//Creates a Queue of length 0.
	public Queue()
	{
		setLength(0);
		front = rear = null;
	}
	
	//Adds a Node to the back of the Queue.
	void enqueue(T data)
	{
		Node<T> nn = new Node<T>(data);
		if(getLength() == 0)
		{
			front = nn;
		}else
		{
			rear.setNext(nn);
		}
		rear = nn;
		setLength(getLength() + 1);
	}
	
	//Returns a Node from the front of the Queue and removes it.
	T dequeue() throws NoSuchElementException
	{
		if (getLength() == 0)
		{
			throw new NoSuchElementException("Queue was empty");
		}
		
		T result = front.getValue();
		front = front.getNext();
		setLength(getLength() - 1);
		if (getLength() == 0)
		{
			rear = null;
		}
		return result;
	}
	
	//Returns the front of the Queue without removing it.
	T first() throws NoSuchElementException
	{
		if (getLength() == 0)
		{
			throw new NoSuchElementException("Queue was empty");
		}else
		{
			return front.getValue();
		}
	}
	
	//Returns the last element of the Queue without removing it.
	T last() throws NoSuchElementException
	{
		if (getLength() == 0)
		{
			throw new NoSuchElementException("Queue was empty");
		}else
		{
			return rear.getValue();
		}
	}
	
	//Returns true if the Queue is empty.
	boolean isEmpty()
	{
		return getLength() == 0;
	}
	
	//Clears the Queue.
	void clearQueue()
	{
		front = null;
		rear = null;
		setLength(0);
	}
	
	//Traverses the Queue in ascending order.
	void traverseAscending() {
		Node<T> curr = front;

		for (curr = front; curr != null; curr = curr.getNext()) {
			System.out.println(curr.getValue());
		}
	}
	
	//Traverses the Queue in descending order.
	void traverseDescending() {
		Node<T> curr = rear;
		
		for (curr = rear; curr != null; curr = curr.getPrev()) 
		{
			System.out.println(curr.getValue());
		}
	}

	//Gets the length of the Queue.
	public int getLength() {
		return length;
	}

	//Sets the length of the Queue.
	public void setLength(int length) {
		this.length = length;
	}

}

class Node<T>{
	private T value;
	private Node<T> next;
	private Node<T> prev;

	//Constructor for Node class, assigns a generic T value to the Node.
	Node(T value) {
		this.value = value;
	}

	//Gets the value of Node.
	T getValue() {
		return value;
	}

	//Sets the value of the Node to value.
	void setValue(T value) {
		this.value = value;
	}

	//Gets the next Node.
	Node<T> getNext() {
		return next;
	}
	
	Node<T> getPrev() {
		return prev;
	}

	//Sets the next Node.
	void setNext(Node<T> n) {
		next = n;
	}

	//Checks to see if there is a Node next to it.
	boolean hasNext() {
		if (getNext() == null)
			return false;
		else
			return true;
	}
}

class Radix {
	
	//Does a Radix sort using an array of 10 Queues as buckets 0-9,
	//Starting with the least significant digit.
	public static int[] sort(int[] arr)
	{
		Queue<Integer>[] qArr = new Queue[10];
		for(int i = 0; i < 10; ++i)
		{
			qArr[i] = new Queue<Integer>();
		}
		int arraySize = arr.length;
		int maxDigits = getMaxLength(arr, arraySize);
		int pow10 = 1;
		
		/**The outer loop iterates through each digit position up to a 
		 * maxDigits given by getMaxLength().
		 */
		for (int digitIndex = 0; digitIndex < maxDigits; ++digitIndex)
		{
			/**The loop goes through each element of the array
			 * and we compute its bucketIndex based on the digit at digitIndex.
			 */
			for (int i = 0; i < arraySize; ++i)
			{
				int bucketIndex = Math.abs(arr[i] / pow10) % 10;
				qArr[bucketIndex].enqueue(arr[i]);
			}
			
			//Empties the buckets into the array.
			for (int i = 0, arrayIndex = 0; i < 10; ++i)
			{
				while (!qArr[i].isEmpty())
				{
					arr[arrayIndex++] = qArr[i].dequeue();
				}
			}
			pow10 = 10* pow10;
		}
		return arr;
	}

	//Gets the max number of digits from the array.
	 static int getMaxLength(int[] arr, int arraySize) {
		int maxDigits = 0;
		for (int i = 0; i < arraySize; ++i)
		{
			int digitCount = RadixGetLength(arr[i]);
			if (digitCount > maxDigits)
			{
				maxDigits = digitCount;
			}
		}
		return maxDigits;
	}

	 //Gets the number of digits in an int.
	static int RadixGetLength(int value) {
		int length = (int) (Math.log10(value) + 1); 
		return length;
	}
}
