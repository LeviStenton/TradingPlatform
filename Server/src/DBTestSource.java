import java.util.List;

public class DBTestSource implements DBInterface{
    @Override
    public void DeteteOrgFromOrgDetails(int orgID) {

    }

    @Override
    public void InsertNewOrgIntoOrgDetails(float credits, String orgName) {

    }

    @Override
    public void DeleteAsset(int assetID) {

    }

    @Override
    public void AddNewAsset(String assetName) {

    }

    @Override
    public void DeleteUser(int userID) {

    }

    @Override
    public void ChangeUserPassword(String password, int userID) {

    }

    @Override
    public void ChangeUserOrg(int orgID, int userID) {

    }

    @Override
    public double GetOrderQuantity(int orderID) {
        return 0;
    }

    @Override
    public void ChangeOrderQuantity(int orderID, double quantity, String operator) {

    }

    @Override
    public void AddOrder(int orderID, int assetID, double price, String type, double quantity, int userID) {

    }

    @Override
    public void InsertOrgAsset(int orgID, int assetID, double quantity, String operator) {

    }

    @Override
    public void DeleteOrder(int orderID) {

    }

    @Override
    public boolean loginAttempt(String userName, String password) {
        return false;
    }

    @Override
    public int OrderJoinOrgID(int orderID) {
        return 0;
    }

    @Override
    public void AddToOrderHistory(Order order) {

    }

    @Override
    public double GetOrgCredits(int orgID) {
        return 0;
    }

    @Override
    public double ChangeWithOperator(double current, double toChange, String operator) {
        return 0;
    }

    @Override
    public double GetOrgAssetQuantity(int orgID, int assetID) {
        return 0;
    }

    @Override
    public void UpdateOrgAsset(double quantity, int orgID, int assetID, String operator) {

    }

    @Override
    public void ChangeOrgCredits(double credits, int orgID, String operator) {

    }

    @Override
    public void CreateAccount(String userName, String password, Integer orgID) {

    }

    @Override
    public List<Order> GetOrders(int assetID, String orderType) {
        return null;
    }

    @Override
    public List<Integer> GetAssetCount() {
        return null;
    }
}
