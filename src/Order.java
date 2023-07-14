import java.text.DecimalFormat;
import java.util.Comparator;

public class Order {
    private String dateCreated;
    private String shippingOption;
    private int priority;
    private double shippingExpenses;
    private double orderCost;
    private String shippingDestination;
    private String orderStatus;
    private String customerID;
    private int orderID;
    private static int seedOrderID = 10000000; 

    private final double TAX_RATE = .0875;

    //List to hold all of our music
    private List<Music> album_orders;

    /**
	 * Default constructor for customer, calls the parent class default constructor
	 */
    
    public Order() {
        dateCreated = "No Date";
        shippingOption = "No Shipping Option";
        priority = 0;
        album_orders = new List<Music>();
        shippingExpenses = 0;
        orderCost = 0;
        shippingDestination = "No Destination";
        orderStatus = "No Order Status";
        customerID = "No Customer ID";
        orderID = -1;
        album_orders = new List<Music>();
    }

    /**
	 * one argument constructor, takes a shipping option, 
	 * recursively calls the setPriority method to assign
	 * priority to the order.
	 * 
	 * @param shippingOption, decides the priority of the shipping
	 * 
	 */
    
    public Order(String shippingOption) {
        this.shippingOption = shippingOption;
        setPriority(shippingOption);
    }


    /**
	 * three argument constuctor, takes the fields listed below,
	 * calls parent class' 3-argument constructor
	 * initializes a list of orders
	 * 
	 * @param dateCreated - the date of an order created
	 * @param shippingOption - shipping option of order
	 * @param album_orders - List of albums we have available 
	 * at the store.  
	 *
	 */
    
    public Order(String dateCreated, String shippingOption, List<Music> album_orders) {
        //System.out.println("This one called");
        this.dateCreated = dateCreated;
        this.shippingOption = shippingOption;
        setPriority(shippingOption);
        this.album_orders = new List<Music>(album_orders);
    }
   
    /**
   	 * four argument constuctor, takes the fields listed below,
   	 * calls parent class' 3-argument constructor
   	 * initializes a list of orders
   	 * 
   	 * @param dateCreated - the date of an order created
   	 * @param shippingOption - shipping option of order
   	 * @param priority - priority based on shipping option
   	 * @param album_orders - List of albums we have available 
   	 * at the store.  
   	 *
   	 */

    public Order(String dateCreated, String shippingOption, int priority, List<Music>album_orders) {
        this.dateCreated = dateCreated;
        this.shippingOption = shippingOption;
        this.priority = priority;
        this.album_orders = new List<Music>(album_orders);
    }
   
    /**
   	 * Seven argument constuctor, takes the fields listed below,
   	 * calls parent class' 7-argument constructor
   	 * initializes a list of orders
   	 * 
   	 * @param dateCreated - the date of an order created
   	 * @param shippingOption - priority of the shipping
   	 * @param album_orders - List of albums we have available 
   	 * at the store.  
   	 * @param subTotal - the total price that needs to be paid //////////////////right?
   	 * @param shippingDestination - customer's shipping destination
   	 * @param orderStatus - current status of the order
   	 * @param customerID - Customer's ID
   	 * 
   	 */
       
   
    public Order(String dateCreated, String shippingOption,  List<Music> album_orders, double subTotal,
     String shippingDestination, String orderStatus, String customerID) {
        this.dateCreated = dateCreated;
        this.shippingOption = shippingOption;
        this.setPriority(shippingOption);
        this.setShippingExpense(shippingOption);
        this.setTotalCost(subTotal);
        this.shippingDestination = shippingDestination;
        this.orderStatus = orderStatus;
        this.album_orders = new List<Music>(album_orders);
        this.customerID = customerID;
        this.orderID = getSeedOrderID();
    }
    
    /**
	 * Copy Constructor for Order
	 * takes a order object and copies over its attributes
	 * by calling the 10-argument constructor and creating a deep copy of the customer's order history //I'm not sure if this is deep copying
	 * 
	 * @param order - order object to be copied
	 */
    
    public Order(Order order) {
        this.dateCreated = order.dateCreated;
        this.shippingOption = order.shippingOption;
        this.setPriority(order.shippingOption);
        this.setShippingExpense(order.shippingOption);
        this.orderCost = order.orderCost;
        this.shippingDestination = order.shippingDestination;
        this.orderStatus = order.orderStatus;
        this.album_orders = new List<Music>(order.album_orders);
        this.customerID = order.customerID;
        this.orderID = order.orderID;
    }


    /**** GETTERS ****/

    /**
     * Returns a shallow copy of a list of albums available at the store
     * @return list of albums 
     */
    public List<Music> getMusic() {
        return album_orders;
    }
    /**
     * Returns the value of the variable "priority"
     * @return shipping priority
     */
    public int getPriority(){
        return priority;
    }
    /**
     * Returns the value of the variable "TAX_RATE"
     * @return tax rate for the purchase
     */
    public double getTaxRate() {
        return TAX_RATE;
    }
    /**
     * Returns the value of the variable "customerID"
     * @return customer's ID
     */
    public String getCustomerID() {
        return customerID;
    }
    /**
     * Returns the value of the variable "orderID"
     * @return order number
     */
    public int getOrderID() {
        return orderID;
    }
    /**
     * Returns the value of the variable "seedOrderID"
     * @return incremented order ID
     */
    public static int getSeedOrderID() {
        return seedOrderID++;
    }
    /**** SETTERS ****/
    
    /**
   	 * setOrderID - updates the order ID
   	 * 
   	 */
    
    public void setOrderID() {
        this.orderID = getSeedOrderID();
    }

    /**
   	 * setTotalCost - updates the total cost amount after the tax
   	 * has been applied to subtotal.
   	 * @param subtotal - total before tax rate applied
   	 */
    
    public void setTotalCost(double subTotal) {
        this.orderCost = (subTotal * TAX_RATE) + subTotal + shippingExpenses;
    }
    /**
   	 * setOrderStatus - updates the current update status
   	 * @param shippingStatus - current shipping status to
   	 * update the order status
   	 */
    public void updateOrderStatus(String shippingStatus) {
        this.orderStatus = shippingStatus;
    }
    /**
	 * setCustomerID - updates the customer's login
	 * @param customerId - the id used to update the customer's id
	 */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    /**
	 * setDateCreated - updates the order date created
	 * @param dateCreated - order created date used to update customer's order date
	 */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    /**
	 * setDeliveryDestination - updates the shipping address
	 * @param deliveryAddress - shipping address used to update customer's shipping address
	 */
    public void setDeliveryDestination(String deliveryAddress) {
        this.shippingDestination = deliveryAddress;
    }
    /**
     * setshippingMethod - updates the shipping method(priority)
     * @param newShippingOption - shipping option used to update customer's shipping priority
     */
    public void setShippingMethod(String newShippingOption) {
        this.shippingOption = newShippingOption;
        this.setPriority(newShippingOption);
    }

    


    /**** ADDITIONAL MUTATORS ****/
    
    /**
   	 * addSong - takes an music object and appends a deep copy to the end of the
   	 * customer's purchase list
   	 * 
   	 * @param music - an album to be added onto the purchase list.
   	 * 
   	 */	
    public void addSong(Music music) {
        album_orders.addLast(music);
    }

    /**
     * setPriority : Priority is assigned by the key 
     *  Same-Day Shipping - 5
     *  Express One-Day Shipping - 4
     *  Two-Day Shipping - 3
     *  Standard Shipping - 2
     *  No-Rush Shipping - 1
     * 
     * @param shippingOption
     */
    
    public void setPriority(String newShippingOption){
        if(newShippingOption.equals("Same-Day Shipping")) {
            priority = 5;
        }else if(newShippingOption.equals("Express One-Day Shipping")) {
            priority = 4;
        }else if (newShippingOption.equals("Two-Day Shipping")) {
            priority = 3;
        }else if(newShippingOption.equals("Standard Shipping")) {
            priority = 2;
        }else if(newShippingOption.equals("No-Rush Shipping")) {
            priority = 1;
        }else {
            System.out.println("Error: Not a valid shipping Option!");
            System.out.println("-" + newShippingOption + "-");
        }
    }
    /**
     *  setShippingExpense: price is assigned by the key 
     *  Same-Day Shipping - 23.95
     *  Express One-Day Shipping - 18.95
     *  Two-Day Shipping - 11.95
     *  Standard Shipping - 6.95
     *  No-Rush Shipping - 1.95
     * 
     * @param shippingOption
     */
    public void setShippingExpense(String newShippingOption){
        if(newShippingOption.equals("Same-Day Shipping")) {
            shippingExpenses = 23.95;
        }else if(newShippingOption.equals("Express One-Day Shipping")) {
            shippingExpenses = 18.95;
        }else if (newShippingOption.equals("Two-Day Shipping")) {
            shippingExpenses = 11.95;
        }else if(newShippingOption.equals("Standard Shipping")) {
            shippingExpenses = 6.95;
        }else{
            shippingExpenses = 1.95;
        }
    }
    /**
	 * formatOrderDataForFileWrite - takes nothing
	 * 
	 * prints to the .txt file the submitted order of the customer
	 * returns nothing
	 */
    public String formatOrderDataForFileWrite() {
        String data = "";
        data += dateCreated + "\n";
        data += shippingOption + "\n";
        data += orderStatus + "\n";
        data += shippingDestination + "\n";
        data += album_orders.getLength() + "\n";
        album_orders.placeIterator();

        while(!album_orders.offEnd()) {
            data += album_orders.getIterator().formatMusicDataForFileWrite();
            album_orders.advanceIterator();
        }
        return data;
    }
    /**
	 * toString - takes nothing
	 * 
	 * Creates a String of the Order information in the format
	 * 
	 */
    @Override public String toString() {
        DecimalFormat d = new DecimalFormat("###,###.00");

        return "\n****ORDER INFORMATION****"  
        		+"\nOrder Id: " + orderID
        +"\nOrder made by user: " + customerID 
        +"\nOrder Made on: " + dateCreated
        + "\nShipped to: " + shippingDestination
        + "\nOrder Status: " + orderStatus
        + "\nShipping Type: " + shippingOption
        + "\nTotal Cost (Shipping Fee Included): $" + d.format(orderCost)
        + "\nShipping Fee: $" + shippingExpenses
        + "\n\n***** ALBUMS THAT WERE ORDERED ***** " + this.album_orders.toString()
        + "******** END OF ORDER ********";
    }

    }

class PriorityComparator implements Comparator<Order> {
	/**
	 * Compares the two mutual fund accounts by total value determines total value
	 * as number of shares times price per share uses the static Double compare
	 * method to make the comparison
	 * 
	 * @param account1 the first MutualFundAccount
	 * @param account2 the second MutualFundAccount
	 */
	@Override
	public int compare(Order order1, Order order2) {
        //System.out.println("Comparing " + order1.getPriority() + " to " + order2.getPriority() + "\n");
		return Integer.compare(order1.getPriority(), order2.getPriority());
	}
}

class OrderIdComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return Integer.compare(order1.getOrderID(), order2.getOrderID());
    }
}
