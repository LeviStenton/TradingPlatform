import Database.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * BLACK BOX TESTS --------------------------------------------------------------------------
 * Black box testing is when you write tests to ensure your method performs
 * exactly as specified in it's specification (eg javadocs)
 */
class BlackBoxTests {
    DBTestSource source = new DBTestSource();
    DBSource db = new DBSource();

    @Before
    public void init() {
        this.source = new DBTestSource();
    }

    @Test
    void CreateAccount(){
        String userName = "Eggs";
        String password = "Bacon";
        db.CreateAccount(userName,password,1);
    }

    @Test
    void LoginVerifyMatch(){
        source.CreateAccount("Alexander","123",1, false);
        assertNotNull(source.loginAttempt("Alexander", "123"));
    }

    @Test
    void LoginVerifyWrongUserName(){
        source.CreateAccount("Alexander","123",1, false);
        assertNull(source.loginAttempt("alex", "123"));
    }

    @Test
    void LoginVerifyWrongPassword(){
        source.CreateAccount("Alexander","123",1, false);
        assertNull(source.loginAttempt("Alexander", "321"));
    }

    @Test
    void LoginVerifyNoAccounts(){
        assertNull(source.loginAttempt("Alexander", "321"));
    }

    @Test
    void RunMarketPlaceLoopNormalCase(){
        Marketplace mk = new Marketplace();
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(0,"1");

        source.AddNewAsset("hub");

        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, true);

        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){
            if(org.getOrgID() == 1){
                assertEquals(10,org.getQuantity(), "Asset were not added to org 1");
            }
        }
        for(OrgDetails org: source.orgDetailsList){
            if(org.getOrgID() == 2){
                assertEquals(150,org.getCredits(), "Credits were not added to org 2");
            }
            if(org.getOrgID() == 1){
                assertEquals(0,org.getCredits(), "Credits were not removed from org 1");
            }
        }
        assertEquals(0, source.orderList.size(), "Orders was not removed from orderList");
        assertEquals(2, source.orderHistoryList.size(), "Orders were not added to orderHistoryList");

    }

    @Test
    void RunMarketPlaceLoopNoCase(){
        Marketplace mk = new Marketplace();
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(150,"1");

        source.AddNewAsset("hub");
        source.AddNewAsset("pc");


        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, true);

        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,2,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){

            assertEquals(0,org.getQuantity(), "Assets were add added to org");

        }
        for(OrgDetails org: source.orgDetailsList){

            assertEquals(150,org.getCredits(), "Credits were added to org");

        }
        assertEquals(2, source.orderList.size(), "Orders were removed from orderList");
        assertEquals(0, source.orderHistoryList.size(), "Orders were added to orderHistoryList");
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseMatch(){
        Marketplace mk = new Marketplace();
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(0,"2");

        source.AddNewAsset("hub");
        source.AddNewAsset("pc");


        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, false);

        source.AddOrder(100,1,20,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();

        for(OrgAssets org: source.orgAssetsList){
            if(org.getOrgID() == 1){
                assertEquals(10,org.getQuantity(), "Asset were not added to org 1");
            }
        }
        for(OrgDetails org: source.orgDetailsList){
            if(org.getOrgID() == 2){
                assertEquals(150,org.getCredits(), "Credits were not added to org 2");
            }
            if(org.getOrgID() == 1){
                assertEquals(0,org.getCredits(), "Credits were not removed from org 1");
            }
        }
        for(Order order:source.orderList){
            assertEquals(10,order.getQuantity(), "Buy Order did not remain in OrderList");
        }
        assertEquals(1,source.orderList.size(), "OrderList should only have one order");

        for(Order order:source.orderHistoryList){
            if(order.getOrderType().equals("B")){
                assertEquals(10,order.getQuantity(), "Buy order has wrong q");
            }
            if(order.getOrderType().equals("S")){
                assertEquals(10,order.getQuantity(), "Sell order has wrong q");
            }
        }
        assertEquals(2,source.orderHistoryList.size(), "Sell order should have two orders");
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseNoMatch(){
        Marketplace mk = new Marketplace();
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(150,"2");

        source.AddNewAsset("hub");


        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, false);

        source.AddOrder(100,1,13,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){

            assertEquals(0,org.getQuantity(), "Assets were add added to org");

        }
        for(OrgDetails org: source.orgDetailsList){

            assertEquals(150,org.getCredits(), "Credits were added to org");

        }
        assertEquals(2, source.orderList.size(), "Orders were removed from orderList");
        assertEquals(0, source.orderHistoryList.size(), "Orders were added to orderHistoryList");
    }

    @Test
    void assetCount(){
        source.AddNewAsset("Test");
        int[] result = new int[1];
        result[0] = 1;
        assertEquals(result[0], source.GetAssetCount().get(0));
    }

    @Test
    void getAllAssets(){
        List<Asset> assets = db.GetAllAssets();
        for (Asset asset : assets) {
            System.out.println(asset.getAssetName());
        }
    }
    @Test
    void loginVerification(){
        String username = "LeviStenton";
        String password = "test123";
        User user = db.loginAttempt(username, password);
        System.out.println(user.getUserName() + " " + user.getAdmin());
    }

    @Test
    void changePassword(){
        int userID = 1;
        String currentPass = "test";
        String newPass = "test123";
        assertTrue(db.ChangeUserPassword(currentPass, newPass, userID));
    }

    @Test
    void adminChangePassword(){
        String username = "LeviStenton";
        String newPass = "test";
        assertTrue(db.AdminChangeUserPassword(username, newPass));
    }

    @Test
    void promoteAccount(){
        String username = "LeviStenton";
        assertTrue(db.PromoteAccount(username, false));
    }

    @Test
    void removeAsset(){
        String assetName = "test";
        assertTrue(db.RemoveAsset(assetName));
    }

    @Test
    void addAsset(){
        String assetName = "test";
        assertTrue(db.AddAsset(assetName));
    }

    @Test
    void getOrgAssetQuantity(){
        int orgID = 2;
        int assetID = 1;
        assertEquals(1, db.GetOrgAssetQuantity(orgID, assetID));
    }

    @Test
    void getOrgs(){
        List<OrgDetails> orgs = db.GetAllOrgs();
        assertNotNull(orgs);
    }
}

/**
 * GLASS/WHITE BOX TESTS ------------------------------------------------------------------
 * Glass/White box testing is when you write tests based on looking at the actual
 * code for the function to ensure each portion is called correctly
 */
class WhiteBoxTests {
    DBTestSource source = new DBTestSource();
    DBSource db = new DBSource();

    @Before
    public void init() {
        this.source = new DBTestSource();
    }

    @Test
    void CreateAccount(){
        String userName = "Eggs";
        String password = "Bacon";
        db.CreateAccount(userName,password,1);
    }

    @Test
    void LoginVerifyMatch(){
        source.CreateAccount("Alexander","123",1, false);
        assertNotNull(source.loginAttempt("Alexander", "123"));
    }

    @Test
    void LoginVerifyWrongUserName(){
        source.CreateAccount("Alexander","123",1, false);
        assertNull(source.loginAttempt("alex", "123"));
    }

    @Test
    void LoginVerifyWrongPassword(){
        source.CreateAccount("Alexander","123",1, false);
        assertNull(source.loginAttempt("Alexander", "321"));
    }

    @Test
    void LoginVerifyNoAccounts(){
        assertNull(source.loginAttempt("Alexander", "321"));
    }

    @Test
    void RunMarketPlaceLoopNormalCase(){
        Marketplace mk = new Marketplace();
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(0,"1");

        source.AddNewAsset("hub");

        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, true);

        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){
            if(org.getOrgID() == 1){
                assertEquals(10,org.getQuantity(), "Asset were not added to org 1");
            }
        }
        for(OrgDetails org: source.orgDetailsList){
            if(org.getOrgID() == 2){
                assertEquals(150,org.getCredits(), "Credits were not added to org 2");
            }
            if(org.getOrgID() == 1){
                assertEquals(0,org.getCredits(), "Credits were not removed from org 1");
            }
        }
        assertEquals(0, source.orderList.size(), "Orders was not removed from orderList");
        assertEquals(2, source.orderHistoryList.size(), "Orders were not added to orderHistoryList");

    }

    @Test
    void RunMarketPlaceLoopNoCase(){
        Marketplace mk = new Marketplace();
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(150,"1");

        source.AddNewAsset("hub");
        source.AddNewAsset("pc");


        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, true);

        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,2,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){

            assertEquals(0,org.getQuantity(), "Assets were add added to org");

        }
        for(OrgDetails org: source.orgDetailsList){

            assertEquals(150,org.getCredits(), "Credits were added to org");

        }
        assertEquals(2, source.orderList.size(), "Orders were removed from orderList");
        assertEquals(0, source.orderHistoryList.size(), "Orders were added to orderHistoryList");
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseMatch(){
        Marketplace mk = new Marketplace();
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(0,"2");

        source.AddNewAsset("hub");
        source.AddNewAsset("pc");


        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, false);

        source.AddOrder(100,1,20,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();

        for(OrgAssets org: source.orgAssetsList){
            if(org.getOrgID() == 1){
                assertEquals(10,org.getQuantity(), "Asset were not added to org 1");
            }
        }
        for(OrgDetails org: source.orgDetailsList){
            if(org.getOrgID() == 2){
                assertEquals(150,org.getCredits(), "Credits were not added to org 2");
            }
            if(org.getOrgID() == 1){
                assertEquals(0,org.getCredits(), "Credits were not removed from org 1");
            }
        }
        for(Order order:source.orderList){
            assertEquals(10,order.getQuantity(), "Buy Order did not remain in OrderList");
        }
        assertEquals(1,source.orderList.size(), "OrderList should only have one order");

        for(Order order:source.orderHistoryList){
            if(order.getOrderType().equals("B")){
                assertEquals(10,order.getQuantity(), "Buy order has wrong q");
            }
            if(order.getOrderType().equals("S")){
                assertEquals(10,order.getQuantity(), "Sell order has wrong q");
            }
        }
        assertEquals(2,source.orderHistoryList.size(), "Sell order should have two orders");
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseNoMatch(){
        Marketplace mk = new Marketplace();
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(150,"2");

        source.AddNewAsset("hub");


        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, false);

        source.AddOrder(100,1,13,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){

            assertEquals(0,org.getQuantity(), "Assets were add added to org");

        }
        for(OrgDetails org: source.orgDetailsList){

            assertEquals(150,org.getCredits(), "Credits were added to org");

        }
        assertEquals(2, source.orderList.size(), "Orders were removed from orderList");
        assertEquals(0, source.orderHistoryList.size(), "Orders were added to orderHistoryList");
    }

    @Test
    void assetCount(){
        source.AddNewAsset("Test");
        int[] result = new int[1];
        result[0] = 1;
        assertEquals(result[0], source.GetAssetCount().get(0));
    }

    @Test
    void getAllAssets(){
        List<Asset> assets = db.GetAllAssets();
        for (Asset asset : assets) {
            System.out.println(asset.getAssetName());
        }
    }
    @Test
    void loginVerification(){
        String username = "LeviStenton";
        String password = "test123";
        User user = db.loginAttempt(username, password);
        System.out.println(user.getUserName() + " " + user.getAdmin());
    }

    @Test
    void changePassword(){
        int userID = 1;
        String currentPass = "test";
        String newPass = "test123";
        assertTrue(db.ChangeUserPassword(currentPass, newPass, userID));
    }

    @Test
    void adminChangePassword(){
        String username = "LeviStenton";
        String newPass = "test";
        assertTrue(db.AdminChangeUserPassword(username, newPass));
    }

    @Test
    void promoteAccount(){
        String username = "LeviStenton";
        assertTrue(db.PromoteAccount(username, false));
    }

    @Test
    void removeAsset(){
        String assetName = "test";
        assertTrue(db.RemoveAsset(assetName));
    }

    @Test
    void addAsset(){
        String assetName = "test";
        assertTrue(db.AddAsset(assetName));
    }

    @Test
    void getOrgAssetQuantity(){
        int orgID = 2;
        int assetID = 1;
        assertEquals(1, db.GetOrgAssetQuantity(orgID, assetID));
    }

    @Test
    void getOrgs(){
        List<OrgDetails> orgs = db.GetAllOrgs();
        assertNotNull(orgs);
    }

//    @Test
//    void buyOrder() {
//
//    }
//
//    @Test
//    void sellOrder() {
//
//    }
//
//    @Test
//    void accountCreation() {
//
//    }
//
//    @Test
//    void removeAccount() {
//        String username = "Eggs";
//        assertTrue(sock.removeAccount(username));
//    }
//
//    @Test
//    void loginVerification() {
//        String username = "LeviStenton";
//        String password = "test";
//        User user = sock.sendLogin(username, password);
//        System.out.println(user.getUserName() + " " + user.getAdmin());
//    }
//
//    @Test
//    void addAsset() {
//        String assetName = "test";
//        assertTrue(sock.addAsset(assetName));
//    }
//
//    @Test
//    void removeAsset() {
//        String assetName = "test";
//        assertTrue(sock.removeAsset(assetName));
//    }
//
//    @Test
//    void orderValidation() {
//
//    }
//
//    @Test
//    void accountPromotion() {
//        String username = "LeviStenton";
//        assertTrue(sock.promoteAccount(username, true));
//    }
//
//    @Test
//    void changePassword() {
//        int userID = 1;
//        String currentPass = "test";
//        String newPass = "test123";
//        assertTrue(sock.changePassword(currentPass, newPass, userID));
//    }
//
//    @Test
//    void adminChangePassword() {
//        String username = "LeviStenton";
//        String newPass = "test";
//        assertTrue(sock.adminChangePassword(username, newPass));
//    }
//
//    @Test
//    void addingOrder() {
//        Order order = new Order(1, 1, 1, "1", 1, 1);
//        sock.sendOrder(order);
//    }
//
//    @Test
//    void removingOrder() {
//        Order order = new Order(1, 1, 1, "1", 1, 1);
//        sock.sendOrder(order);
//    }
//
//    @Test
//    void editORder() {
//
//    }
//
//    @Test
//    void configReading() {
//
//    }
}
