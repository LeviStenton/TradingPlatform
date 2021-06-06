package Database;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the mock version of DBSource, works the same BUT changes a set of lists instead of the database
 */
public class DBTestSource implements DBInterface {

    List<Asset> assetList = new ArrayList<>();
    List<User> profileList = new ArrayList<>();
    public List<OrgAssets> orgAssetsList = new ArrayList<>();
    public List<OrgDetails> orgDetailsList = new ArrayList<>();
    public List<Order> orderList = new ArrayList<>();
    public List<Order> orderHistoryList = new ArrayList<>();

    @Override
    public void InsertNewOrgIntoOrgDetails(float credits, String orgName) {
        orgDetailsList.add(new OrgDetails(orgDetailsList.size() + 1,credits,orgName));
    }

    @Override
    public void AddNewAsset(String assetName) {
        assetList.add(new Asset(assetList.size() + 1,assetName));
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
    public void AddOrder(Order order) {
        orderList.add(order);
    }

    @Override
    public void InsertOrgAsset(int orgID, int assetID, double quantity, String operator) {
        for (OrgAssets asset:orgAssetsList){
            if(asset.getOrgID() == orgID){
                UpdateOrgAsset(quantity,orgID,assetID,operator);
                return;
            }
        }
        orgAssetsList.add(new OrgAssets(orgID, assetID,quantity));

    }

    @Override
    public void DeleteOrder(int orderID) {
        orderList.removeIf(t -> t.getOrderID() == orderID);
    }

    @Override
    public User loginAttempt(String userName, String password) {
        for(User account: profileList){
            if(account.getUserName().equals(userName) && account.getPassword().equals(password)){
                return  account;
            }
        }
        return null;
    }

    @Override
    public int OrderJoinOrgID(int orderID) {
        for(Order order: orderList){
            for(User account: profileList){
                if(order.getOrderID() == orderID){
                    if(account.getUserID() == order.getUserID()){
                        return account.getOrgID();
                    }
                }
            }
        }
        return -1;

    }

    @Override
    public void AddToOrderHistory(Order order) {
        orderHistoryList.add(order);
    }

    @Override
    public double GetOrgCredits(int orgID) {
        for(OrgDetails org: orgDetailsList){
            if(org.getOrgID() == orgID){
                return org.getCredits();
            }
        }

        return -1;
    }

    @Override
    public double ChangeWithOperator(double current, double toChange, String operator) {
        switch (operator) {
            case "+" -> current += toChange;
            case "-" -> current -= toChange;
            case "=" -> current = toChange;
        }
        return current;
    }

    @Override
    public double GetOrgAssetQuantity(int orgID, int assetID) {
        for(OrgAssets org: orgAssetsList){
            if(org.getOrgID() == orgID && org.getAssetID() == assetID){
                return org.getQuantity();
            }
        }
        return -1;
    }

    @Override
    public void UpdateOrgAsset(double quantity, int orgID, int assetID, String operator) {
        double assetQuantity = GetOrgAssetQuantity(orgID, assetID);
        assetQuantity = ChangeWithOperator(assetQuantity, quantity, operator);
        for(OrgAssets org:orgAssetsList){
            if(org.getOrgID() == orgID && org.getAssetID() == assetID){
                org.setQuantity(assetQuantity);
            }
        }
    }

    @Override
    public void ChangeOrgCredits(double credits, int orgID, String operator) {
        double currentCredits;

        currentCredits = GetOrgCredits(orgID);
        currentCredits = ChangeWithOperator(currentCredits, credits, operator);
        for(OrgDetails org: orgDetailsList){
            if(org.getOrgID() == orgID){
                org.setCredits(currentCredits);
            }
        }
    }

    @Override
    public void CreateAccount(String userName, String password, Integer orgID, boolean admin) {
        profileList.add(new User(profileList.size() + 1,userName,password,orgID,admin));
    }

    @Override
    public List<Order> GetOrders(int assetID, String orderType) {
        List<Order> toReturn = new ArrayList<>();
        for(Order order: orderList){
            if(order.getOrderType().equals(orderType)){
                if(order.getAssetID() == assetID){
                    toReturn.add(order);
                }
            }
        }
        if(toReturn.size() != 0){
            return toReturn;
        }
        return null;
    }

    @Override
    public List<Integer> GetAssetCount() {
        List<Integer> toReturn = new ArrayList<>();
        for(Asset asset: assetList){
            toReturn.add(asset.getAssetID());
        }
        return toReturn;
    }
}
