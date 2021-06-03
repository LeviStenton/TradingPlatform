package Orders;

/**
 * Handles updating of the current orders database
 * @dprecated This has been moved to the server side, needs to be changed to notify the server to make these changes
 */

public class CurrentOrdersDB {
    //Create table if it does not exist
    public static final String CREATE_TABLE = null;

    /**
     * Adds a order to the database
     *
     * @param COrder the order to be added
     */
    public void addOrder(Order COrder){

    }


    /**
     * Deletes order from the database
     *
     * @param id The order id to be deleted
     */
    public void deleteOrder(int id){

    }

    /**
     * Change a placed orders price
     *
     * @param id The id of the order to be changed
     * @param price The new Price for the order
     */
    public void updatePrice(int id, float price){

    }

    /**
     * Change a placed orders quantity
     *
     * @param id The id of the order to be changed
     * @param quantity The new Quantity for the order
     */
    public void updateQuantity(int id, float quantity){

    }

    /**
     * Gets the number of orders in the database
     *
     * @return size of database
     */
    public int getSize(){

        return 0;
    }

    /**
     * Finalizes any resources used by the data source and ensures data is persisted
     */
    public void close(){

    }

}
