package mergsplit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Node {
	private String value;
	private Node next;

	Node(String value) {
		this.value = value;
	}

	String getValue() {
		return value;
	}

	void setValue(String value) {
		this.value = value;
	}

	Node getNext() {
		return next;
	}

	void setNext(Node n) {
		next = n;
	}

	boolean hasNext() {
		if (getNext() == null)
			return false;
		else
			return true;
	}

}

class LList {

	Node head;

	void add(String value) {
		Node nn = new Node(value);
		if (head == null)
			head = nn;
		else
			nn.setNext(head);
		head = nn;
	}

	void traverse() {
		Node curr = head;

		for (curr = head; curr != null; curr = curr.getNext()) {
			System.out.println(curr.getValue());
		}
	}

	public int size() {
		int size = 0;
		Node curr = head;
		while (curr.hasNext()) {
			curr.getNext();
			size++;
		}
		return size;

	}

}

public class MergeSplit {

	public static void main(String[] args) {
		Node n = new Node("test");
		System.out.println(n.hasNext());
		LList myList = readFile("input.txt");
		
		assert (myList.size() % 2 == 0);
		LList firstHalf = new LList();
		LList latterHalf = new LList();

		splitMerge(myList, firstHalf, latterHalf);

		traverse(firstHalf);

		System.out.println();

		traverse(latterHalf);

		System.out.println();

		traverse(merge(firstHalf, latterHalf));
	}

	public static LList readFile(String input) {
		LList myList = new LList();
		File txtFile = new File(input);

		try {
			Scanner scan = new Scanner(txtFile);
			while (scan.hasNextLine()) {
				myList.add(scan.nextLine());

			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		return myList;
	}

	public static void splitMerge(LList fullList, LList myList1, LList myList2) {
		Node n1 = fullList.head;
		Node n2 = fullList.head;

		while (n2 != null) {
			myList1.add(n1.getValue());
			n1 = n1.getNext();
			n2 = n2.getNext().getNext();
		}
		while (n1 != null) {
			myList2.add(n1.getValue());
			n1 = n1.getNext();
		}
	}

	public static void traverse(LList print) {
		print.traverse();
	}

	public static LList merge(LList list1, LList list2) {
		Node n1 = list1.head;
		LList returnList = new LList();

		while (n1 != null) {
			returnList.add(n1.getValue());
			n1 = n1.getNext();
		}
		n1 = list2.head;

		while (n1 != null) {
			returnList.add(n1.getValue());
			n1 = n1.getNext();
		}

		return returnList;
	}

}
