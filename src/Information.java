public abstract class Information {
	
	private String first;
	private String last;
	private String logIn;
	private String passWord;
	private String address;
	private String city;
	private String state;
	private String zip;
	

	/**
	 * Default constructor for information, calls the parent class default constructor
	 * assign default values to the private variables
	 */
	public Information() {
		first = "No First name";
		last = "No Last name";
		logIn = "no id";
		passWord = "no password";
		address = "Address unknown";
		city = "City unknown";
		state = "State unknown";
		zip = "Zip unknown";
	}
	
	/**
	 * Two argument constructor, takes a login and password, 
	 * calls the parent class' two-argument constructor and
	 * initializes a new account
	 * 
	 * @param login - login of customer
	 * @param password - password of customer
	 */
	public Information(String login, String password) {
		this.logIn = login;
		this.passWord = password;
	}
	/**
	 * Four argument constuctor, takes the fields listed below,
	 * calls parent class' 4-argument constructor
	 * initializes a new account
	 * 
	 * @param first - first name of customer
	 * @param last - last name of customer
	 * @param login - login of customer
	 * @param password - password of customer
	 */
	
	public Information(String first, String last, String logIn, String passWord) {
		this.first = first;
		this.last = last;
		this.logIn = logIn;
		this.passWord = passWord;
	}
	/**
	 * 8-argument constructor, this fills all the parameters except for the order history
	 * calls its parents' 8-argument constructor and initializes an account
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
	public Information(String firstName, String lastName, String login, String password, String address, String city,
			String state, String zip) {
		this.first = firstName;
		this.last = lastName;
		this.logIn = login;
		this.passWord = password;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	/**
	 * getFirstName - receives nothing
	 * 
	 * @return - the first name of the customer
	 */
	public String getFirstName() {
		return first;
	}
	
	/**
	 * getLastName - receives nothing
	 * 
	 * @return - the last name of the customer
	 */
	public String getLastName() {
		return last;
	}
	
	/**
	 * getLogin - receives nothing
	 * @return the login of the customer
	 */
	public String getLogin() {
		return logIn;
	}
	
	/**
	 * getPassword - receives nothing
	 * @return - the password of the customer
	 */
	public String getPassword() {
		return passWord;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String first) {
		this.first = first;
	}
	
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String last) {
		this.last = last;
	}
	
	/**
	 * updates the customer loginID
	 * @param login the login to set
	 */
	public void setLogin(String logIn) {
		this.logIn = logIn;
	}
	
	/**
	 * updates the customer's password
	 * @param password the password to set
	 */
	public void setPassword(String passWord) {
		this.passWord = passWord;
	}
	/**
	 * getAddress - receives nothing
	 * @return - the address of the customer
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * getCity - receives nothing
	 * @return - the city of the customer
	 */
	public String getCity() {
		return city;
	}
	/**
	 * getState - receives nothing
	 * @return - the state of the customer
	 */
	public String getState() {
		return state;
	}
	/**
	 * getZip - receives nothing
	 * @return - the zip code of the customer
	 */
	public String getZip() {
		return zip;
	}
	
	/**
	 * @param address the address to set
	 */
	public void updateAddress(String address) {
		this.address = address;
	}
	/**
	 * setAddress - updates the customer's address 
	 * @param address - the address used to update the customer's address 
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * setCity - updates the customer's city 
	 * @param city - the city used to update the customer's city 
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * setState - updates the customer's state 
	 * @param state - the state used to update the customer's state 
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * setZip - updates the customer's zip code 
	 * @param zip - the zip code used to update the customer's zip code 
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	




}
