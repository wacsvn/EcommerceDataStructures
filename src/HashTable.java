import java.util.ArrayList;

public class HashTable<T> {

	private int numElements;
	private ArrayList<List<T>> Table;

	/**
	 * Constructor for the hash table. Initializes the Table to be sized according
	 * to value passed in as a parameter Inserts size empty Lists into the table.
	 * Sets numElements to 0
	 * 
	 * @param size the table size
	 */
	public HashTable(int size) {
		Table = new ArrayList<List<T>>(size);
		for (int i = 0; i < size; i++) {
			List temp = new List<T>();
			Table.add(temp);
		}
		this.numElements = 0;
	}

	/** Accessors */

	/**
	 * returns the hash value in the Table for a given Object
	 * 
	 * @param t the Object
	 * @return the index in the Table
	 */
	private int hash(T t) {
		int code = t.hashCode();
		return code % Table.size();
	}

	/**
	 * counts the number of elements at this index
	 * 
	 * @param index the index in the Table
	 * @precondition 0 <= index < Table.length
	 * @return the count of elements at this index
	 * @throws IndexOutOfBoundsException
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= Table.size()) {
			throw new IndexOutOfBoundsException("countBucket(): " + "index is outside bounds of the table");
		}
		return Table.get(index).getLength();
	}

	/**
	 * returns total number of elements in the Table
	 * 
	 * @return total number of elements
	 */
	public int getNumElements() {
		return numElements;
	}

	/**
	 * Accesses a specified element in the Table
	 * 
	 * @param t the element to search for
	 * @return the element stored in the Table, or null if this Table does not
	 *         contain t.
	 * @precondition t != null
	 * @throws NullPointerException when t is null
	 */
	public T get(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("get(): t is null");
		} else {
			int bucket = hash(t);
			//System.out.println("Getting at bucket: " + bucket);
			Table.get(bucket).placeIterator();
			for (int i = 0; i < Table.get(bucket).getLength(); i++) {	
				if (Table.get(bucket).getIterator().equals(t)) {
					//System.out.println("Found! returning customer");
					return Table.get(bucket).getIterator();
				}
				Table.get(bucket).advanceIterator();
			}
		}
		//System.out.println("returning null trying to find: " + t.toString());
		return null;

	}

	/**
	 * Determines whether a specified key is in the Table
	 * 
	 * @param t the element to search for
	 * @return whether the element is in the Table
	 * @precondition t != null
	 * @throws NullPointerException when t is null
	 */
	public boolean contains(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("contains(): t is null");
		} else {
			int bucket = hash(t);
			Table.get(bucket).placeIterator();
			for (int i = 0; i < Table.get(bucket).getLength(); i++) {
				if (Table.get(bucket).getIterator().equals(t)) {
					return true;
				}
				Table.get(bucket).advanceIterator();
			}
		}
		return false;
	}

	/** Mutators */

	/**
	 * Inserts a new element in the Table at the end of the chain in the bucket
	 * 
	 * @param t the element to insert
	 * @precondition t != null
	 * @throws NullPointerException when t is null
	 */
	public void insert(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("insert(): t is null");
		} else {
			int bucket = hash(t);
			//System.out.println("Bucket insert: " + bucket);
			Table.get(bucket).addLast(t);
			numElements++;
		}

	}

	/**
	 * removes the key t from the Table calls the hash method on the key to
	 * determine correct placement has no effect if t is not in the Table or for a
	 * null argument
	 * 
	 * @param t the key to remove
	 * @throws NullPointerException when t is null
	 */
	public void remove(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("remove(): t is null");
		}
		int bucket = hash(t);
		Table.get(bucket).placeIterator();
		for (int i = 0; i < Table.get(bucket).getLength(); i++) {
			if (Table.get(bucket).getIterator().equals(t)) {
				Table.get(bucket).removeIterator();
				numElements--;
				return;
			}
			Table.get(bucket).advanceIterator();
		}
	}

	/**
	 * Clears this hash table so that it contains no keys.
	 */
	public void clear() {
		List temp = new List<>();
		for (int i = 0; i < numElements; i++) {
			Table.set(i, temp);

		}
		numElements = 0;
	}

	/** Additional Methods */

	/**
	 * Prints all the keys at a specified bucket in the Table. Tach key displayed on
	 * its own line, with a blank line separating each key Above the keys, prints
	 * the message "Printing bucket #<bucket>:" Note that there is no <> in the
	 * output
	 * 
	 * @param bucket the index in the Table
	 */
	public void printBucket(int bucket) {
		if (bucket > Table.size() || bucket == -1) {
			throw new IndexOutOfBoundsException("The bucket at that index does not exist");
		} else {
			System.out.print("\nBucket #" + (bucket) + ": \n");
			if (Table.get(bucket) != null) {
				Table.get(bucket).placeIterator();
				for (int i = 0; i < Table.get(bucket).getLength(); i++) {
					System.out.print(Table.get(bucket).getIterator());
					Table.get(bucket).advanceIterator();
				}
			}
		}
	}

	/**
	 * Prints the first key at each bucket along with a count of the total keys with
	 * the message "+ <count> -1 more at this bucket." Each bucket separated with
	 * two blank lines. When the bucket is empty, prints the message "This bucket is
	 * empty." followed by two blank lines
	 */
	public void printTable() {
		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).isEmpty()) {

			} else {
				System.out.println("\nBucket #" + (i + 1));
				System.out.print(Table.get(i).getFirst());
				System.out.println("\nFinished Printing Bucket");
			}
		}
	}

	/**
	 * Starting at the first bucket, and continuing in order until the last bucket,
	 * concatenates all elements at all buckets into one String
	 */
	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).getLength() != 0) {
				//System.out.println("Start customer: ");
				//System.out.println("Length: " + Table.get(i).getLength());
				result += Table.get(i).toString();
				//System.out.println("End Customer\n");
			}
		}
		return result;
	}

}
