package Orders;

/**
 * Hold details about a order so they can be changed or referenced
 */
public class COrder {
    private String type;
    private double price;
    private double quantity;
    private String asset;
    private int placedBy;

    /**
     *Constructor for the Order class
     *
     * @param type The type of order, eg 'B' for buy and 'S' for sell
     * @param price The price that the order was placed for
     * @param quantity The quantity of the asset to be sold
     * @param asset The asset name
     * @param placedBy The person who placed the order
     */
    public COrder(String type, float price, float quantity, String asset, int placedBy){
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.asset = asset;
        this.placedBy = placedBy;
    }

    /**
     * Get the name of the person who placed the order
     * @return
     */
    public int getPlacedBy(){return this.placedBy;}

    /**
     * Get the Type of the order
     * @return 'B' or 'S' depending on, if it is a buy or sell order
     */
    public String getType(){ return this.type; }

    /**
     * Get the Price of the order
     * @return Float of the price the order was placed for
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
    public int getAsset(){ return this.asset; }

    /**
     * Set the Price of the order
     * @param price Float of the new price for the order
     */
    public void setPrice(double price){ this.price = price; }

    /**
     * Set the Quantity of the order
     * @param quantity Float of the new quantity for the order
     */
    public void setQuantity(double quantity){ this.quantity = quantity; }
}