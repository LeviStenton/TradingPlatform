import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    private PreparedStatement loginVerification;
    private PreparedStatement accountCreation;
    private PreparedStatement getOrders;
    private PreparedStatement getAssetCount;
    private PreparedStatement addOrderHistory;
    private PreparedStatement changeOrgCredits;
    private PreparedStatement getOrgCredits;
    private PreparedStatement orderJoinOrgID;
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

    public void AddToOrderHistory(Order order){
        try {
            addOrderHistory.setInt(1, order.getOrderID());
            addOrderHistory.setString(2, order.getDatePlaced());
            addOrderHistory.setInt(3, order.getAssetID());
            addOrderHistory.setFloat(4, order.getPrice());
            addOrderHistory.setString(5, order.getOrderType());
            addOrderHistory.setInt(6, order.getQuantity());
            addOrderHistory.setInt(7, order.getUserID());
            addOrderHistory.setString(8, order.getCompleted());
            addOrderHistory.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void AddOrgCredits(float credits, int orgID, String operator){
        ResultSet rs;
        float currentCredits = 0;

        try{
            getOrgCredits.setInt(1, orgID);
            rs = getOrgCredits.executeQuery();
            currentCredits = rs.getInt("CreditQuantity");

        }catch (SQLException e){
            e.printStackTrace();
        }

        switch (operator){
            case "+":
                currentCredits += credits;
                break;
            case "-":
                currentCredits -= credits;
                break;
            case "=":
                currentCredits = credits;
                break;
        }

        try {
            changeOrgCredits.setFloat(1, currentCredits);
            changeOrgCredits.setInt(2, orgID);
            changeOrgCredits.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

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

    public void ChangeCredits(){

    }

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

//accountCreation.executeQuery();
