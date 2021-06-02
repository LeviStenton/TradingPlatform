package Database;

import Database.DBInterface;

import java.util.List;

public class DBTestSource implements DBInterface {

    List<Assets> assetsList;
    List<AccountDetails> accountDetailsList;
    List<OrgAssets> orgAssetsList;
    List<OrgDetails> orgDetailsList;
    List<Order> orderList;
    List<Order> orderHistoryList;


    @Override
    public void DeteteOrgFromOrgDetails(int orgID) {
        orgDetailsList.removeIf(t -> t.getOrgID() == orgID);
        orgAssetsList.removeIf(t -> t.getOrgID() == orgID);

    }

    @Override
    public void InsertNewOrgIntoOrgDetails(float credits, String orgName) {
        orgDetailsList.add(new OrgDetails(orgDetailsList.size()-1,credits,orgName));
    }

    @Override
    public void DeleteAsset(int assetID) {
        assetsList.removeIf(t -> t.getAssetID() == assetID);
    }

    @Override
    public void AddNewAsset(String assetName) {
        assetsList.add(new Assets(assetsList.size()-1,assetName));
    }

    @Override
    public void DeleteUser(int userID) {
        accountDetailsList.removeIf(t -> t.getUserID() == userID);
    }

    @Override
    public void ChangeUserPassword(String password, int userID) {
        for(AccountDetails account: accountDetailsList){
            if(account.getUserID() == userID){
                account.setPassword(password);
            }
        }
    }

    @Override
    public void ChangeUserOrg(int orgID, int userID) {
        for(AccountDetails account: accountDetailsList){
            if(account.getUserID() == userID){
                account.setOrgID(orgID);
            }
        }
    }

    @Override
    public double GetOrderQuantity(int orderID) {
        for(Order account: orderList){
            if(account.getOrderID() == orderID){
                return account.getQuantity();
            }
        }
        return -1;
    }

    @Override
    public void ChangeOrderQuantity(int orderID, double quantity, String operator) {
        double assetQuantity = GetOrderQuantity(orderID);
        assetQuantity = ChangeWithOperator(assetQuantity,quantity,operator);
        for(Order account: orderList){
            if(account.getOrderID() == orderID){
                account.setQuantity(assetQuantity);
            }
        }
    }

    @Override
    public void AddOrder(int orderID, int assetID, double price, String type, double quantity, int userID) {
        orderList.add(new Order(orderID,assetID,price,type,quantity,userID));
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
