package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hold details about a order so they can be changed or referenced
 */
public class Order {
    private int orderID;
    private String datePlaced;
    private int assetID;
    private double price;
    private String orderType;
    private double quantity;
    private int userID;
    private String completed;

    /**
     * Constructor for the Database.Order class

     */
    public Order(ResultSet order){
        try {
            this.orderType = order.getString("OrderType");
            this.price = order.getDouble("Price");
            this.quantity = order.getDouble("Quantity");
            this.assetID = order.getInt("AssetID");
            this.userID = order.getInt("UserID");
            this.orderID = order.getInt("OrderID");
            this.datePlaced = order.getString("DatePlaced");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Order(ResultSet order, String completed){
        try {
            this.orderType = order.getString("OrderType");
            this.price = order.getDouble("Price");
            this.quantity = order.getInt("Quantity");
            this.assetID = order.getInt("AssetID");
            this.userID = order.getInt("UserID");
            this.orderID = order.getInt("OrderID");
            this.datePlaced = order.getString("DatePlaced");
        }catch (SQLException e){
            e.printStackTrace();
        }
        this.completed = completed;
    }

    /**
     * Get the ID of the person who placed the order
     * @return
     */
    public int getUserID(){return this.userID;}

    /**
     * Get the Type of the order
     * @return 'B' or 'S' depending on, if it is a buy or sell order
     */
    public String getOrderType(){ return this.orderType; }

    /**
     * Get the Price of the order
     * @return double of the price the order was placed for
     */
    public double getPrice(){ return this.price; }

    /**
     * Get the Quantity of the order
     * @return Float of the quantity the order was placed for
     */
    public double getQuantity(){ return this.quantity; }

    /**
     * Get the Asset type that was placed in the order
     * @return The string of the Asset name
     */
    public int getAssetID(){ return this.assetID; }
    //TODO comments
    public int getOrderID(){return this.orderID;}

    public String getDatePlaced(){return this.datePlaced;}

    public String getCompleted(){return this.completed;}

    /**
     * Set the Price of the order
     * @param price double of the new price for the order
     */
    public void setPrice(double price){ this.price = price; }

    /**
     * Set the Quantity of the order
     * @param quantity double of the new quantity for the order
     */
    public void setQuantity(double quantity){ this.quantity = quantity; }

    /**
     * Set the date the order was placed
     * @param datePlaced
     */
    public void setDatePlaced(String datePlaced){this.datePlaced = datePlaced;}

    public void setCompleted(String completed){this.completed = completed;};
}