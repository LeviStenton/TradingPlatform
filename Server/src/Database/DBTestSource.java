package Database;

import Database.DBInterface;

import java.util.ArrayList;
import java.util.List;

public class DBTestSource implements DBInterface {

    List<Assets> assetsList = new ArrayList<Assets>();
    List<AccountDetails> accountDetailsList = new ArrayList<AccountDetails>();
    List<OrgAssets> orgAssetsList = new ArrayList<OrgAssets>();
    List<OrgDetails> orgDetailsList = new ArrayList<OrgDetails>();
    List<Order> orderList = new ArrayList<Order>();
    List<Order> orderHistoryList = new ArrayList<Order>();

    public List<Assets> getAssetsList(){return this.assetsList;};
    public List<AccountDetails> getAccountDetailsList(){return this.accountDetailsList;};
    public List<OrgAssets> getOrgAssetsList(){return this.orgAssetsList;};
    public List<OrgDetails> getOrgDetailsList(){return this.orgDetailsList;};
    public List<Order> getOrderList(){return this.orderList;};
    public List<Order> getOrderHistoryList(){return this.orderHistoryList;};


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
        assetsList.removeIf(t -> t.getAssetID() == assetID);
    }

    @Override
    public void AddNewAsset(String assetName) {
        assetsList.add(new Assets(assetsList.size() + 1,assetName));
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
    public boolean loginAttempt(String userName, String password) {
        for(AccountDetails account:accountDetailsList){
            if(account.getUserName() == userName && account.getPassword().equals(password)){
                return  true;
            }
        }
        return false;
    }

    @Override
    public int OrderJoinOrgID(int orderID) {
        for(Order order: orderList){
            for(AccountDetails account: accountDetailsList){
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
    public void CreateAccount(String userName, String password, Integer orgID) {
        accountDetailsList.add(new AccountDetails(accountDetailsList.size() + 1,userName,password,orgID));
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
        for(Assets asset:assetsList){
            toReturn.add(asset.getAssetID());
        }
        return toReturn;
    }
}
