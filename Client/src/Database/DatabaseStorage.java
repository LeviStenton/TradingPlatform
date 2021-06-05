package Database;
import Database.Asset;
import Database.Order;
import Database.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseStorage {
    private static List<Asset> assetList = new ArrayList<Asset>();
    private static List<User> profileList = new ArrayList<User>();
    //List<OrgAssets> orgAssetsList = new ArrayList<OrgAssets>();
    //List<OrgDetails> orgDetailsList = new ArrayList<OrgDetails>();
    private static List<Order> orderList = new ArrayList<Order>();
    private static List<Order> orderHistoryList = new ArrayList<Order>();

    public static List<Asset> getAssetList(){
        return assetList;
    }
    public static void setAssetList(List<Asset> assetList2){
        assetList = assetList2;
    }

    public static List<Order> getOrderHistory(){
        return orderHistoryList;
    }
    public static void setOrderHistory(List<Order> orderHistoryList2){
        orderHistoryList = orderHistoryList2;
    }
}
