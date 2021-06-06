package Database;


import java.util.ArrayList;
import java.util.List;

/**
 * Acts as temp Database storage system so the client does not need to ping the server everytime it needs something
 */
public class DatabaseStorage {
    private static List<Asset> assetList = new ArrayList<>();
    private static List<User> profileList = new ArrayList<>();
    private static List<OrgAssets> orgAssetsList = new ArrayList<>();
    private static List<OrgDetails> orgDetailsList = new ArrayList<>();
    private static List<Order> orderList = new ArrayList<>();
    private static List<Order> orderHistoryList = new ArrayList<>();

    /**
     *Gets the stored assetList DB table
     * @return List<Asset> List of Asset class
     */
    public static List<Asset> getAssetList(){
        return assetList;
    }

    /**
     * sets the stored assetList
     * Used when updating from the database
     * @param assetList2 The updated version of the assetList
     */
    public static void setAssetList(List<Asset> assetList2){
        assetList = assetList2;
    }

    /**
     * Gets the stored orderHistory DB table
     * @return List<Order> List of Order class
     */
    public static List<Order> getOrderHistory(){
        return orderHistoryList;
    }

    /**
     * sets the stored orderHistory
     * Used when updating from the database
     * @param orderHistoryList2 The updated version of the orderHistory
     */
    public static void setOrderHistory(List<Order> orderHistoryList2){
        orderHistoryList = orderHistoryList2;
    }

    /**
     * Gets the stored profileList DB table
     * Note the client does not store the passwords
     * @return List<User> List of User class
     */
    public static List<User> getProfileList(){
        return profileList;
    }

    /**
     * Sets a list of user objects to this storage system
     *
     * @param profileList2 the list to set
     */
    public static void setProfileList(List<User> profileList2){
        profileList = profileList2;
    }

    /**
     * Gets all the mock orders in this storage system
     *
     * @return the mock orders
     */
    public static List<Order> getOrders(){
        return orderList;
    }

    /**
     * Set a list of order objects to this storage system
     *
     * @param orderList2 the list to set
     */
    public static void setOrders(List<Order> orderList2){
        orderList = orderList2;
    }

    /**
     * Gets all the mock assets belonging to a mock organization
     *
     * @return the list of mock assets
     */
    public static List<OrgAssets> getOrgAssets(){ return orgAssetsList; }

    /**
     * Sets a list of OrgAssets objects to this storage system
     *
     * @param orgAssetsList2 the list of OrgAssets
     */
    public static void setOrgAssets(List<OrgAssets> orgAssetsList2){
        orgAssetsList = orgAssetsList2;
    }

    /**
     * Gets all the organizations in the mock storage system
     *
     * @return the list of OrgDetails objects
     */
    public static List<OrgDetails> getOrgDetails(){ return orgDetailsList; }

    /**
     * Sets a list of OrgDetails objects to this mock storage system
     *
     * @param orgDetailsList2 a list of OrgDetails objects
     */
    public static void setOrgDetails(List<OrgDetails> orgDetailsList2){
        orgDetailsList = orgDetailsList2;
    }
}
