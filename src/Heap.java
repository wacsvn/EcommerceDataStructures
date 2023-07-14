import java.util.Comparator;
import java.util.ArrayList;
import java.util.*;

public class Heap<T> {

	private int heapSize;
	private ArrayList<T> heap;
	private Comparator<T> comparator;

	/**
	 * Constructors/
	 * 
	 * /** Constructor for the Heap class
	 * 
	 * @param data       an unordered ArrayList
	 * @param comparator determines organization of heap based on priority
	 */
	public Heap(ArrayList<T> data, Comparator<T> comparator) {
		heap = new ArrayList<T>();
		heap.add(null); // A[0] = null, 
		//System.out.println("Size of Heap: " + heap.size());
		for (int i = 0; i < data.size(); i++) {
			//heap.set(i, data.get(i));
			heap.add(data.get(i));
			//System.out.println("Adding " + data.get(i).toString() + ", size: " + heap.size());
		}

		heapSize = data.size();
		this.comparator = comparator;
		buildHeap();
	}

	/** Mutators */

	/**
	 * Converts an ArrayList into a valid max heap. Called by constructor Calls
	 * helper method heapify
	 */
	public void buildHeap() {
		//System.out.println("Size of Heap: " + heapSize);
		for (int i = (heapSize / 2); i >= 1; i--) {
			heapify(i);
		}
	}

	/**
	 * helper method to buildHeap, remove, and sort bubbles an element down to its
	 * proper location within the heap
	 * 
	 * @param index an index in the heap
	 */
	private void heapify(int index) {
		//System.out.println("Index: " + index + "\n");
		int left = get_left(index);
		int right = get_right(index);
		int max = index;
		
		//System.out.println("Left: " + left + "\nRight: " + right + "\n");

		if(left <= heapSize && comparator.compare(heap.get(left), heap.get(index)) > 0){
			max = left;
		}
		
		if(right <= heapSize && comparator.compare(heap.get(right), heap.get(max)) > 0) {
			max = right;
		}


		if (index != max) {
			//System.out.println("Swapping " + max + " with " + index);
			T temp = heap.get(index);
			heap.set(index, heap.get(max));
			heap.set(max, temp);
			heapify(max);
		}

	}

	/**
	 * Inserts the given data into heap Calls helper method heapIncreaseKey
	 * 
	 * @param key the data to insert
	 */
	public void insert(T key) {
		heap.add(key);
		heapSize++;
		heapIncreaseKey(heapSize, key);

	}

	/**
	 * Helper method for insert bubbles an element up to its proper location
	 * 
	 * @param index the current index of the key
	 * @param key   the data
	 */
	private void heapIncreaseKey(int index, T key) {
		if(comparator.compare(key, heap.get(index)) >= 0) {
			heap.set(index, key);
			//System.out.println("Increasing Key...");
			while(index > 1 && comparator.compare(heap.get(getParent(index)), heap.get(index)) < 0) {
				//System.out.println("Bubbling up " + heap.get(index) + " To " + heap.get(getParent(index)));
				T temp = heap.get(getParent(index));
				heap.set(getParent(index), heap.get(index)); //Setting the parent index with the key
				heap.set(index, temp); //Setting the index of the key to the parent
				index  = getParent(index);
			}
		}
	

	}

	/**
	 * removes the element at the specified index Calls helper method heapify
	 * 
	 * @param index the index of the element to remove
	 */
	public void remove(int index) {

		T lastElement = heap.get(heapSize);
		heap.set(index, lastElement);
		heapSize--;
		heapify(index);

	}

	/** Accessors */

	/**
	 * returns the maximum element (highest priority)
	 * 
	 * @return the max value
	 */
	public T getMax() {
		return heap.get(1);
	}

	/**
	 * returns the location (index) of the parent of the element stored at index
	 * 
	 * @param index the current index
	 * @return the index of the parent
	 * @precondition 0 < i <= heap_size
	 * i < 0, i > heap_size
	 * @throws IndexOutOfBoundsException
	 */
	public int getParent(int index) throws IndexOutOfBoundsException {
		// 
		if (index < 0 || index > heapSize) {
			throw new IndexOutOfBoundsException("GetParent: Index " + index + " Out of Bounds!");
		}

		else if (index % 2 == 0) {
			return index / 2;
		} else {
			return (index - 1) / 2;
		}

	}

	/**
	 * returns the location (index) of the left child of the element stored at index
	 * 
	 * @param index the current index
	 * @return the index of the left child
	 * @precondition 0 < i <= heap_size
	 * @throws IndexOutOfBoundsException
	 */
	public int get_left(int index) throws IndexOutOfBoundsException {
		if (index <= 0 || index > heapSize) {
			throw new IndexOutOfBoundsException("get_left() Index: " +
			 index + " Out of Bounds!\nHeap Size: " + heapSize);
		}

		return 2 * index;
	}

	/**
	 * returns the location (index) of the right child of the element stored at
	 * index
	 * 
	 * @param index the current index
	 * @return the index of the right child
	 * @precondition 0 < i <= heap_size
	 * @throws IndexOutOfBoundsException
	 */
	public int get_right(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > heapSize) {
			throw new IndexOutOfBoundsException("get_right(): Index: " + index + " Out of Bounds!\nHeap Size: " +
															 heapSize);
		}
		return (2 * index) + 1;
	}

	/**
	 * returns the heap size (current number of elements)
	 * 
	 * @return the size of the heap
	 */
	public int getHeapSize() {
		return heapSize;
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public T getElement(int index) throws IndexOutOfBoundsException {
		if (index > heapSize) {
			throw new IndexOutOfBoundsException("getElement: Index " + index + " is out of bounds!");
		}
		return heap.get(index);
	}

	/** Additional Operations */

	/**
	 * Creates a String of all elements in the heap
	 */
	@Override
	public String toString() {
		String result = "";

		for (int i = 1; i < heapSize; i++) {
			
			result += " " + heap.get(i);
			

		}

		return result + "\n";
}

	/**
	 * Uses the heap sort algorithm to sort the heap into ascending order Calls
	 * helper method heapify
	 * 
	 * @return an ArrayList of sorted elements
	 * @postcondition heap remains a valid heap
	 */
	public ArrayList<T> sort() {
		for (int i = heapSize; i >= 2; i--) {
			T temp = heap.get(1);
			heap.set(1, heap.get(i));
			heap.set(i, temp);
			heapSize--;
			heapify(1);
		}

		return new ArrayList<T>();
	}

}

