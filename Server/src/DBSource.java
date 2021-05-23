import org.sqlite.SQLiteException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBSource {

    private final Connection connection;

    private final String LOGIN_DETAILS = "SELECT Username, Password FROM AccountDetails WHERE Username=?";
    private final String CREATE_ACCOUNT = "INSERT INTO AccountDetails(Username, Password, OrganizationID) VALUES (?, ?, ?)";
    private final String ORDERS = "SELECT * FROM Orders WHERE OrderType = ? AND AssetID = ? ORDER BY DatePlaced";
    private final String ASSETCOUNT ="SELECT MAX(AssetID) FROM Assets";
    private final String ADDORDERHISTORY = "INSERT INTO OrderHistory(OrderID, DatePlaced, AssetID, Price, OrderType, Quantity, UserID, Completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String CHANGEORGCREDITS = "UPDATE OrganizationDetails SET CreditQuantity = ? WHERE OrganizationID = ?";
    private final String GETORGCREDITS = "SELECT CreditQuantity FROM OrganizationDetails WHERE OrganizationID = ?";
    private final String ORDERJOINORGID = "SELECT AccountDetails.OrganizationID FROM Orders LEFT JOIN AccountDetails ON Orders.UserID = AccountDetails.UserID WHERE OrderID = ?";
    private final String INSERTORGASSET = "INSERT INTO OrganizationAssets(OrganizationID, AssetID, Quantity) VALUES (?,?,?)";
    private final String UPDATEORGASSET = "UPDATE OrganizationAssets SET Quantity = ? WHERE OrganizationID = ? AND AssetID = ?";
    private final String GETORGASSETQUANTITY = "SELECT Quantity FROM OrganizationAssets WHERE OrganizationID = ? AND AssetID = ?";
    private final String DELETEORDER = "DELETE FROM Orders WHERE OrderID = ?";
    private final String ADDORDER = "INSERT INTO Orders(OrderID, DatePlaced, AssetID, Price, OrderType,Quantity,UserID) VALUES (?,?,?,?,?,?,?)";
    private final String CHANGEORDERQUANTITY = "UPDATE Orders SET Quantity = ? WHERE OrderID = ?";
    private final String GETORDERFROMORDERID = "SELECT * FROM Orders WHERE OrderID = ?";

    private PreparedStatement loginVerification;
    private PreparedStatement accountCreation;
    private PreparedStatement getOrders;
    private PreparedStatement getAssetCount;
    private PreparedStatement addOrderHistory;
    private PreparedStatement changeOrgCredits;
    private PreparedStatement getOrgCredits;
    private PreparedStatement orderJoinOrgID;
    private PreparedStatement insertOrgAsset;
    private PreparedStatement updateOrgAsset;
    private PreparedStatement getOrgAssetQuantity;
    private PreparedStatement deleteOrder;
    private PreparedStatement addOrder;
    private PreparedStatement changeOrderQuantity;
    private PreparedStatement getOrderFromOrderID;

    public DBSource(){
        connection = DBConnection.getInstance();

        try {
            loginVerification = connection.prepareStatement(LOGIN_DETAILS);
            accountCreation = connection.prepareStatement(CREATE_ACCOUNT);
            getOrders = connection.prepareStatement(ORDERS);
            getAssetCount = connection.prepareStatement(ASSETCOUNT);
            addOrderHistory = connection.prepareStatement(ADDORDERHISTORY);
            changeOrgCredits = connection.prepareStatement(CHANGEORGCREDITS);
            getOrgCredits = connection.prepareStatement(GETORGCREDITS);
            orderJoinOrgID = connection.prepareStatement(ORDERJOINORGID);
            insertOrgAsset = connection.prepareStatement(INSERTORGASSET);
            updateOrgAsset = connection.prepareStatement(UPDATEORGASSET);
            getOrgAssetQuantity = connection.prepareStatement(GETORGASSETQUANTITY);
            deleteOrder = connection.prepareStatement(DELETEORDER);
            addOrder = connection.prepareStatement(ADDORDER);
            changeOrderQuantity = connection.prepareStatement(CHANGEORDERQUANTITY);
            getOrderFromOrderID = connection.prepareStatement(GETORDERFROMORDERID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the quantity of a order
     * Selects order based on orderID
     * @param orderID the ID of the order
     * @return The current quantity that the order has
     */
    public double GetOrderQuantity(int orderID){
        ResultSet rs;
        double quantity = -1;
        try{
            getOrderFromOrderID.setInt(1, orderID);
            rs = getOrderFromOrderID.executeQuery();
            quantity = rs.getInt("Quantity");

        }catch (SQLException e){
            e.printStackTrace();
        }

        return quantity;
    }

    /**
     * Changes the quantity of a order based on the operator
     * Selects order based on orderID
     * @param orderID The ID of the order
     * @param quantity The amount to change the order by
     * @param operator Takes math operators eg "+" "-". Will change the order quantity based on this
     */
    public void ChangeOrderQuantity(int orderID, double quantity, String operator){
        ResultSet rs;
        double assetQuantity = GetOrderQuantity(orderID);
        assetQuantity = ChangeWithOperator(assetQuantity, quantity, operator);
        try{
            changeOrderQuantity.setDouble(1,assetQuantity);
            changeOrderQuantity.setInt(2,orderID);
            changeOrderQuantity.executeUpdate();
        }
        catch (SQLException e) {

            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
    }

    /**
     * Adds a order to the Orders database
     * @param orderID The ID of the order
     * @param assetID the ID of the asset
     * @param price the price per unit
     * @param type What type of order is the order eg "B" "S"
     * @param quantity The amount of the asset to be listed
     * @param userID The userID of the person who placed the order
     */
    public void AddOrder(int orderID,int assetID, double price, String type, double quantity, int userID){
        ResultSet rs;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        try{
            if(orderID != -1){
                addOrder.setInt(1,orderID);
            }
            addOrder.setString(2,formatter.format(date));
            addOrder.setInt(3,assetID);
            addOrder.setDouble(4,price);
            addOrder.setString(5,type);
            addOrder.setDouble(6,quantity);
            addOrder.setInt(7,userID);
            addOrder.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a asset into a org if it doesn't exist or updates it instead
     * @param orgID The ID of the org
     * @param assetID The ID of the asset
     * @param quantity The amount to be changed by
     * @param operator Takes math operators eg "+" "-". Will change the amount a Org has of a asset based on this
     */
    public void InsertOrgAsset(int orgID, int assetID, double quantity, String operator){
        ResultSet rs;
        try{
            insertOrgAsset.setInt(1,orgID);
            insertOrgAsset.setInt(2,assetID);
            insertOrgAsset.setDouble(3,quantity);
            insertOrgAsset.executeUpdate();
        }
        catch (SQLException e) {
            //error code 19 = org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY]
            if(e.getErrorCode() == 19){
                //Org already has some of this asset it needs to be updated instead of inserted
                UpdateOrgAsset(quantity, orgID, assetID, operator);
                System.out.println("Yes");
            }
            else {
                System.out.println(e.getErrorCode());
                e.printStackTrace();
            }

        }
    }

    /**
     * Deletes order from Orders DB
     * Selects based on orderID
     * @param orderID The ID of the order
     */
    public void DeleteOrder(int orderID){
        try {
            deleteOrder.setInt(1, orderID);
            deleteOrder.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginAttempt(String userName, String password){
        ResultSet rs;
        try {
            loginVerification.setString(1, userName);
            rs = loginVerification.executeQuery();
            rs.next();
            return rs.getString("Username").equals(userName) && rs.getString("Password").equals(password);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Left joins AccountDetails to Orders to add the user who placed the order orgID
     * @param orderID The ID of the order
     * @return the user who placed the order orgID
     */
    public int OrderJoinOrgID(int orderID){
        ResultSet rs;

        try {
            orderJoinOrgID.setInt(1,orderID);
            rs = orderJoinOrgID.executeQuery();
            return rs.getInt("OrganizationID");
        }catch (SQLException e){
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Adds a completed order to the OrderHistory database
     * @param order Contains all the order details
     */
    public void AddToOrderHistory(Order order){
        try {
            addOrderHistory.setInt(1, order.getOrderID());
            addOrderHistory.setString(2, order.getDatePlaced());
            addOrderHistory.setInt(3, order.getAssetID());
            addOrderHistory.setDouble(4, order.getPrice());
            addOrderHistory.setString(5, order.getOrderType());
            addOrderHistory.setDouble(6, order.getQuantity());
            addOrderHistory.setInt(7, order.getUserID());
            addOrderHistory.setString(8, order.getCompleted());
            addOrderHistory.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets a org current amount of credits
     * Selects from orgID
     * @param orgID the ID of the org
     * @return
     */
    public double GetOrgCredits(int orgID){
        ResultSet rs;
        double currentCredits = -1;
        try{
            getOrgCredits.setInt(1, orgID);
            rs = getOrgCredits.executeQuery();
            currentCredits = rs.getInt("CreditQuantity");

        }catch (SQLException e){
            e.printStackTrace();
        }

        return currentCredits;
    }


    /**
     * Acts as a calculator
     * @param current The left side of the operator
     * @param toChange The right side of the operator
     * @param operator Takes math operators "+" "-" "="
     * @return The result of the calculator
     */
    public double ChangeWithOperator(double current,double toChange, String operator){
        switch (operator){
            case "+":
                current += toChange;
                break;
            case "-":
                current -= toChange;
                break;
            case "=":
                current = toChange;
                break;
        }
        return current;
    }

    /**
     * Gets how many of a asset a org has
     * Select based on orgID and assetID
     * @param orgID The ID of the org
     * @param assetID The ID of the asset
     * @return
     */
    public double GetOrgAssetQuantity(int orgID, int assetID){
        ResultSet rs;
        float quantity = 0;

        try{
            getOrgAssetQuantity.setInt(1,orgID);
            getOrgAssetQuantity.setInt(2,assetID);
            rs = getOrgAssetQuantity.executeQuery();
            return rs.getDouble("Quantity");
        }
        catch (SQLException e) {

            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates the amount of a asset a org has
     * @param quantity The amount to change by
     * @param orgID The ID of the org
     * @param assetID the ID of the asset
     * @param operator Takes math operators "+" "-" "="
     */
    public void UpdateOrgAsset(double quantity, int orgID, int assetID, String operator){
        ResultSet rs;
        double assetQuantity = GetOrgAssetQuantity(orgID,assetID);
        assetQuantity = ChangeWithOperator(assetQuantity, quantity, operator);
        try{
            updateOrgAsset.setDouble(1,assetQuantity);
            updateOrgAsset.setInt(2,orgID);
            updateOrgAsset.setInt(3,assetID);
            updateOrgAsset.executeUpdate();
        }
        catch (SQLException e) {

            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
    }

    /**
     *Change the amount of credits a org has
     * @param credits The amount to change
     * @param orgID The ID of the org
     * @param operator Takes math operators "+" "-" "="
     */
    public void ChangeOrgCredits(double credits, int orgID, String operator){
        double currentCredits = 0;

        currentCredits = GetOrgCredits(orgID);
        currentCredits = ChangeWithOperator(currentCredits, credits, operator);


        try {
            changeOrgCredits.setDouble(1, currentCredits);
            changeOrgCredits.setInt(2, orgID);
            changeOrgCredits.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * Creates a new Account
     * @param userName The selected name for the account
     * @param password The selected password for the account
     * @param orgID The org the user will be apart of
     */
    public void CreateAccount(String userName, String password, Integer orgID){
        ResultSet rs;
        try {
            accountCreation.setString(1, userName);
            accountCreation.setString(2, password);
            accountCreation.setString(3, orgID.toString());
            accountCreation.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all orders from the Orders database
     * Selects based on order Type and assetID
     * @param assetID The ID of the asset
     * @param orderType The type of order eg "B" "S"
     * @return A list of all orders matching the params
     */
    public List<Order> GetOrders(int assetID, String orderType){
        ResultSet rs;
        List<Order> orders = new ArrayList<Order>();
        Order order;

        try{
            getOrders.setString(1, orderType);
            getOrders.setInt(2, assetID);
            rs = getOrders.executeQuery();
            while(rs.next()){
                order = new Order(rs);
                orders.add(order);
            }
            return orders;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finds how many assets are currently in the system
     * @return The highest AssetID
     */
    public int GetAssetCount(){
        ResultSet rs;
        int count = 0;

        try {
            rs = getAssetCount.executeQuery();
            count = rs.getInt("MAX(AssetID)");
            return count;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return count;
    }
}