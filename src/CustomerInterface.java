import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class CustomerInterface {
	public static void main(String[] args) throws IOException {
		// Defining number of buckets for the hashtable
		final int NUM_EMPLOYEE = 100;
		final int NUM_CUSTOMERS = 100;

		// BST's to store Albums alphabetically by Artist name or album name
		BST<Music> musicByAlbum = new BST<>();
		BST<Music> musicByArtist = new BST<>();

		ArrayList<Music> musicForFileWrite = new ArrayList<>();

		// Comparators for Music BST's
		Comparator<Music> albumTitleComparator = new AlbumTitleComparator();
		Comparator<Music> artistComparator = new ArtistComparator();

		// Comparators for Customer ID
		Comparator<Customer> idComparator = new IDComparator();

		// Priority Queue to store all active orders
		Heap<Order> activeOrders = new Heap<Order>(new ArrayList<Order>(), new PriorityComparator());

		// Hashtable for Customers and Employees
		HashTable<Customer> customerOrderHashTable = new HashTable<>(NUM_CUSTOMERS);
		HashTable<Employee> employ = new HashTable<>(NUM_EMPLOYEE);

		// BST To print Customer Data by their ID
		BST<Customer> customerDataBaseBST = new BST<>();

		// Field for user input
		String choice = "";

		// Fields for Customer and Music
		String first, last, ID, PW, stName, city, state, zip, albumTitle, artist, releaseDate;
		// Initialize mediaFormat to an empty string, this is for validation purposes
		String mediaFormat = "";
		int ordersMade = 0;

		int numAlbums;
		double price;

		// Fields for Order
		String orderDate, shippingOption, shippingStatus, deliveryAddress;
		int numberSongs, numberUnitsBought;

		// consant strings to keep the file strings
		final String ORDERS_CUSTOMERS_FILE = "Orders.txt";
		final String ALBUM_FILE = "Albums.txt";
		final String EMPLOYEE_FILE = "Employee.txt";
		final String CUSTOMER_FILE = "Customer.txt";

		// Files for outputting data
		File outputMusicFile = new File(ALBUM_FILE);
		File outputOrderFile = new File(ORDERS_CUSTOMERS_FILE);
		File outputCustomerFile = new File(CUSTOMER_FILE);

		// Files for reading in initial data
		File employeeFile = new File(EMPLOYEE_FILE);
		File albumFile = new File(ALBUM_FILE);
		File customersAndOrdersFile = new File(ORDERS_CUSTOMERS_FILE);

		Scanner input = new Scanner(customersAndOrdersFile);

		/****** READING IN ORDERS ******/
		while (input.hasNextLine()) {
			first = input.nextLine();
			last = input.nextLine();
			ID = input.nextLine();
			PW = input.nextLine();
			stName = input.nextLine();
			city = input.nextLine();
			state = input.nextLine();
			zip = input.nextLine();

			// Creating the customer
			Customer customerToDataBase = new Customer(first, last, ID, PW, stName, city, state, zip);


			// Some customers might make multiple orders, when they do, we have to make sure
			// they do not
			// get added to our hashtable twice
			if (!customerOrderHashTable.contains(customerToDataBase)) {
				customerOrderHashTable.insert(customerToDataBase);
				customerDataBaseBST.insert(customerToDataBase, idComparator);
			}

			/****** READING IN ORDER DATA ******/
			orderDate = input.nextLine();
			shippingOption = input.nextLine();
			shippingStatus = input.nextLine();
			deliveryAddress = input.nextLine();

			// This will hold the albums that belong to the Order
			List<Music> customerOrder = new List<>();
			numberUnitsBought = Integer.parseInt(input.nextLine());

			// this will keep track of the total cost of the package plus shipping
			double totalCost = 0;

			// Reading in each album in the order
			for (int i = 0; i < numberUnitsBought; i++) {
				albumTitle = input.nextLine();
				artist = input.nextLine();
				numberSongs = Integer.parseInt(input.nextLine());
				releaseDate = input.nextLine();
				mediaFormat = input.nextLine();
				price = Double.parseDouble(input.nextLine());
				numAlbums = Integer.parseInt(input.nextLine());
				totalCost += (price * numAlbums);

				// Creatimg the album and adding it to the order
				Music musicOrder = new Music(albumTitle, artist, mediaFormat.toUpperCase(), releaseDate, numberSongs,
						price, numAlbums);

				customerOrder.addLast(musicOrder);

			}

			// The orders are separated by a line, so we read over that line to get to the
			// next Order
			if (input.hasNextLine()) {
				input.nextLine();
			}

			// Creating our new Order
			Order newOrderToDatabase = new Order(orderDate, shippingOption, customerOrder, totalCost, deliveryAddress,
					shippingStatus, ID);

			// Adding the order to the correct customer using the hashtable
			customerOrderHashTable.get(customerToDataBase).addOrder(newOrderToDatabase);

			// If the order has not been shipped yet, denoted by "AWAITING SHIPMENT", we add
			// it to our priority queue
			if (shippingStatus.equals("AWAITING SHIPMENT")) {
				activeOrders.insert(newOrderToDatabase);
			}

		} // end of reading in customer and order data

		/****** READING IN EMPLOYEES ******/
		input.close();

		input = new Scanner(employeeFile);

		// Read in fields similar to customers
		while (input.hasNextLine()) {
			first = input.nextLine();
			last = input.nextLine();
			ID = input.nextLine();
			PW = input.nextLine();
			stName = input.nextLine();
			city = input.nextLine();
			state = input.nextLine();
			zip = input.nextLine();

			// Employees are separated by a line space, we read over it
			if (input.hasNextLine()) {
				input.nextLine();
			}

			// Creating our employee object and inserting it into the employee hashtable
			Employee employee = new Employee(first, last, ID, PW, stName, city, state, zip);

			employ.insert(employee);

		} // end reading in Employees

		input.close();

		/****** READING IN ALBUMS ******/

		// point our input to our album text file
		input = new Scanner(albumFile);

		// While loop to read in the album fields
		while (input.hasNextLine()) {
			albumTitle = input.nextLine();
			artist = input.nextLine();
			numberSongs = Integer.parseInt(input.nextLine());
			releaseDate = input.nextLine();
			mediaFormat = input.nextLine().toUpperCase();
			price = Double.parseDouble(input.nextLine());
			numAlbums = Integer.parseInt(input.nextLine());

			// Skipping over line separating the albums
			if (input.hasNextLine()) {
				input.nextLine();
			}

			// Creating our Albums as two separate objects so that a change to one will not
			// influence the other
			Music musicToAlbumBST = new Music(albumTitle, artist, mediaFormat, releaseDate, numberSongs, price,
					numAlbums);

			Music musicToArtistBST = new Music(albumTitle, artist, mediaFormat, releaseDate, numberSongs, price,
					numAlbums);

			// Inserting the objects into their respective BST
			musicByAlbum.insert(musicToAlbumBST, albumTitleComparator);
			musicByArtist.insert(musicToArtistBST, artistComparator);
			musicForFileWrite.add(new Music(musicToAlbumBST));
		}

		input.close();
		// end reading in albums

		/****** START OF MAIN PROGRAM - READING IN LOGIN DATA ******/
		input = new Scanner(System.in);
		boolean systemOn = true;

		System.out.println("Welcome to De Anza Music Store!");
		System.out.print("\nPlease enter your Login ID: ");
		ID = input.nextLine();
		System.out.print("Please enter your password: ");
		PW = input.nextLine();
		System.out.println("");

		// Two different objects to check whether or not the login credentials are in
		// the employee hashtable or customer bst

		Customer customerLoginInformation = new Customer(ID, PW);
		Employee employeeLoginInformation = new Employee(ID, PW);

		// boolean to check whether or not a customer needs to be added to the orders
		// hashtable
		boolean newCustomer = false;
		// boolean to print a welcome message on their first login
		boolean printWelcomeMessage = true;

		while (systemOn) {

			// Create a new account if the login and password is not recognized

			if (customerDataBaseBST.search(customerLoginInformation, idComparator) == null
					&& !employ.contains(employeeLoginInformation)) {

				System.out.println("\nWe don't have your account on file...\n");
				System.out.println("Let's create an account for you!");
				System.out.print("Enter your first name: ");
				first = input.nextLine();
				System.out.print("Enter your last name: ");
				last = input.nextLine();
				System.out.print("Enter your address: ");
				stName = input.nextLine();
				System.out.print("Enter your city: ");
				city = input.nextLine();
				System.out.print("Enter your state: ");
				state = input.nextLine();
				System.out.print("Enter your zip code: ");
				zip = input.nextLine();

				customerLoginInformation = new Customer(first, last, ID, PW, stName, city, state, zip);

				// boolean to tell program to add this customer to the orders hashtable
				newCustomer = true;

				ordersMade = 0;

				// Store the new customer in our BST
				customerDataBaseBST.insert(customerLoginInformation, idComparator);
			} // End of new customer creation

			/* ********************* START OF EMPLOYEE CODE *********************/

			else if (employ.contains(employeeLoginInformation)) {

				if (printWelcomeMessage) {
					System.out.println("\nWelcome Employee " + employ.get(employeeLoginInformation).getFirstName() + " "
							+ employ.get(employeeLoginInformation).getLastName());
					printWelcomeMessage = false;
				}

				// print to the console the employee options, all methods are written at the
				// bottom
				printEmployeeMenu();

				input = new Scanner(System.in);

				System.out.print("\nEnter your choice: ");
				choice = input.nextLine();

				// Start of Option A - Searching for a customer using their ID
				if (choice.equalsIgnoreCase("A")) {
					System.out.print("Please enter the ID of the customer: ");
					choice = input.nextLine();

					// Search for the customer using the BST
					if (customerDataBaseBST.search(new Customer(choice), idComparator) != null) {
						// Print customer information and their orders to the console
						System.out.println("\nHere is the customer and their outstanding Orders: ");
						System.out.println(
								customerDataBaseBST.search(new Customer(choice), idComparator).writeCustomerInfo());
					} else {
						System.out.println("That user is not in the database... Returning to Main Menu\n");
					}
					// End of Option A - searching for customer

					// Option B - Printing the customers with their outstanding orders to the
					// console
				} else if (choice.equalsIgnoreCase("B")) {
					customerDataBaseBST.inOrderPrint();

					// Option C - Print all orders out to the console by priority
					// Method code at the bottom
				} else if (choice.equalsIgnoreCase("C")) {

					printOrdersByPriority(activeOrders);
					// Option D - Ship an Order
				} else if (choice.equalsIgnoreCase("D")) {

					// if the priority queue is empty, print a message to the console
					if (activeOrders.getHeapSize() == 0) {
						System.out.println("There are no current active orders. Returning to main menu.....\n");
					} else {
						// Get highest priority order in the heap
						System.out.println("\nGrabbing Order With Highest Shipping Priority....");
						System.out.print("Here is the order information: \n");
						System.out.println(activeOrders.getMax());

						// Keep a shallow copy of the highest priority order
						Order chosenOrder = activeOrders.getMax();

						// Keep track of the accompanying customer with the highest priority order
						Customer shippedOrderCustomer = customerDataBaseBST
								.search(new Customer(chosenOrder.getCustomerID()), idComparator);

						// Prompting the employee if they want to ship the order or cancel the order
						System.out.println("\nWhat would you like to do?: ");
						System.out.println("1. Ship the Order");
						System.out.println("2. Cancel the Order");

						System.out.print("Enter your choice: ");

						choice = input.nextLine();

						// Option 1 - Shipping the order
						if (choice.equals("1")) {
							// calls updateStock, passing the album BSTs and the order
							// This decrements the number of albums of each song that was ordered
							updateStock(chosenOrder, musicByAlbum, musicByArtist, musicForFileWrite);

							// Update the order's status in the customer hashtable
							System.out.println("Updating customer's Order Status");

							// calling method within Customer.java that changes the shipping status to
							// SHIPPED
							shippedOrderCustomer.updateShippingOrder(chosenOrder);

							

							// Pop the order off the active order priority queue
							activeOrders.remove(1);

							System.out.println("Order Shipped Successfully! Returning to Main Menu....");

							// End of Option - 1
						}

						// Option 2 - Cancelling the order
						else if (choice.equals("2")) {

							// Prompt the employee to enter a cancellation message, could enter a reason why
							// the package
							// was cancelled
							System.out.print("Enter the Cancellation Message: ");
							choice = input.nextLine();

							// method within customer to update the status with the cancellation message of
							// the order
							shippedOrderCustomer.updateShippingOrderStatus(chosenOrder, choice);
							System.out.println("Successfully cancelled the order! Returning to main menu.... ");
						} else {
							System.out.println("Invalid option! Returning to main menu...\n");
						}
					} // End of Option - D

					// Start of Option E - Displaying the products by album name or artist name
				} else if (choice.equalsIgnoreCase("E")) {

					// Prompt employee in what manner do they want the products to be displayed
					System.out.println("In what manner do you want the products to be displayed?:");
					System.out.println("1. By Artist Name");
					System.out.println("2. By Album Name");
					System.out.print("Enter your choice: ");
					choice = input.nextLine();

					// Call the desired BST's inOrderPrint
					if (choice.equals("1")) {
						musicByArtist.inOrderPrint();
					} else if (choice.equals("2")) {
						musicByAlbum.inOrderPrint();
					} else {
						System.out.println("Invalid choice! Returning to Main Menu.... \n");
					}
					// End of Option E

					// Start of Option F - Adding a new album to the BST database
				} else if (choice.equalsIgnoreCase("F")) {

					// Prompting user for album fields
					System.out.println("\nPlease Enter Album's information!\n");
					System.out.print("Enter the Album Title: ");
					albumTitle = input.nextLine();
					System.out.print("\nEnter Artist of the Album: ");
					artist = input.nextLine();
					System.out.print("\nEnter number of songs in album: ");
					numberSongs = Integer.parseInt(input.nextLine());
					System.out.print(
							"\nEnter released date of album in format of (month day, year) eg. February 15, 2020: ");
					releaseDate = input.nextLine();

					// Validating whether or not the media foramt entered is correct
					boolean acceptedMediaFormat = false;

					while (!acceptedMediaFormat) {
						System.out.print("\nWhat is the format of the (CD / LP): ");
						mediaFormat = input.nextLine().toUpperCase();
						// If the mediaFormat is valid, exit the while loop
						if ((mediaFormat.equals("CD")) || (mediaFormat.equals("LP"))) {
							acceptedMediaFormat = true;
						} else {
							System.out.print(
									"\nSorry. But we do not accept that format of the album. Please enter a valid format (CD/LP): ");
							mediaFormat = input.nextLine();
						}
					}

					System.out.print("Enter the Price of the Album: ");
					price = Double.parseDouble(input.nextLine());
					System.out.print("Enter How Many Units to Add: ");
					numAlbums = Integer.parseInt(input.nextLine());
					Music temp = new Music(albumTitle, artist, mediaFormat, releaseDate, numberSongs, price, numAlbums);

					// If we already have that album, we update the number of albums we have in
					// stock
					if (musicByAlbum.search(temp, albumTitleComparator) != null && (musicByAlbum
							.search(temp, albumTitleComparator).getMediaFormat().equalsIgnoreCase(mediaFormat))) {

						System.out.println("We already have that album in stock! Updating... ");
						musicByAlbum.search(temp, albumTitleComparator).updateNumAlbums(numAlbums);
						musicByArtist.search(temp, artistComparator).updateNumAlbums(numAlbums);

						//update our arraylist to write to file
				
						for(int i = 0; i < musicForFileWrite.size(); i++){
							if(musicForFileWrite.get(i).getAlbumTitle().equalsIgnoreCase(musicByAlbum.search(temp, albumTitleComparator).getAlbumTitle())&&
								musicForFileWrite.get(i).getMediaFormat().equalsIgnoreCase(musicByAlbum.search(temp, albumTitleComparator).getMediaFormat())) {
									musicForFileWrite.get(i).updateNumAlbums(numAlbums);
								}
						}

						System.out.println("Stock updated! Returning to main menu.... \n");

					} else {
						// If that album is not in our database, we insert it as a completely new node
						musicByAlbum.insert(temp, albumTitleComparator);
						musicByArtist.insert(temp, artistComparator);
						musicForFileWrite.add(new Music(temp));
						System.out.println("\nFollowing album has been added! Returning to main menu.... \n");
					}
					// end of option F

					// Start of option G - removing an album
				} else if (choice.equalsIgnoreCase("G")) {
					System.out.print("Enter the Title of the Album to remove: ");
					albumTitle = input.nextLine();
					System.out.print("\nEnter the Artist of the Album to remove: ");
					artist = input.nextLine();
					System.out.print("\nEnter the Media Format of the album to remove (CD/LP): ");
					mediaFormat = input.nextLine();

					// The process for removing an album is complex
					// because we have two different BSTs, and two different
					// Media formats to account for

					Music temp = new Music(albumTitle, artist);
					temp.setMediaFormat(mediaFormat);

					// Call method to remove the albums in each BST, code written under 'MUTATOR
					// FUNCTIONS' section
					removeAlbum(temp, musicByAlbum, musicByArtist, musicForFileWrite);

					
					

					// End of option G

					// Start of Option X - Terminate the program, and write updates to file
				} else if (choice.equalsIgnoreCase("X")) {
					// end condition to exit while loop
					systemOn = false;

					// Create printwriters for each file to write to
					PrintWriter musicPrintWriter = new PrintWriter(outputMusicFile);
					PrintWriter ordersPrintWriter = new PrintWriter(outputOrderFile);
					PrintWriter customerPrintWriter = new PrintWriter(outputCustomerFile);

					// Call method to write the changes to the files,
					// method code is written at the bottom under "WRITING TO FILE" section
					writeChangesToFile(customerDataBaseBST, customerOrderHashTable, musicForFileWrite, musicPrintWriter,
							ordersPrintWriter, customerPrintWriter);

				} else {
					// Print error message when choice is not valid
					System.out.println("\nInvalid menu option. Please enter A-G or H to exit.\n");
				}

				/********************* CUSTOMER CODE ******************/

			} else {
				// Print a welcome message if it's the first time they're logging in during this
				// instance of the program
				customerLoginInformation = customerDataBaseBST.search(customerLoginInformation, idComparator);

				if (printWelcomeMessage) {
					System.out.println("Welcome Customer "
							+ customerDataBaseBST.search(customerLoginInformation, idComparator).getFirstName() + " "
							+ customerDataBaseBST.search(customerLoginInformation, idComparator).getLastName());
					printWelcomeMessage = false;
				}

				// Print the customer's choices to the console
				printCustomerMenu();

				input = new Scanner(System.in);

				System.out.print("Enter your choice: ");

				String choice2 = input.nextLine();

				// Start Option A - Search for albums
				if (choice2.equalsIgnoreCase("A")) {
					System.out.println("select from the following options: ");
					System.out.println("1. Find and display one record using the Album Title: ");
					System.out.println("2. Find and display one record using the Album Artist: ");

					System.out.print("Enter your choice: ");
					choice = input.nextLine();

					if (choice.equals("1")) {

						System.out.print("Enter title of the album: ");
						albumTitle = input.nextLine();

						// Validating whether or not the media format entered is correct

						boolean acceptedMediaFormat = false;

						while (!acceptedMediaFormat) {
							System.out.print("\nEnter the Media Format of the album (LP/CD): ");
							mediaFormat = input.nextLine().toUpperCase();

							// If the mediaFormat is valid, exit the while loop
							if ((mediaFormat.equals("CD")) || (mediaFormat.equals("LP"))) {
								acceptedMediaFormat = true;
							} else {
								System.out.print(
										"\nSorry. But we do not accept that format of the album. Please enter a valid format (CD/LP): ");
								mediaFormat = input.nextLine();
							}

						}

						Music temp = new Music(albumTitle, "");
						temp.setMediaFormat(mediaFormat);

						if (findAlbumByTitleName(temp, musicByAlbum) != null) {
							System.out.println("Here is the album's information: \n");
							System.out.println(findAlbumByTitleName(temp, musicByAlbum));
						} else {
							System.out.println("Sorry we don't have following title of the album");
						}

					} else if (choice.equals("2")) {
						System.out.print("Enter Artist of the album: ");
						artist = input.nextLine();

						boolean acceptedMediaFormat = false;

						while (!acceptedMediaFormat) {
							System.out.print("\nEnter the Media Format of the album (LP/CD): ");
							mediaFormat = input.nextLine().toUpperCase();

							// Validating whether or not the media format entered is correct
							// If the mediaFormat is valid, exit the while loop
							if ((mediaFormat.equals("CD")) || (mediaFormat.equals("LP"))) {
								acceptedMediaFormat = true;
							} else {
								System.out.print(
										"\nSorry. But we do not accept that format of the album. Please enter a valid format (CD/LP): ");
								mediaFormat = input.nextLine();
							}

						}

						Music temp = new Music("", artist);
						temp.setMediaFormat(mediaFormat);

						if (findAlbumByArtistName(temp, musicByArtist) != null) {
							System.out.println("Here is the album's information: \n");
							System.out.println(findAlbumByArtistName(temp, musicByArtist));
						} else {
							System.out.println("Sorry we don't have following artist's album");

						}

					} else {
						System.out.println("\nInvalid option.");
					}
				} else if (choice2.equalsIgnoreCase("B")) {
					System.out.println("Select from the following options: ");
					System.out.println("1. Find and display one record using the Album Title: ");
					System.out.println("2. Find and display one record using the Artist Name: ");

					System.out.print("Enter your choice: ");

					choice = input.nextLine();

					if (choice.equals("1")) {
						musicByAlbum.inOrderPrint();
					} else if (choice.equals("2")) {
						musicByArtist.inOrderPrint();
					} else {
						System.out.println("Invalid choice");
					}
				} else if (choice2.equalsIgnoreCase("C")) {

					Order shoppingCart = new Order();

					shoppingCart.setCustomerID(customerLoginInformation.getLogin());

					boolean purchaseOn = true;
					boolean albumFound = false;

					double totalCost = 0;

					while (purchaseOn) {
						System.out.print("Enter the title of the Album: ");
						albumTitle = input.nextLine();
						System.out.print("Enter the Media Format (LP/CD): ");
						mediaFormat = input.nextLine().toUpperCase();

						Music musicPurchase = new Music();
						musicPurchase.setAlbumTitle(albumTitle);
						musicPurchase.setMediaFormat(mediaFormat);

						albumFound = false;
						Music purchasedAlbum = new Music();

						if (findAlbumByTitleName(musicPurchase, musicByAlbum) != null) {
							albumFound = true;
							purchasedAlbum = new Music(findAlbumByTitleName(musicPurchase, musicByAlbum));

						} else {
							System.out.print(
									"Sorry, we do not have that album in stock currently. Would you still like to make a purchase?(Y/N): ");
							choice = input.nextLine();
							albumFound = false;
							if (!choice.equalsIgnoreCase("y")) {
								purchaseOn = false;
							}

						}

						if (purchaseOn && albumFound) {
							System.out.print("How many albums would you like to purchase?: ");
							numAlbums = Integer.parseInt(input.nextLine());
							purchasedAlbum.setNumAlbums(numAlbums);

							shoppingCart.addSong(purchasedAlbum);

							totalCost += purchasedAlbum.getAlbumPrice() * numAlbums;

							//findAlbumByTitleName(purchasedAlbum, musicByAlbum).updateNumAlbums(numAlbums * -1);
							//findAlbumByArtistName(purchasedAlbum, musicByArtist).updateNumAlbums(numAlbums * -1);

							System.out.print("Do you want to make another purchase? (Y/N): ");
							choice = input.nextLine();
							if (choice.equalsIgnoreCase("n")) {
								purchaseOn = false;
								boolean checkOutCompleted = true;
							}
						}

					}

					if (newCustomer && ordersMade == 0) {
						customerOrderHashTable.insert(customerLoginInformation);
						ordersMade++;
					}

					if (albumFound) {
						System.out.println("\nWhere do you want to ship the package?: ");
						System.out.println("1. To Your Default Address: ");
						System.out.println("2. New Address");
						System.out.print("\nEnter your choice: ");
						choice = input.nextLine();

						if (choice.equals("1")) {
							shoppingCart.setDeliveryDestination(
									customerOrderHashTable.get(customerLoginInformation).getAddress());
						} else if (choice.equals("2")) {
							System.out.print("Enter the street address where you would like to ship this package : ");
							String streetName = input.nextLine();
							System.out.print("Enter the city name: ");
							String cityName = input.nextLine();
							System.out.print("Enter the state: ");
							String stateName = input.nextLine();
							System.out.print("Enter the zip code: ");
							int zipCode = Integer.parseInt(input.nextLine());

							deliveryAddress = streetName + ", " + cityName + ", " + stateName + " " + zipCode;
							shoppingCart.setDeliveryDestination(deliveryAddress);

						}

						shoppingCart.setDateCreated("06-21-21");
						shoppingCart.updateOrderStatus("AWAITING SHIPMENT");
						shoppingCart.setCustomerID(customerLoginInformation.getLogin());

						System.out.println("\nPlease Select Your Shipping Method");
						System.out.println("1. Same-Day Shipping: $23.95");
						System.out.println("2. Express One-Day Shipping: $18.95");
						System.out.println("3. Two-Day Shipping: $11.95");
						System.out.println("4. Standard Shipping: $6.95");
						System.out.println("5. No-Rush Shipping: $1.95");

						System.out.print("\nEnter your choice: ");
						choice = input.nextLine();

						if (choice.equals("1")) {
							shoppingCart.setShippingExpense("Same-Day Shipping");
							shoppingCart.setShippingMethod("Same-Day Shipping");
						} else if (choice.equals("2")) {
							shoppingCart.setShippingExpense("Express One-Day Shipping");
							shoppingCart.setShippingMethod("Express One-Day Shipping");
						} else if (choice.equals("3")) {
							shoppingCart.setShippingExpense("Two-Day Shipping");
							shoppingCart.setShippingMethod("Two-Day Shipping");
						} else if (choice.equals("4")) {
							shoppingCart.setShippingExpense("Standard Shipping");
							shoppingCart.setShippingMethod("Standard Shipping");
						} else if (choice.equals("5")) {
							shoppingCart.setShippingExpense("No-Rush Shipping");
							shoppingCart.setShippingMethod("No-Rush Shipping");
						} else {
							System.out.println("Error: Not a valid shipping Option!");
						}

						

						Order addOrderToDatabase = shoppingCart;
						addOrderToDatabase.setOrderID();
						addOrderToDatabase.setTotalCost(totalCost);

						customerOrderHashTable.get(customerLoginInformation).addOrder(addOrderToDatabase);

						System.out
								.println("Thank you for shopping with us! Your order information is listed below!: \n");
						System.out.println("/********* SHOPPING CART ************/");

						System.out.println(addOrderToDatabase);

						activeOrders.insert(addOrderToDatabase);
					}

				} else if (choice2.equalsIgnoreCase("D")) {

					if (newCustomer && ordersMade == 0) {
						System.out.println("You currently have no outstanding orders.Returning to main menu....\n");
					} else if (customerOrderHashTable.get(customerLoginInformation).getOrders().getFirst() == null) {
						System.out.println("You currently have no outstanding orders.Returning to main menu....\n");
					} else {
						customerOrderHashTable.get(customerLoginInformation).printOrders();
					}
				} else if (choice2.equalsIgnoreCase("X")) {
					systemOn = false;

					PrintWriter musicPrintWriter = new PrintWriter(outputMusicFile);
					PrintWriter ordersPrintWriter = new PrintWriter(outputOrderFile);
					PrintWriter customerPrintWriter = new PrintWriter(outputCustomerFile);

					writeChangesToFile(customerDataBaseBST, customerOrderHashTable, musicForFileWrite, musicPrintWriter,
							ordersPrintWriter, customerPrintWriter);
				} else {
					System.out.println("\nInvalid menu option. Please enter A-G or H to exit.\n");
				}
			}

		}
		System.out.println("\nThank you for shopping with us!\n");

	}

	/*************************** PROGRAM FUNCTIONS *********************/

	/*********** PRINTING TO CONSOLE OUTPUT FUNCTIONS **********/

	/**
	 * Function to output menu options to the employee returns nothing Takes nothing
	 * Returns nothing
	 */
	public static void printEmployeeMenu() {
		System.out.println("Select from the following options: \n");
		System.out.println("------------------------------------\n");
		System.out.println("A: Search for a Customer by Customer ID\n");
		System.out.println("B: Display Customer Orders by Customer ID\n");
		System.out.println("C: View Orders by Priority\n");
		System.out.println("D: Ship an Order\n");
		System.out.println("E: List Database of Products\n");
		System.out.println("F: Add a new product\n");
		System.out.println("G: Remove a product\n");
		System.out.println("X: Quit\n");
		System.out.println("------------------------------------\n");
	}

	/**
	 * Function to print output menu options to the customer takes nothing returns
	 * nothing
	 */
	public static void printCustomerMenu() {
		System.out.println("Select from the following options: \n");
		System.out.println("------------------------------------\n");
		System.out.println("A: Search for a product\n");
		System.out.println("B: Display products\n");
		System.out.println("C: Place an order\n");
		System.out.println("D: View Purchases\n");
		System.out.println("X: Exit\n");
		System.out.println("------------------------------------\n");
	}

	/***
	 * Prints a heap by absolutely priority by popping off each highest priority
	 * element and writing to screen Does this by creating a deep copy of the heap
	 * passed in the parameter
	 * 
	 * @param orderDatabase - Heap to print out by Priority returns nothing
	 */
	public static void printOrdersByPriority(Heap<Order> orderDatabase) {
		// ArrayList to hold the copied values
		ArrayList<Order> printDataBase = new ArrayList<>();

		// Copying over the arraylist from the database passed through parameter
		for (int i = 0; i < orderDatabase.getHeapSize(); i++) {
			printDataBase.add(orderDatabase.getElement(i + 1));

		}

		// Create our deep copy of the heap
		Heap<Order> heapToOutput = new Heap<>(printDataBase, new PriorityComparator());

		// Pop off the highest priority element and print to screen
		for (int i = 0; i < orderDatabase.getHeapSize(); i++) {
			System.out.println("\nOrder Number " + (i + 1) + ". ");
			System.out.println(heapToOutput.getMax()); // Print contents of order to screen
			if (heapToOutput.getHeapSize() > 1) {
				heapToOutput.remove(1); // Pop off highest priority element
			}
		}
		System.out.println();
	}

	/******** DATA MUTATOR FUNCTIONS **********/

	/**
	 * updateStock - makes changes to the album BSTs to reflect stock
	 * changes/resupply when an order is made. Prints error messages to the console
	 * if there is a problem
	 * 
	 * @param customerOrder - Order that contains albums that we need to supply from
	 *                      our BSTs
	 * @param musicByAlbum  - BST sorted by Albums
	 * @param musicByArtist - BST sorted by Artists
	 */

	public static void updateStock(Order customerOrder, BST<Music> musicByAlbum, BST<Music> musicByArtist, ArrayList<Music> musicForFileWrite) {
		
		List<Music> musicOrdered = customerOrder.getMusic();
		Comparator<Music> albumTitleComparator = new AlbumTitleComparator();
		Comparator<Music> artistComparator = new ArtistComparator();


		if (musicOrdered.getLength() == 0) {
			System.out.println("Error: The order is empty!");
		} else {
			musicOrdered.placeIterator();

			while (!musicOrdered.offEnd()) {
				int numberUnitsBought = musicOrdered.getIterator().getNumAlbums();

				if (findAlbumByTitleName(musicOrdered.getIterator(), musicByAlbum) != null) {
					if (numberUnitsBought > findAlbumByTitleName(musicOrdered.getIterator(), musicByAlbum).getNumAlbums()) {
						System.out.println("Error: Not Sufficient Supply of Item.");
						System.out.println("Item in which re-supply is required before fulfilling order is listed below: \n");
						System.out.println(musicByAlbum.search(musicOrdered.getIterator(), albumTitleComparator));
					} else {
						try {
							findAlbumByArtistName(musicOrdered.getIterator(), musicByArtist).updateNumAlbums(numberUnitsBought * -1);
							findAlbumByTitleName(musicOrdered.getIterator(), musicByAlbum).updateNumAlbums(numberUnitsBought * -1);
							
							for(int i = 0; i < musicForFileWrite.size(); i++){
								// System.out.println("\ncomparing: \n");
								// System.out.println(musicForFileWrite.get(i));
								// System.out.println("\nto\n");
								// System.out.println(musicOrdered);
								if(musicForFileWrite.get(i).getAlbumTitle().equalsIgnoreCase((musicOrdered.getIterator().getAlbumTitle()))&&
									musicForFileWrite.get(i).getMediaFormat().equalsIgnoreCase(musicOrdered.getIterator().getMediaFormat())) {
										System.out.println("updated");
										System.out.println("USED TO BE ***");
										
										Music musicUpdate = musicForFileWrite.get(i);
										System.out.println(musicUpdate);
										musicUpdate.updateNumAlbums(numberUnitsBought * -1);
										musicForFileWrite.set(i, musicUpdate);
										System.out.println("NOW IS ******");
										System.out.println(musicForFileWrite.get(i));

									}
							}
						} catch (NullPointerException e) {
							System.out.println("Could not find the album while updating the stock!");
						}


					}
				}
				

				musicOrdered.advanceIterator();
			}
		}
		System.out.println("Items have been successfully gathered and the order is ready to ship! ");
		
	}

	/**
	 * removeAlbum - remove an album from each BST
	 * 
	 * @param albumToBeRemoved - album we want to remove
	 * @param albumsByTitle    - BST sorted by Title
	 * @param albumsByArtist   - BST sorted by Artist returns nothing
	 */

	public static void removeAlbum(Music albumToBeRemoved, BST<Music> musicByAlbum, BST<Music> musicByArtist, ArrayList<Music> musicForFileWrite) {

		for(int i = 0; i < musicForFileWrite.size(); i++){
			if(musicForFileWrite.get(i).getAlbumTitle().equalsIgnoreCase((albumToBeRemoved.getAlbumTitle()))&&
				musicForFileWrite.get(i).getMediaFormat().equalsIgnoreCase(albumToBeRemoved.getMediaFormat())) {
					musicForFileWrite.remove(i);
				}
		}

		// Comparators for each BST
		Comparator<Music> albumTitleComparator = new AlbumTitleComparator();
		Comparator<Music> artistComparator = new ArtistComparator();

		// Keep track of the media Format
		String mediaFormat = albumToBeRemoved.getMediaFormat();

		// Set a shallow copy of the album to an object
		Music searchAlbum = musicByAlbum.search(albumToBeRemoved, albumTitleComparator);

		// Case 1 - Album is found with the same media format == true album was found
		if (searchAlbum != null && searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {

			musicByAlbum.remove(albumToBeRemoved, albumTitleComparator);

			// Case 2- Album is found, but the media formats are different == true album was
			// not found
			// But it is possible that the Album with the correct order format is still
			// present in the tree
		} else if (searchAlbum != null && !searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {

			// Keep a deep copy of the music to remove so we can add it back later
			Music musicRemoved = new Music(musicByAlbum.search(albumToBeRemoved, albumTitleComparator));

			// Start by removing the album with the different media Format
			musicByAlbum.remove(albumToBeRemoved, albumTitleComparator);

			// search again for the same album title, and if the media format is the same,
			// remove that one
			searchAlbum = musicByAlbum.search(albumToBeRemoved, albumTitleComparator);
			if (searchAlbum != null && searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {
				// Remove the true album
				musicByAlbum.remove(searchAlbum, albumTitleComparator);
				// Re-insert the removed album
				musicByAlbum.insert(musicRemoved, albumTitleComparator);
			} else {
				System.out.println("\nThe following album does not exist in our current stock.\n");
				return; // exit since the album was not found
			}

		} else {
			System.out.println("\nThe following album does not exist in our current stock.\n");
			return; // exit since the album was not found
		}

		searchAlbum = musicByArtist.search(albumToBeRemoved, artistComparator);

		/**** Now repeat the same process, but with the BST sorted by artist name ****/

		// Case 1 - Album is found with the same media format == true album was found
		if (searchAlbum != null && searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {

			musicByArtist.remove(albumToBeRemoved, artistComparator);

			// Case 2- Album is found, but the media formats are different == true album was
			// not found
			// But it is possible that the Album with the correct order format is still
			// present in the tree
		} else if (searchAlbum != null && !searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {

			// Keep a deep copy of the music to remove so we can add it back later
			Music musicRemoved = new Music(musicByArtist.search(albumToBeRemoved, artistComparator));

			// Start by removing the album with the different media Format
			musicByArtist.remove(albumToBeRemoved, artistComparator);

			// search again for the same album title, and if the media format is the same,
			// remove that one
			searchAlbum = musicByArtist.search(albumToBeRemoved, artistComparator);

			if (searchAlbum != null && searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {
				// Remove the true album
				musicByArtist.remove(searchAlbum, artistComparator);
				// Re-insert the removed album
				musicByArtist.insert(musicRemoved, artistComparator);

			} else {
				// Print Error message for the employee if the album was not found
				System.out.println("\nAlbum was found in Title but not in Artist BST\n");
			}

		} else {
			// Print Error message for the employee if the album was not found
			System.out.println("\nAlbum was found in Title but not in Artist BST\n");
		}

	}

	/************ DATA ACCESSFOR FUNCTIONS *************/

	public static Music findAlbumByArtistName(Music albumToBeSearched, BST<Music> musicByArtist) {
		// Comparator for BST
		Comparator<Music> artistComparator = new ArtistComparator();

		// Keep track of the media Format
		String mediaFormat = albumToBeSearched.getMediaFormat();

		// Set a shallow copy of the album to an object
		Music searchAlbum = musicByArtist.search(albumToBeSearched, artistComparator);

		// Case 1 - Album is found with the same media format == true album was found
		if (searchAlbum != null && searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {

			return searchAlbum;

			// Case 2- Album is found, but the media formats are different == true album was
			// not found
			// But it is possible that the Album with the correct order format is still
			// present in the tree
		} else if (searchAlbum != null && !searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {

			// Keep a deep copy of the music to remove so we can add it back later
			Music musicRemoved = new Music(musicByArtist.search(albumToBeSearched, artistComparator));

			// Start by removing the album with the different media Format
			musicByArtist.remove(albumToBeSearched, artistComparator);

			// search again for the same album title, and if the media format is the same,
			// remove that one
			searchAlbum = musicByArtist.search(albumToBeSearched, artistComparator);

			if (searchAlbum != null && searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {

				// Re-insert the removed album
				musicByArtist.insert(musicRemoved, artistComparator);

				// Remove the true album
				return searchAlbum;
			} else {
				// Print Error message for the employee if the album was not found
				System.out.println("\nThe following album does not exist in our current stock.\n");
				return null;
			}

		} else {
			// Print Error message for the employee if the album was not found
			System.out.println("\nThe following album does not exist in our current stock.\n");
			return null;
		}
	}

	public static Music findAlbumByTitleName(Music albumToBeSearched, BST<Music> musicByAlbum) {
		// Comparators for each BST
		Comparator<Music> albumTitleComparator = new AlbumTitleComparator();

		// Keep track of the media Format
		String mediaFormat = albumToBeSearched.getMediaFormat();

		// Set a shallow copy of the album to an object
		Music searchAlbum = musicByAlbum.search(albumToBeSearched, albumTitleComparator);

		if (searchAlbum != null && searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {
			return searchAlbum;

			// Case 2- Album is found, but the media formats are different == true album was
			// not found
			// But it is possible that the Album with the correct order format is still
			// present in the tree
		} else if (searchAlbum != null && !searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {

			// Keep a deep copy of the music to remove so we can add it back later
			Music musicRemoved = new Music(musicByAlbum.search(albumToBeSearched, albumTitleComparator));

			// Start by removing the album with the different media Format
			musicByAlbum.remove(albumToBeSearched, albumTitleComparator);

			// search again for the same album title, and if the media format is the same,
			// remove that one
			searchAlbum = musicByAlbum.search(albumToBeSearched, albumTitleComparator);
			if (searchAlbum != null && searchAlbum.getMediaFormat().equalsIgnoreCase(mediaFormat)) {
				// Remove the true album
				// Re-insert the removed album
				musicByAlbum.insert(musicRemoved, albumTitleComparator);
				return searchAlbum;
			} else {
				return null; // return if since the album was not found
			}

		} else {
			return null; // return if since the album was not found
		}

	}

	/************** WRITING TO THE FILE ***********/

	/***
	 * This method formats the data and writes new updates to different text files
	 * So that when a new instance of the program is ran, the updates will be
	 * accounted for
	 * 
	 * @param customerBST      - Hashtable of customers
	 * @param customerDataBase - BST of customers sorted by customer ID
	 * @param musicBST         - music BST sorted by albums
	 * @param musicPW          - Printwriter to write to Albums.txt
	 * @param orderPW          - Printwriter to write to Order.txt
	 * @param customerPW       - PrintWriter to write to Customer.txt
	 */
	public static void writeChangesToFile(BST<Customer> customerBST, HashTable<Customer> customerDataBase,
			ArrayList<Music> musicFileForWrite, PrintWriter musicPW, PrintWriter orderPW, PrintWriter customerPW) {


		for(int i = 0; i < musicFileForWrite.size();i++) {
			musicPW.write(musicFileForWrite.get(i).formatMusicDataForFileWrite() + "\n");
		}
		
		Comparator<Customer> IDComparator = new IDComparator();

		// Create a deep copy of the customer BST
		BST<Customer> outputCustomerBST = new BST<Customer>(customerBST, IDComparator);

		// While our deep copy is not empty,
		// remove the root and print it to the file
		while (!outputCustomerBST.isEmpty()) {
			Customer customerToFile = outputCustomerBST.getRoot();

			// If the customer does not have any orders, do not print their information to
			// the Order text file
			for (int i = 1; i <= customerDataBase.get(customerToFile).getOrders().getLength(); i++) {
				// calls the method in Customer.java to format the music to be able to be read
				// in after it is written to the file
				orderPW.write(customerToFile.formatCustomerDataForOrderFileWrite(i) + "\n");
			}

			// Add customer to customers text file
			customerPW.write(customerToFile.formatCustomerDataForCustomerFileWrite() + "\n");
			outputCustomerBST.remove(customerToFile, IDComparator);
		}

		// Close our print writers to save changes
		musicPW.close();
		orderPW.close();
		customerPW.close();
	}
}
