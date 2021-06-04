package Database;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerTests {
    DBTestSource source = new DBTestSource();
    DBSource db = new DBSource();
    @Before
    public void init() {
        this.source = new DBTestSource();
    }

    @Test
    void CreateAccount(){
        String userName = "LeviStenton";
        String password = "password1";
        String getUserName;

        source.CreateAccount(userName, password, 1, false);

        for(User account: source.getAccountDetailsList()){
            getUserName = account.getUserName();
            assertTrue(getUserName.equals(userName));
            return;

        }
    }

//    @Test
//    void LoginVerify(){
//        source.CreateAccount("Alexander","1000:2bed40233126488f9226952f04b413a2:540229735fbf3a9885e1469340f3e2d6077cdff016c3322027cb3073dd14efedc85ae7dcbb8bb4abb4a5736fca4c2232d37b99a76faa2c8d0381299366af8b0b",1);
//        assertTrue(source.loginAttempt("Alexander", "1000:2bed40233126488f9226952f04b413a2:540229735fbf3a9885e1469340f3e2d6077cdff016c3322027cb3073dd14efedc85ae7dcbb8bb4abb4a5736fca4c2232d37b99a76faa2c8d0381299366af8b0b"));
//    }

    @Test
    void RunMarketPlaceLoopNormalCase(){
        Marketplace mk = new Marketplace(source);
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
        assertTrue(source.orderList.size() == 0, "Orders was not removed from orderList");
        assertTrue(source.orderHistoryList.size() == 2, "Orders were not added to orderHistoryList");

    }

    @Test
    void RunMarketPlaceLoopNoCase(){
        Marketplace mk = new Marketplace(source);
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(0,"1");

        source.AddNewAsset("hub");
        source.AddNewAsset("pc");


        source.CreateAccount("Alex","123",1, false);
        source.CreateAccount("Levi","123",2, true);

        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,2,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){
            if(org.getOrgID() == 1){
                assertEquals(10,org.getQuantity(), "Asset were not added to org 2");
            }
        }
        for(OrgDetails org: source.orgDetailsList){
            if(org.getOrgID() == 2){
                assertEquals(150,org.getCredits(), "Credits were not added to org 1");
            }
        }
        assertTrue(source.orderList.size() == 0, "Orders was not removed from orderList");
        assertTrue(source.orderHistoryList.size() == 2, "Orders were not added to orderHistoryList");
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseMatch(){
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,20,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseNoMatch(){
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,13,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
    }

    @Test
    void InsertAsset(){
        DBSource source = new DBSource();
        //source.InsertOrgAsset(1,1,10);
    }

    @Test
    void getAllAssets(){
        Asset[] assets = db.GetAllAssets();
        for(int i = 0; i < assets.length; ++i){
            System.out.println(assets[i].getAssetName());
        }
    }
    @Test
    void loginVerification(){
        String username = "LeviStenton";
        String password = "test";
        User user = db.loginAttempt(username, password);
        System.out.println(user.getUserName() + " " + user.getAdmin());
    }
}
