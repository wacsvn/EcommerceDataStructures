import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<T> {

	private class Node {

		private T data;

		private Node next;

		public Node prev;

		public Node(T data) {

			this.data = data;

			this.next = null;

			this.prev = null;

		}

	}

	private int length;

	private Node first;

	private Node last;

	private Node iterator;

	/**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new List with default values
	 *
	 * @postcondition all private field are assigned with a default value.
	 */

	public List() {

		first = null;

		last = null;

		length = 0;

		iterator = null;

	}

	/**
	 * Copy constructor
	 *
	 * Creates a copy of an original list
	 *
	 * @postcondition a copied List created
	 */

	public List(List<T> orig) {

		List<T> l = new List<T>();

		if (orig.first == null) {
			return;
		} else if (orig.getLength() == 0) {
			length = 0;
			last = null;
			first = null;
			iterator = null;
		} else {
			Node temp = orig.first; // making new list
//temp = first
//first has .next,.prev
			while (temp != null) {
				this.addLast(temp.data); // copies to copied node
				temp = temp.next; // making the node go forward
			}
			this.iterator = orig.iterator; // copy iterator over
		}
	}

	/**** MUTATORS ****/

// removeFirst
	/**
	 *
	 * removes the element at the front of the list
	 *
	 * @precondition list cannot be empty.
	 *
	 * @postcondition the element at head is removed and length decreased by 1.
	 *
	 * @throws NoSuchElementException when precondition is violated
	 *
	 */

	public void removeFirst() throws NoSuchElementException {

		if (length == 0) {

			throw new NoSuchElementException("removeFirst() : Cannot remove "

					+ "from an empty List!");
		} else if (length == 1) {
			
			first = last = null;
			
		} else {

			first = first.next;
			first.prev = null;

		}

		length--;
	}

// removeLast
	/**
	 *
	 * removes the element at the end of the list
	 *
	 * @precondition list cannot be empty.
	 *
	 * @postcondition the element at head is removed and length decreased by 1.
	 *
	 * @throws NoSuchElementException when precondition is violated
	 *
	 */

	public void removeLast() throws NoSuchElementException {

		if (length == 0) {

			throw new NoSuchElementException("removeLast: list is empty. Nothing to remove.");

		} else if (length == 1) {

			first = last = null;

		} else {

			last = last.prev;
			last.next = null;

		}

		length--;

	}
	//1
	//singly linked list n

// addFirst

	/**
	 *
	 * Creates a new first element
	 *
	 * @param the data to insert at the front of the list
	 *
	 * @postcondition new node at the head is created and length increased by 1.
	 *
	 */

	public void addFirst(T data) {

		if (first == null) {

			first = last = new Node(data);

		} else {

			Node n = new Node(data);

			n.next = first;
			first.prev = n;

			first = n;

		}

		length++;

	}

// addLast

	/**
	 *
	 * Creates a new last element
	 *
	 * @param the data to insert at the
	 *
	 *            end of the list
	 *
	 * @postcondition new node at the tail is created and length increased by 1.
	 *
	 */

	public void addLast(T data) {

		if (first == null) {

			first = last = new Node(data);

		}

		else {

			Node n = new Node(data);

			last.next = n;

			n.prev = last;

			last = n;

		}

		length++;

	}
	//double 1
	//singly 1

// placeIterator

	/**
	 * Places the iterator on the head
	 */
	public void placeIterator() {
		iterator = first;
	}

// removeIterator
	/**
	 * remove the iterator in the list
	 *
	 * @precondition iterator != null
	 * @postcondition iterator points to null. a pointed node removed from the list.
	 * @throws NullPointerException when precondition is violated
	 **/
	public void removeIterator() throws NullPointerException {
		if (length == 0) {
			throw new NullPointerException("removeIterator(): " + "cannot remove when iterator is null.");
		} else if (iterator == first) {
			removeFirst();
		} else if (iterator == last) {
			removeLast();
		} else {
			iterator.prev.next = iterator.next;
			iterator.next.prev = iterator.prev;
			iterator = null;
			length--;
		}

	}

// addIterator
	/**
	 * Inserts new data in the List after the iterator
	 *
	 * @param data the new data to insert
	 * @precondition iterator != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void addIterator(T data) throws NullPointerException {
		if (offEnd()) { // precondition
			throw new NullPointerException("addIterator: " + "Iterator is off end. Cannot add.");
		} else if (iterator == last) { // edge case
			addLast(data);
		} else { // general case
			Node n = new Node(data);
			
			n.next = iterator.next;
			n.prev = iterator;
			iterator.next.prev = n;
			iterator.next = n;

// Add a couple of steps for a doubly linked list

			length++;
		}
	}

// advanceIterator
	/**
	 * Advances the iterator by one node in the List
	 *
	 * @precondition !offEnd()
	 * @postcondition iterator references iterator.next
	 * @throws NullPointerException when the precondition is violated
	 */
	public void advanceIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("advanceIterator: " + "Iterator is null and cannot advance");
		}
		iterator = iterator.next;
	}

// reverseIterator
	/**
	 * Reverses the iterator by one node in the list
	 *
	 * @precondition !offEnd()
	 * @postcondition iterator references iterator.prev
	 * @throws NullPointerException when the precondition is violate
	 */
	public void reverseIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("reverseIterator: " + "Iterator is null and cannot reverse");
		}
		iterator = iterator.prev;
	}

	/**
	 *
	 * Returns whether the list is currently empty
	 *
	 * @return whether the list is empty
	 *
	 */

	public boolean isEmpty() {

		return length == 0;

	}

	/**** ACCESSORS ****/

	/**
	 *
	 * Returns the value stored in the first node
	 *
	 * @precondition !isEmpty.
	 *
	 * @return the value stored at the first node
	 *
	 * @throws NoSuchElementException when precondition is violated
	 *
	 */

	public T getFirst() throws NoSuchElementException {

		if (length == 0) {

			throw new NoSuchElementException("getFirst(): List is Empty. "

					+ "No data to access!");

		}

		return first.data;

	}

	/**
	 *
	 * Returns the value stored in the last node
	 *
	 * @precondition !isEmpty.
	 *
	 * @return the value stored in the last node
	 *
	 * @throws NoSuchElementException when precondition is violated
	 *
	 */

	public T getLast() throws NoSuchElementException {

		if (length == 0) {

			throw new NoSuchElementException("getLast(): List is Empty. "

					+ "No data to access!");

		}

		return last.data;

	}

// is empty

// get Length
	/**
	 *
	 * Returns the current length of the list
	 *
	 * @return the length of the list from 0 to n
	 *
	 */

	public int getLength() {

		return length;

	}

// getIterator
	/**
	 * Returns the element currently pointed at by the iterator
	 *
	 * @precondition the iterator cannot be null
	 * @return the value pointed at by the iterator
	 * @throws NullPointerException when the precondition is violated
	 */
	public T getIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("getIterator: " + "iterator is off the end of the List");
		}
		return iterator.data;
	}
	/**
	 * Points the iterator at first and then advances it to the specified index
	 * 
	 * @param index the index where the iterator should be placed
	 * @precondition 0 < index <= length
	 * @throws IndexOutOfBoundsException when precondition is violated
	 */
	public void iteratorToIndex(int index) throws IndexOutOfBoundsException {
		if (index > length) {
			throw new IndexOutOfBoundsException("iteratorToIndex(): the index is out of bounds");
		} else {
			iterator = first;
			for (int i = 1; i < index; i++) {
				iterator = iterator.next;
			}
		}
	}

// offEnd
	/**
	 * Returns whether or not the iterator is off the end of the List, i.e. null
	 *
	 * @return whether the iterator is null
	 */
	public boolean offEnd() {
		return iterator == null;
	}

// equals

	/**
	 * overrides the equals method for object to compares this list to another list
	 * to see if they contain the same data in the same order.
	 *
	 * @return whether another list have same data in same order
	 * @throw IllegalStateException if the list is empty
	 */

	public boolean equals (List listb) {

//Make pointers
		Node a = this.first;
		Node b = listb.first;

		while (a != null && b != null) {
			if (a.data != b.data) {
				return false;
			}
			a = a.next;
			b = b.next;
		}
		return (a == null && b == null);
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 *
	 * List with each value with a whitespace
	 *
	 * in between the elements of the list.
	 *
	 * @return the List as a String for display
	 *
	 */
	@Override
	public String toString() {
		String result = "";
		Node temp = first;
		while (temp != null) {
			result += temp.data + "\n";
			temp = temp.next;
		}
		return result ;
	}

	/**
	 *
	 * List with each value on its own line
	 *
	 * At the end of the List a new line
	 *
	 * @return printed list with numbered line.
	 *
	 */

	public String printNumberedlist() {

		String result = "";
		Node temp = first;
		int count = 1;
		while (temp != null) {
			result += count + ". " + temp.data + "\n";
			temp = temp.next;
			count++;
		}
		return result;
	}
	
}
