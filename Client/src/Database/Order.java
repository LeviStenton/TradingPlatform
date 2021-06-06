package Database;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hold details about a order so they can be changed or referenced
 */
public class Order implements Serializable {
    private int orderID = -1;
    private String datePlaced;
    private int assetID;
    private double price;
    private String orderType;
    private double quantity;
    private int userID;
    private String completed;

    /**
     * Constructor for the Database.Order class
     *
     * @param order The results from querying the database for an order
     */
    public Order(ResultSet order) {
        try {
            this.orderType = order.getString("OrderType");
            this.price = order.getDouble("Price");
            this.quantity = order.getDouble("Quantity");
            this.assetID = order.getInt("AssetID");
            this.userID = order.getInt("UserID");
            this.orderID = order.getInt("OrderID");
            this.datePlaced = order.getString("DatePlaced");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Constructor for the Database.Order class
     *
     * @param order The results from querying the database for an order
     * @param completed Determines whether the order is completed
     */
    public Order(ResultSet order, String completed) {
        try {
            this.orderType = order.getString("OrderType");
            this.price = order.getDouble("Price");
            this.quantity = order.getInt("Quantity");
            this.assetID = order.getInt("AssetID");
            this.userID = order.getInt("UserID");
            this.orderID = order.getInt("OrderID");
            this.datePlaced = order.getString("DatePlaced");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.completed = completed;
    }

    /**
     * Constructor for the Database.Order class, made manually
     *
     * @param orderID The ID number of the order
     * @param assetID The ID number of the asset
     * @param price The price of the order
     * @param type The type of order, 'sell' or 'buy'
     * @param quantity The quantity of the asset sold/bought
     * @param userID The ID number of the user issuing the order
     */
    public Order(int orderID, int assetID, double price, String type, double quantity, int userID){
        this.orderID = orderID;
        this.assetID = assetID;
        this.price = price;
        this.orderType = type;
        this.quantity = quantity;
        this.userID = userID;
    }

    /**
     * Constructor for the Database.Order class, made manually
     * @param assetID The ID number of the asset
     * @param price The price of the order
     * @param type The type of order, 'sell' or 'buy'
     * @param quantity The quantity of the asset sold/bought
     * @param userID The ID number of the user issuing the order
     */
    public Order(int assetID, double price, String type, double quantity, int userID){
        this.assetID = assetID;
        this.price = price;
        this.orderType = type;
        this.quantity = quantity;
        this.userID = userID;
    }

    /**
     * Get the ID of the person who placed the order
     *
     * @return This Order's user ID
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Get the Type of the order
     *
     * @return 'B' or 'S' depending on, if it is a buy or sell order
     */
    public String getOrderType() {
        return this.orderType;
    }

    /**
     * Get the Price of the order
     *
     * @return double of the price the order was placed for
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Get the Quantity of the order
     *
     * @return Float of the quantity the order was placed for
     */
    public double getQuantity() {
        return this.quantity;
    }

    /**
     * Get the Asset type that was placed in the order
     *
     * @return The string of the Asset name
     */
    public int getAssetID() {
        return this.assetID;
    }

    /**
     * Gets the ID of the order
     *
     * @return the ID number of the order
     */
    public int getOrderID() {
        return this.orderID;
    }

    /**
     * Gets the date the order was placed
     *
     * @return the date the or was placed
     */
    public String getDatePlaced() {
        return this.datePlaced;
    }

    /**
     * Gets that state of completion of the order
     *
     * @return whether the order was completed or not
     */
    public String getCompleted() {
        return this.completed;
    }

    /**
     * Set the Price of the order
     *
     * @param price double of the new price for the order
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set the Quantity of the order
     *
     * @param quantity double of the new quantity for the order
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Set whether or not that order is complete
     *
     * @param completed Determines whether the order is completed
     */
    public void setCompleted(String completed) {
        this.completed = completed;
    }

}