package Database;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the mock version of DBSource, works the same BUT changes a set of lists instead of the database
 */
public class DBTestSource implements DBInterface {

    List<Asset> assetList = new ArrayList<Asset>();
    List<User> profileList = new ArrayList<User>();
    List<OrgAssets> orgAssetsList = new ArrayList<OrgAssets>();
    List<OrgDetails> orgDetailsList = new ArrayList<OrgDetails>();
    List<Order> orderList = new ArrayList<Order>();
    List<Order> orderHistoryList = new ArrayList<Order>();

    public List<Asset> getAssetsList(){return this.assetList;};
    public List<User> getAccountDetailsList(){return this.profileList;};
    public List<OrgAssets> getOrgAssetsList(){return this.orgAssetsList;};
    public List<OrgDetails> getOrgDetailsList(){return this.orgDetailsList;};
    public List<Order> getOrderList(){return this.orderList;};
    public List<Order> getOrderHistoryList(){return this.orderHistoryList;};

    /**
     *
     * @param orgID
     */
    @Override
    public void DeteteOrgFromOrgDetails(int orgID) {
        orgDetailsList.removeIf(t -> t.getOrgID() == orgID);
        orgAssetsList.removeIf(t -> t.getOrgID() == orgID);

    }

    @Override
    public void InsertNewOrgIntoOrgDetails(float credits, String orgName) {
        orgDetailsList.add(new OrgDetails(orgDetailsList.size() + 1,credits,orgName));
    }

    @Override
    public void DeleteAsset(int assetID) {
        assetList.removeIf(t -> t.getAssetID() == assetID);
    }

    @Override
    public void AddNewAsset(String assetName) {
        assetList.add(new Asset(assetList.size() + 1,assetName));
    }

    @Override
    public void DeleteUser(int userID) {
        profileList.removeIf(t -> t.getUserID() == userID);
    }

    @Override
    public void ChangeUserPassword(String password, int userID) {
        for(User account: profileList){
            if(account.getUserID() == userID){
                account.setPassword(password);
            }
        }
    }

    @Override
    public void ChangeUserOrg(int orgID, int userID) {
        for(User account: profileList){
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
            if(account.getUserName() == userName && account.getPassword().equals(password)){
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
        double currentCredits = 0;

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
        List<Order> toReturn = new ArrayList<Order>();
        for(Order order: orderList){
            if(order.getOrderType() == orderType){
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
        List<Integer> toReturn = new ArrayList<Integer>();
        for(Asset asset: assetList){
            toReturn.add(asset.getAssetID());
        }
        return toReturn;
    }
}
