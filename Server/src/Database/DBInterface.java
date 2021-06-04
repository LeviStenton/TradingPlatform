package Database;

import java.util.List;

public interface DBInterface {


    void DeteteOrgFromOrgDetails(int orgID);

    void InsertNewOrgIntoOrgDetails(float credits, String orgName);

    void DeleteAsset(int assetID);

    void AddNewAsset(String assetName);

    void DeleteUser(int userID);

    void ChangeUserPassword(String password, int userID);

    void ChangeUserOrg(int orgID, int userID);

    double GetOrderQuantity(int orderID);

    void ChangeOrderQuantity(int orderID, double quantity, String operator);

    void AddOrder(int orderID, int assetID, double price, String type, double quantity, int userID);

    void AddOrder(Order order);

    void InsertOrgAsset(int orgID, int assetID, double quantity, String operator);

    void DeleteOrder(int orderID);

    User loginAttempt(String userName, String password);

    int OrderJoinOrgID(int orderID);

    void AddToOrderHistory(Order order);

    double GetOrgCredits(int orgID);

    double ChangeWithOperator(double current, double toChange, String operator);

    double GetOrgAssetQuantity(int orgID, int assetID);

    void UpdateOrgAsset(double quantity, int orgID, int assetID, String operator);

    void ChangeOrgCredits(double credits, int orgID, String operator);

    void CreateAccount(String userName, String password, Integer orgID, boolean admin);

    List<Order> GetOrders(int assetID, String orderType);

    List<Integer> GetAssetCount();
}
