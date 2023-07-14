import java.util.Comparator;

public class Customer extends Information {
	//Orders to hold the customer's order history
	private List<Order> orders;

	/**
	 * Default constructor for customer, calls the parent class default constructor
	 */
	public Customer() {
		super();

	}
	
	/**
	 * Single argument constructor, takes a login and sets a login, initializes a new order history
	 * 
	 * @param login
	 */
	public Customer(String login) {
		super();
		super.setLogin(login);
		this.orders = new List<>();
	}

	/**
	 * Two argument constructor, takes a login and password, 
	 * calls the parent class' two-argument constructor and
	 * initializes a new order history list
	 * 
	 * @param login - login of customer
	 * @param password - password of customer
	 */
	public Customer(String login, String password) {
		super(login, password);
		this.orders = new List<>();
	}

	/**
	 * Four argument constuctor, takes the fields listed below,
	 * calls parent class' 4-argument constructor
	 * initializes a list of orders
	 * 
	 * @param firstName - first name of customer
	 * @param lastName - last name of customer
	 * @param login - login of customer
	 * @param password - password of customer
	 */
	public Customer(String firstName, String lastName, String login, String password) {
		super(firstName, lastName, login, password);
		this.orders = new List<>();
	}

	/**
	 * 8-argument constructor, this fills all the parameters except for the order history
	 * calls its parents' 8-argument constructor and initializes a list of orders
	 * 
	 * @param firstName - first name of customer
	 * @param lastName - last name of customer
	 * @param login - login of customer
	 * @param password - password of customer
	 * @param address - address of customer
	 * @param city - city of customer
	 * @param state - state of customer
	 * @param zip - zip of customer
	 */
	public Customer(String firstName, String lastName, String login, String password, String address, String city,
			String state, String zip) {

		super(firstName, lastName, login, password, address, city, state, zip);
		this.orders = new List<>();
	}

	/**
	 * Copy Constructor for Customer
	 * takes a customer object and copies over its attributes including its order history
	 * by calling the 8-argument constructor and creating a deep copy of the customer's order history
	 * 
	 * @param c - customer object to be copied
	 */
	public Customer(Customer c) {
		this(c.getFirstName(), c.getLastName(), c.getLogin(), c.getPassword(), c.getAddress(), c.getCity(), c.getState(),
			c.getZip());

		//deep copy of list
		this.orders = new List<>(c.getOrders());
	}


	/*** GETTERS ***/

	/**
	 * getCustomerName - receives nothing
	 * 
	 * @return - the first name and last name separated by a space
	 */
	public String getCustomerName() {
		return super.getFirstName() + " " + super.getLastName();
	}

	/**
	 * getCustomerID - receives nothing
	 * @return the login of the customer
	 */
	public String getCustomerID() {
		return super.getLogin();
	}

	/**
	 * getCustomerPW - receives nothing
	 * @return - the password of the customer
	 */
	public String getCustomerPW() {
		return super.getPassword();
	}

	/**
	 * getCustomerAddress - receives nothing
	 * @return - the address of the customer
	 */
	public String getCustomerAddress() {
		return super.getAddress();
	}

	/**
	 * getCustomerCity - receives nothing
	 * @return - the city of the customer
	 */
	public String getCustomerCity() {
		return super.getCity();
	}

	/**
	 * getCustomerState - receives nothing
	 * @return - the state of the customer
	 */
	public String getCustomerState() {
		return super.getState();
	}

	/**
	 * getCustomerZip - receives nothing
	 * @return - the zip code of the customer
	 */
	public String getCustomerZip() {
		return super.getZip();
	}

	/**
	 * getOrders - receives nothing
	 * @return - a shallow copy of the customer's order history
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**** SETTERS ****/

	/**
	 * setCustomerID - updates the customer's login
	 * @param id - the id used to update the customer's id
	 */
	public void setCustomerID(String id) {
		super.setLogin(id);
	}

	/**
	 * setCustomerPW - updates the customer's password
	 * @param pw - the password used to update the customer's password
	 */
	public void setCustomerPW(String pw) {
		super.setPassword(pw);
	}

	/**
	 * setCustomerFirst - updates the customer's first name
	 * @param first - the first name used to update the customer's first name
	 */
	public void setCustomerFirst(String first) {
		super.setFirstName(first);
	}

	/**
	 * setCustomerLast - updates the customer's last name
	 * @param last - the last name used to update the customer's last name
	 */
	public void setCustomerLast(String last) {
		super.setLastName(last);
	}
	
	/**
	 * setCustomerAddress - updates the customer's address 
	 * @param address - the address used to update the customer's address 
	 */
	public void setCustomerAddress(String address) {
		super.setAddress(address);
	}

	/**
	 * setCustomerCity - updates the customer's city 
	 * @param city - the city used to update the customer's city 
	 */
	public void setCustomerCity(String city) {
		super.setCity(city);
	}

	/**
	 * setCustomerState - updates the customer's state 
	 * @param state - the state used to update the customer's state 
	 */
	public void setCustomerState(String state) {
		super.setState(state);
	}

	/**
	 * setCustomerZip - updates the customer's zip code 
	 * @param zip - the zip code used to update the customer's zip code 
	 */
	public void setCustomerZip(String zip) {
		super.setZip(zip);
	}


	/**
	 * equals 
	 * 
	 * @param - an object
	 * returns a boolean - True if the object is a customer object
	 * that has the same login and password as this customer object.
	 * Returns false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Customer)) {
			return false;
		} else {
			Customer otherCustomer = (Customer) o;
			return otherCustomer.getLogin().equals(super.getLogin())
					&& otherCustomer.getPassword().equals(super.getPassword());
		}
	}

	/**** ADDITIONAL CLASS MUTATORS/SETTERS ****/

	/**
	 * addOrder - takes an order object and appends a deep copy to the end of the
	 * customer's order history
	 * 
	 * @param order - order to be added to the order history
	 * returns nothing
	 */	
	public void addOrder(Order order) {
		Order insertOrder = new Order(order);
		this.orders.addLast(insertOrder);
	}

	/**
	 * updateShippingOrder
	 * changes a shipping order's status to "SHIPPED"
	 * 
	 * @param order - order in which needs to be shipped
	 */
	public void updateShippingOrder(Order order) {
		if(orders.isEmpty()) {
			System.out.println("This customer has no outstanding orders.");
		}else {
			orders.placeIterator();
			while(!orders.offEnd()) {
				if(orders.getIterator().getOrderID() == order.getOrderID()) {
					orders.getIterator().updateOrderStatus("SHIPPED");
					return;
				}
				orders.advanceIterator();
			}
		}
		System.out.println("The Order Was Not Found.....");
	}

	/***
	 * updateShippingOrderStatus - updates an order's shipping status
	 * 
	 * @param order - order in which needs to be updated 
	 * @param message - message to update the order
	 */
	public void updateShippingOrderStatus(Order order, String message) {
		if(orders.isEmpty()) {
			System.out.println("This customer has no outstanding orders.");
		}else {
			orders.placeIterator();
			while(!orders.offEnd()) {
				if(orders.getIterator().getOrderID() == order.getOrderID()) {
					orders.getIterator().updateOrderStatus(message);
					return;
				}
				orders.advanceIterator();
			}
		}
		System.out.println("The Order Was Not Found.....");
	}



	/***** ACCESSOR STRING FUNCTIONS *****/

	/**
	 * printOrders - takes nothing
	 * 
	 * prints to the screen the order history of this customer
	 * returns nothing
	 */
	public void printOrders() {
		System.out.println("\nYour Current Orders: \n" + this.orders.toString());
	}

	/**
	 * formatCustomerDataForOrderFileWrite - formats the data such that
	 * it can be written and re-read into a textfile into a customer hashtable
	 * 
	 * @param orderNumber - how many orders are within the customer
	 */
	public String formatCustomerDataForOrderFileWrite(int orderNumber) {
		String data = "";
		data += super.getFirstName() + "\n"; //Start of Customer Info
		data += super.getLastName() + "\n";
		data += super.getLogin() + "\n";
		data += super.getPassword() + "\n";
		data += super.getAddress() + "\n";
		data += super.getCity() + "\n";
		data += super.getState() + "\n";
		data += super.getAddress() + "\n"; //End Customer Info

		orders.placeIterator();

		//Formatting order data
		for(int i = 1; i < orderNumber; i++) {
			orders.advanceIterator();
		}
		data += orders.getIterator().formatOrderDataForFileWrite();

		return data;
	}

	/**
	 * formatCustomerDataForCustomerFileWrite() - takes nothing, formats the data such that 
	 * it can be written to a text file and be read in as a BST
	 * 
	 * @return - string containing formatted data of a customer
	 */
	public String formatCustomerDataForCustomerFileWrite() {
		String data = "";
		data += super.getFirstName() + "\n"; 
		data += super.getLastName() + "\n";
		data += super.getLogin() + "\n";
		data += super.getPassword() + "\n";
		data += super.getAddress() + "\n";
		data += super.getCity() + "\n";
		data += super.getState() + "\n";
		data += super.getZip() + "\n"; 
		

		return data;
	}

	/**
	 * writeCustomerInfo() - takes nothing, formats the data such that 
	 * it can be written to the console to be read easily
	 * 
	 * @return - string containing formatted data of a customer
	 */
	public String writeCustomerInfo() {
		String data = "";
		data += "First Name: " + super.getFirstName() + "\n"; 
		data += "Last Name: " + super.getLastName() + "\n";
		data += "Login: " + super.getLogin() + "\n";
		data += "Address: " + super.getAddress() + "\n";
		data += "City: " + super.getCity() + "\n";
		data += "State: " + super.getState() + "\n";
		data += "Zip: " + super.getZip() + "\n\n"; 
		data += this.toString();

		return data;
	}

	/**
	 * toString
	 * extracts all attributes of a Customer and stores them in a string
	 * 
	 * @return - a string containing all of the data
	 */
	@Override
	public String toString() {
		String data = "";
		
		data += "Orders of Customer : " + super.getLogin() ;

		if(orders.getLength() > 0) {
			data += this.orders.toString();
		}else {
			data += "\nThis customer has not made any orders yet";
		}
		return data;
	}

	/***
	 * hashCode - creates a hashcode by adding the login and password of the customer and sum each
	 * character's ascii values
	 *
	 *  @param - none
	 *  returns the sum of the login and password's ascii values
	 */
	@Override
	public int hashCode() {
		String key = super.getLogin() + super.getPassword();
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}

}

/**
 * ID Comparator - compare
 * compares a Customer based on their customer ID's returns an integer of their comparison
 */
class IDComparator implements Comparator<Customer> {
	/**
	 * Compares the two customers by taking the comparison of their customer ID's
	 * 
	 * @param customer1 the first customer
	 * @param customer2 the second customer
	 */
	@Override
	public int compare(Customer customer1, Customer customer2) {
	
		return customer1.getCustomerID().compareTo(customer2.getCustomerID());

	}

}
