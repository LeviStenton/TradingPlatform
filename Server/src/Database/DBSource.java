package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBSource implements DBInterface{

    private PreparedStatement loginVerification;
    private PreparedStatement getPassword;
    private PreparedStatement accountCreation;
    private PreparedStatement promoteAccount;
    private PreparedStatement getOrders;
    private PreparedStatement getAssetCount;
    private PreparedStatement getAllAssets;
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
    private PreparedStatement changeUserPassword;
    private PreparedStatement adminChangeUserPassword;
    private PreparedStatement addNewAsset;
    private PreparedStatement deleteUser;
    private PreparedStatement deleteAsset;
    private PreparedStatement insetNewOrgIntoOrgDetails;
    private PreparedStatement getOrderHistory;
    private PreparedStatement getAllUsers;
    private PreparedStatement getAllOrders;
    private PreparedStatement getAllOrgAssets;
    private PreparedStatement getAllOrgDetails;
    private PreparedStatement getAllOrgs;

    public DBSource() {
        Connection connection = Database.DBConnection.getInstance();

        try {
            String LOGIN_DETAILS = "SELECT * FROM AccountDetails WHERE Username=? AND Password=?";
            loginVerification = connection.prepareStatement(LOGIN_DETAILS);
            String GET_PASSWORD = "SELECT Password FROM AccountDetails WHERE UserID=?";
            getPassword = connection.prepareStatement(GET_PASSWORD);
            String CREATE_ACCOUNT = "INSERT INTO AccountDetails(Username, Password, OrganizationID) VALUES (?, ?, ?)";
            accountCreation = connection.prepareStatement(CREATE_ACCOUNT);
            String PROMOTE_ACCOUNT = "UPDATE AccountDetails SET Admin = ? WHERE Username = ?";
            promoteAccount = connection.prepareStatement(PROMOTE_ACCOUNT);
            String ORDERS = "SELECT * FROM Orders WHERE OrderType = ? AND AssetID = ? ORDER BY DatePlaced";
            getOrders = connection.prepareStatement(ORDERS);
            String ASSETCOUNT = "SELECT AssetID FROM Assets";
            getAssetCount = connection.prepareStatement(ASSETCOUNT);
            String GETALLASSETS = "SELECT * FROM Assets";
            getAllAssets = connection.prepareStatement(GETALLASSETS);
            String ADDORDERHISTORY = "INSERT INTO OrderHistory(OrderID, DatePlaced, AssetID, Price, OrderType, Quantity, UserID, Completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            addOrderHistory = connection.prepareStatement(ADDORDERHISTORY);
            String CHANGEORGCREDITS = "UPDATE OrganizationDetails SET CreditQuantity = ? WHERE OrganizationID = ?";
            changeOrgCredits = connection.prepareStatement(CHANGEORGCREDITS);
            String GETORGCREDITS = "SELECT CreditQuantity FROM OrganizationDetails WHERE OrganizationID = ?";
            getOrgCredits = connection.prepareStatement(GETORGCREDITS);
            String ORDERJOINORGID = "SELECT AccountDetails.OrganizationID FROM Orders LEFT JOIN AccountDetails ON Orders.UserID = AccountDetails.UserID WHERE OrderID = ?";
            orderJoinOrgID = connection.prepareStatement(ORDERJOINORGID);
            String INSERTORGASSET = "INSERT INTO OrganizationAssets(OrganizationID, AssetID, Quantity) VALUES (?,?,?)";
            insertOrgAsset = connection.prepareStatement(INSERTORGASSET);
            String UPDATEORGASSET = "UPDATE OrganizationAssets SET Quantity = ? WHERE OrganizationID = ? AND AssetID = ?";
            updateOrgAsset = connection.prepareStatement(UPDATEORGASSET);
            String GETORGASSETQUANTITY = "SELECT Quantity FROM OrganizationAssets WHERE OrganizationID = ? AND AssetID = ?";
            getOrgAssetQuantity = connection.prepareStatement(GETORGASSETQUANTITY);
            String DELETEORDER = "DELETE FROM Orders WHERE OrderID = ?";
            deleteOrder = connection.prepareStatement(DELETEORDER);
            String ADDORDER = "INSERT INTO Orders(OrderID, DatePlaced, AssetID, Price, OrderType,Quantity,UserID) VALUES (?,?,?,?,?,?,?)";
            addOrder = connection.prepareStatement(ADDORDER);
            String CHANGEORDERQUANTITY = "UPDATE Orders SET Quantity = ? WHERE OrderID = ?";
            changeOrderQuantity = connection.prepareStatement(CHANGEORDERQUANTITY);
            String GETORDERFROMORDERID = "SELECT * FROM Orders WHERE OrderID = ?";
            getOrderFromOrderID = connection.prepareStatement(GETORDERFROMORDERID);
            String CHANGEUSERPASSWORD = "UPDATE AccountDetails SET Password = ? WHERE UserID = ?";
            changeUserPassword = connection.prepareStatement(CHANGEUSERPASSWORD);
            String ADMINCHANGE_PASSWORD = "UPDATE AccountDetails SET Password = ? WHERE Username = ?";
            adminChangeUserPassword = connection.prepareStatement(ADMINCHANGE_PASSWORD);
            String ADDNEWASSET = "INSERT INTO Assets(AssetName) VALUES (?)";
            addNewAsset = connection.prepareStatement(ADDNEWASSET);
            String DELETEUSER = "DELETE FROM AccountDetails WHERE Username = ?";
            deleteUser = connection.prepareStatement(DELETEUSER);
            String DELETEASSET = "DELETE FROM Assets WHERE AssetName = ?";
            deleteAsset = connection.prepareStatement(DELETEASSET);
            String INSERTNEWORGINTOORGDETAILS = "INSERT INTO OrganizationDetails(CreditQuantity,OrganizationName) VALUES (?,?)";
            insetNewOrgIntoOrgDetails = connection.prepareStatement(INSERTNEWORGINTOORGDETAILS);
            String GETORDERHISTORY = "SELECT * FROM OrderHistory";
            getOrderHistory = connection.prepareStatement(GETORDERHISTORY);
            String GETALLUSERS = "SELECT * FROM AccountDetails";
            getAllUsers = connection.prepareStatement(GETALLUSERS);
            String GETALLORDERS = "SELECT * FROM Orders";
            getAllOrders = connection.prepareStatement(GETALLORDERS);
            String GETALLORGASSETS = "SELECT * FROM OrganizationAssets";
            getAllOrgAssets = connection.prepareStatement(GETALLORGASSETS);
            String GETALLORGDETAILS = "SELECT * FROM OrganizationDetails";
            getAllOrgDetails = connection.prepareStatement(GETALLORGDETAILS);
            String GETALLORGS = "SELECT * FROM OrganizationDetails";
            getAllOrgs = connection.prepareStatement(GETALLORGS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the whole OrderHistory DB table
     * @return Returns the table as List<Order>
     */
    public List<Order> GetOrderHistory(){
        ResultSet rs;
        List<Order> orderHistory = new ArrayList<>();
        try{
            rs = getOrderHistory.executeQuery();
            while (rs.next()){
                orderHistory.add( new Order(rs,rs.getString("Completed")));
            }
            return orderHistory;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Gets the whole OrgDetails DB table
     * @return Returns the table as List<OrgDetails>
     */
    public List<OrgDetails> GetAllOrgDetails(){
        ResultSet rs;
        List<OrgDetails> details = new ArrayList<>();
        try{
            rs = getAllOrgDetails.executeQuery();
            while (rs.next()){
                details.add( new OrgDetails(rs.getInt("OrganizationID"),rs.getDouble("CreditQuantity"),rs.getString("OrganizationName")));
            }
            return details;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the whole OrgAssets DB table
     * @return Returns the table as List<OrgAssets>
     */
    public List<OrgAssets> GetAllOrgAssets(){
        ResultSet rs;
        List<OrgAssets> assets = new ArrayList<>();
        try{
            rs = getAllOrgAssets.executeQuery();
            while (rs.next()){
                assets.add( new OrgAssets(rs.getInt("OrganizationID"),rs.getInt("AssetID"),rs.getDouble("Quantity")));
            }
            return assets;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Gets the whole AccountDetails DB table
     * @return Returns the table as List<User>
     */
    public List<User> GetAllUsers(){
        ResultSet rs;
        List<User> users = new ArrayList<>();
        try{
            rs = getAllUsers.executeQuery();
            while (rs.next()){
                users.add( new User(rs.getInt("UserID"),rs.getString("Username"),rs.getInt("OrganizationID"),rs.getBoolean("Admin")));
            }
            return users;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the whole AccountDetails DB table
     * @return Returns the table as List<User>
     */
    public List<Order> GetAllOrders(){
        ResultSet rs;
        List<Order> orders = new ArrayList<>();
        try{
            rs = getAllOrders.executeQuery();
            while (rs.next()){
                orders.add( new Order(rs));
            }
            return orders;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts new org into OrganizationDetails
     *
     * @param credits The amount of credits the org should have
     * @param orgName The name of the new org
     */
    public void InsertNewOrgIntoOrgDetails(float credits, String orgName) {
        try {
            insetNewOrgIntoOrgDetails.setFloat(1, credits);
            insetNewOrgIntoOrgDetails.setString(1, orgName);
            insetNewOrgIntoOrgDetails.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Adds a new Asset to Asset table
     *
     * @param assetName the asset to add
     */
    public void AddNewAsset(String assetName) {
        try {
            addNewAsset.setString(1, assetName);
            addNewAsset.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Used to change a users password
     * Hashing is done client side
     *
     * @param currentPass The user's current password
     * @param newPass The new password to set
     * @param userID The user password to change
     * @return The success of the operation
     */
    public boolean ChangeUserPassword(String currentPass, String newPass, int userID) {
        ResultSet rs;
        try {
            getPassword.setInt(1, userID);
            rs = getPassword.executeQuery();
            rs.next();
            String dbPass = rs.getString("Password");
            if(currentPass.equals(dbPass)){
                changeUserPassword.setString(1, newPass);
                changeUserPassword.setInt(2, userID);
                changeUserPassword.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Used to change a users password
     * Hashing is done client side
     *
     * @param username The username of the user to be changed
     * @param newPass The new password to set
     * @return The success of the operation
     */
    public boolean AdminChangeUserPassword(String username, String newPass) {
        try {
            adminChangeUserPassword.setString(1, newPass);
            adminChangeUserPassword.setString(2, username);
            adminChangeUserPassword.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the quantity of a order
     * Selects order based on orderID
     *
     * @param orderID the ID of the order
     * @return The current quantity that the order has
     */
    public double GetOrderQuantity(int orderID) {
        ResultSet rs;
        double quantity = -1;
        try {
            getOrderFromOrderID.setInt(1, orderID);
            rs = getOrderFromOrderID.executeQuery();
            quantity = rs.getInt("Quantity");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantity;
    }

    /**
     * Changes the quantity of a order based on the operator
     * Selects order based on orderID
     *
     * @param orderID  The ID of the order
     * @param quantity The amount to change the order by
     * @param operator Takes math operators eg "+" "-". Will change the order quantity based on this
     */
    public void ChangeOrderQuantity(int orderID, double quantity, String operator) {
        double assetQuantity = GetOrderQuantity(orderID);
        assetQuantity = ChangeWithOperator(assetQuantity, quantity, operator);
        try {
            changeOrderQuantity.setDouble(1, assetQuantity);
            changeOrderQuantity.setInt(2, orderID);
            changeOrderQuantity.executeUpdate();
        } catch (SQLException e) {

            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
    }

    /**
     * Adds a order to the Orders database
     *
     * @param orderID  The ID of the order
     * @param assetID  the ID of the asset
     * @param price    the price per unit
     * @param type     What type of order is the order eg "B" "S"
     * @param quantity The amount of the asset to be listed
     * @param userID   The userID of the person who placed the order
     */
    public void AddOrder(int orderID, int assetID, double price, String type, double quantity, int userID) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        try {
            if (orderID != -1) {
                addOrder.setInt(1, orderID);
            }
            addOrder.setString(2, formatter.format(date));
            addOrder.setInt(3, assetID);
            addOrder.setDouble(4, price);
            addOrder.setString(5, type);
            addOrder.setDouble(6, quantity);
            addOrder.setInt(7, userID);
            addOrder.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a order to the Orders database
     *
     * @param order The order to add to the database
     */
    public void AddOrder(Order order) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        try {
            if (order.getOrderID() != -1) {
                addOrder.setInt(1, order.getOrderID());
            }
            addOrder.setString(2, formatter.format(date));
            addOrder.setInt(3, order.getAssetID());
            addOrder.setDouble(4, order.getPrice());
            addOrder.setString(5, order.getOrderType());
            addOrder.setDouble(6, order.getQuantity());
            addOrder.setInt(7, order.getUserID());
            addOrder.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a asset into a org if it doesn't exist or updates it instead
     *
     * @param orgID    The ID of the org
     * @param assetID  The ID of the asset
     * @param quantity The amount to be changed by
     * @param operator Takes math operators eg "+" "-". Will change the amount a Org has of a asset based on this
     */
    public void InsertOrgAsset(int orgID, int assetID, double quantity, String operator) {
        try {
            insertOrgAsset.setInt(1, orgID);
            insertOrgAsset.setInt(2, assetID);
            insertOrgAsset.setDouble(3, quantity);
            insertOrgAsset.executeUpdate();
        } catch (SQLException e) {
            //error code 19 = org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY]
            if (e.getErrorCode() == 19) {
                //Org already has some of this asset it needs to be updated instead of inserted
                UpdateOrgAsset(quantity, orgID, assetID, operator);
                System.out.println("Yes");
            } else {
                System.out.println(e.getErrorCode());
                e.printStackTrace();
            }

        }
    }

    /**
     * Deletes order from Orders DB
     * Selects based on orderID
     *
     * @param orderID The ID of the order
     */
    public void DeleteOrder(int orderID) {
        try {
            deleteOrder.setInt(1, orderID);
            deleteOrder.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given a username and password, will attempt to verify it against
     * the database to determine whether there is an account associated
     * with them.
     *
     * @return The User class with their details
     */
    public User loginAttempt(String userName, String password) {
        ResultSet rs;
        try {
            loginVerification.setString(1, userName);
            loginVerification.setString(2, password);
            rs = loginVerification.executeQuery();
            return new User(rs.getInt("UserID"), rs.getString("Username"),
                    rs.getString("Password"), rs.getInt("OrganizationID"), rs.getBoolean("Admin"));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Left joins AccountDetails to Orders to add the user who placed the order orgID
     *
     * @param orderID The ID of the order
     * @return the user who placed the order orgID
     */
    public int OrderJoinOrgID(int orderID) {
        ResultSet rs;

        try {
            orderJoinOrgID.setInt(1, orderID);
            rs = orderJoinOrgID.executeQuery();
            return rs.getInt("OrganizationID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Adds a completed order to the OrderHistory database
     *
     * @param order Contains all the order details
     */
    public void AddToOrderHistory(Order order) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a org current amount of credits
     * Selects from orgID
     *
     * @param orgID the ID of the org
     */
    public double GetOrgCredits(int orgID) {
        ResultSet rs;
        double currentCredits = -1;
        try {
            getOrgCredits.setInt(1, orgID);
            rs = getOrgCredits.executeQuery();
            currentCredits = rs.getInt("CreditQuantity");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return currentCredits;
    }


    /**
     * Acts as a calculator
     *
     * @param current  The left side of the operator
     * @param toChange The right side of the operator
     * @param operator Takes math operators "+" "-" "="
     * @return The result of the calculator
     */
    public double ChangeWithOperator(double current, double toChange, String operator) {
        switch (operator) {
            case "+" -> current += toChange;
            case "-" -> current -= toChange;
            case "=" -> current = toChange;
        }
        return current;
    }

    /**
     * Gets how many of a asset a org has
     * Select based on orgID and assetID
     *
     * @param orgID   The ID of the org
     * @param assetID The ID of the asset
     */
    public double GetOrgAssetQuantity(int orgID, int assetID) {
        ResultSet rs;

        try {
            getOrgAssetQuantity.setInt(1, orgID);
            getOrgAssetQuantity.setInt(2, assetID);
            rs = getOrgAssetQuantity.executeQuery();
            return rs.getDouble("Quantity");
        } catch (SQLException e) {

            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates the amount of a asset a org has
     *
     * @param quantity The amount to change by
     * @param orgID    The ID of the org
     * @param assetID  the ID of the asset
     * @param operator Takes math operators "+" "-" "="
     */
    public void UpdateOrgAsset(double quantity, int orgID, int assetID, String operator) {
        double assetQuantity = GetOrgAssetQuantity(orgID, assetID);
        assetQuantity = ChangeWithOperator(assetQuantity, quantity, operator);
        try {
            updateOrgAsset.setDouble(1, assetQuantity);
            updateOrgAsset.setInt(2, orgID);
            updateOrgAsset.setInt(3, assetID);
            updateOrgAsset.executeUpdate();
        } catch (SQLException e) {

            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
    }

    /**
     * Change the amount of credits a org has
     *
     * @param credits  The amount to change
     * @param orgID    The ID of the org
     * @param operator Takes math operators "+" "-" "="
     */
    public void ChangeOrgCredits(double credits, int orgID, String operator) {
        double currentCredits;
        currentCredits = GetOrgCredits(orgID);
        currentCredits = ChangeWithOperator(currentCredits, credits, operator);
        try {
            changeOrgCredits.setDouble(1, currentCredits);
            changeOrgCredits.setInt(2, orgID);
            changeOrgCredits.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void CreateAccount(String userName, String password, Integer orgID, boolean admin) {

    }

    /**
     * Creates a new Account
     *
     * @param userName The selected name for the account
     * @param password The selected password for the account
     * @param orgID    The org the user will be apart of
     */
    public boolean CreateAccount(String userName, String password, Integer orgID) {
        try {
            accountCreation.setString(1, userName);
            accountCreation.setString(2, password);
            accountCreation.setString(3, orgID.toString());
            accountCreation.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all orders from the Orders database
     * Selects based on order Type and assetID
     *
     * @param assetID   The ID of the asset
     * @param orderType The type of order eg "B" "S"
     * @return A list of all orders matching the params
     */
    public List<Order> GetOrders(int assetID, String orderType) {
        ResultSet rs;
        List<Order> orders = new ArrayList<>();
        Order order;

        try {
            getOrders.setString(1, orderType);
            getOrders.setInt(2, assetID);
            rs = getOrders.executeQuery();
            while (rs.next()) {
                order = new Order(rs);
                orders.add(order);
            }
            return orders;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finds how many assets are currently in the system
     *
     * @return The highest AssetID
     */
    public List<Integer> GetAssetCount() {
        ResultSet rs;
        List<Integer> id = new ArrayList<>();
        try {
            rs = getAssetCount.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("AssetID"));
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Returns all assets in the database
     *
     * @return A list of Assets objects
     */
    public List<Asset> GetAllAssets() {
        ResultSet rs;
        List<Asset> assets = new ArrayList<>();
        try {
            rs = getAllAssets.executeQuery();
            while (rs.next())
                assets.add(new Asset((int)rs.getObject("AssetID"), (String) rs.getObject("AssetName")));
            return assets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    public boolean PromoteAccount(String username, boolean admin){
        try {
            promoteAccount.setBoolean(1, admin);
            promoteAccount.setString(2, username);
            promoteAccount.executeUpdate();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean RemoveAccount(String username){
        try {
            deleteUser.setString(1, username);
            deleteUser.executeUpdate();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean AddAsset(String assetName){
        try {
            addNewAsset.setString(1, assetName);
            addNewAsset.executeUpdate();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean RemoveAsset(String assetName){
        try {
            deleteAsset.setString(1, assetName);
            deleteAsset.executeUpdate();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<OrgDetails> GetAllOrgs() {
        ResultSet rs;
        List<OrgDetails> orgs = new ArrayList<>();
        try {
            rs = getAllOrgs.executeQuery();
            while (rs.next())
                orgs.add(new OrgDetails(rs.getInt("OrganizationID"), rs.getDouble("CreditQuantity"), rs.getString("OrganizationName")));
            return orgs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}