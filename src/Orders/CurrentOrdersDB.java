package Orders;

/**
 * Handles updating of the current orders database
 */
public class CurrentOrdersDB {

    public static final String CREATE_TABLE = null; //Create table if it does not exist

    /**
     * Adds a order to the database
     *
     * @param order the order to be added
     */
    public void addOrder(Order order){

    }


    /**
     * Deletes order from the database
     *
     * @param id The order id to be deleted
     */
    public void deleteOrder(int id){

    }

    /**
     * Change a order to have a different quantity or price
     */
    public void updateOrder(int id){

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
