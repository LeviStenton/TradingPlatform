import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hold details about a order so they can be changed or referenced
 */
public class Order {
    private int orderID;
    private String datePlaced;
    private String assetID;
    private float price;
    private String orderType;
    private float quantity;
    private int userID;

    /**
     * Constructor for the Order class

     */
    public Order(ResultSet order){
        try {
            this.orderType = order.getString("OrderType");
            this.price = order.getFloat("Price");
            this.quantity = order.getInt("Quantity");
            this.assetID = order.getString("AssetID");
            this.userID = order.getInt("UserID");
            this.orderID = order.getInt("OrderID");
            this.datePlaced = order.getString("DatePlaced");
        }catch (SQLException e){
            e.printStackTrace();
        }

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
     * @return Float of the price the order was placed for
     */
    public float getPrice(){ return this.price; }

    /**
     * Get the Quantity of the order
     * @return Float of the quantity the order was placed for
     */
    public float getQuantity(){ return this.quantity; }

    /**
     * Get the Asset type that was placed in the order
     * @return The string of the Asset name
     */
    public String getAsset(){ return this.assetID; }

    public int getOrderID(){return this.orderID;}

    public String getDatePlaced(){return this.datePlaced;}

    /**
     * Set the Price of the order
     * @param price Float of the new price for the order
     */
    public void setPrice(float price){ this.price = price; }

    /**
     * Set the Quantity of the order
     * @param quantity Float of the new quantity for the order
     */
    public void setQuantity(float quantity){ this.quantity = quantity; }

    /**
     * Set the date the order was placed
     * @param datePlaced
     */
    public void setDatePlaced(String datePlaced){this.datePlaced = datePlaced;}
}