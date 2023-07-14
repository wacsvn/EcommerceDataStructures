public class Employee extends Information {

	/***
	 * Default Constructor
	 * calls the default constructor of the parent class Information
	 */
	public Employee() {
		super();
	}

	/**
	 * Two-Argument Constructor
	 * Used for initially logging in
	 * 
	 * @param login - login of the employee
	 * @param password - password of the employee
	 */
	public Employee(String login, String password) {
		super(login, password);
	}

	/***
	 * Full parameter constructor
	 * 
	 * @param firstName - first name of employee
	 * @param lastName - last name of employee
	 * @param login - login of employee
	 * @param password - password of employee
	 * @param address - address of employee
	 * @param city - city of employee
	 * @param state - state of of employee
	 * @param zip - zip code of of employee
	 */

	public Employee(String firstName, String lastName, String login, String password, String address, String city,
	String state, String zip) {
		super(firstName, lastName, login, password, address, city, state ,zip);
	}

	/**
	 * Copy constructor, takes an employee object and copies its attributes
	 * by calling the 8-argument constructor and passing it the copied attributes
	 * 
	 * @param e - employee object to copy
	 */
	public Employee(Employee e) {
		this(e.getFirstName(), e.getLastName(), e.getLogin(), e.getPassword(), e.getAddress(),
		e.getCity(), e.getState(), e.getZip());
	}

	/**
	 * equals - Determines if two Employees are equal by comparing their login and password
	 * 
	 * @param - an object to compare with
	 * returns a boolean if they are equal, false if otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Employee)) {
			return false;
		} else {
			Employee o2 = (Employee) o;
			return o2.getLogin().equals(super.getLogin()) && o2.getPassword().equals(super.getPassword());
		}
	}

	/***
	 * hashCode - creates a hashcode by adding the login and password of the employee and sum each
	 * character's ascii values
	 *
	 *  @param - none
	 * returns the sum of the login and password's ascii values
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
