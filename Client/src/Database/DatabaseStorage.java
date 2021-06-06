package Database;


import java.util.ArrayList;
import java.util.List;

public class DatabaseStorage {
    private static List<Asset> assetList = new ArrayList<Asset>();
    private static List<User> profileList = new ArrayList<User>();
    private static List<OrgAssets> orgAssetsList = new ArrayList<OrgAssets>();
    private static List<OrgDetails> orgDetailsList = new ArrayList<OrgDetails>();
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

    public static List<User> getProfileList(){
        return profileList;
    }
    public static void setProfileList(List<User> profileList2){
        profileList = profileList2;
    }

    public static List<Order> getOrders(){
        return orderList;
    }
    public static void setOrders(List<Order> orderList2){
        orderList = orderList2;
    }

    public static List<OrgAssets> getOrgAssets(){ return orgAssetsList; }
    public static void setOrgAssets(List<OrgAssets> orgAssetsList2){
        orgAssetsList = orgAssetsList2;
    }

    public static List<OrgDetails> getOrgDetails(){ return orgDetailsList; }
    public static void setOrgDetails(List<OrgDetails> orgDetailsList2){
        orgDetailsList = orgDetailsList2;
    }
}
