package Database;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.sql.Array;
import java.sql.SQLException;

public class ServerTests {
    DBTestSource source = new DBTestSource();
    @Before
    public void init() {

        this.source = new DBTestSource();

        //source.CreateAccount("LeviStenton","password1",2);
    }

    @Test
    void CreateAccount(){
        String userName = "LeviStenton";
        String password = "password1";
        String getUserName;
        source.CreateAccount(userName,password,1);
        for(AccountDetails account: source.getAccountDetailsList()){
            getUserName = account.getUserName();
            assertTrue(getUserName.equals(userName));
            return;

        }
    }

    @Test
    void LoginVerifyMatch(){
        source.CreateAccount("Alexander","123",1);
        assertTrue(source.loginAttempt("Alexander", "123"));
    }

    @Test
    void LoginVerifyWrongUserName(){
        source.CreateAccount("Alexander","123",1);
        assertFalse(source.loginAttempt("alex", "123"));
    }

    @Test
    void LoginVerifyWrongPassword(){
        source.CreateAccount("Alexander","123",1);
        assertFalse(source.loginAttempt("Alexander", "321"));
    }

    @Test
    void LoginVerifyNoAccounts(){
        assertFalse(source.loginAttempt("Alexander", "321"));
    }

    @Test
    void RunMarketPlaceLoopNormalCase(){
        Marketplace mk = new Marketplace(source);
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(0,"1");

        source.AddNewAsset("hub");

        source.CreateAccount("Alex","123",1);
        source.CreateAccount("Levi","123",2);

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
        source.InsertNewOrgIntoOrgDetails(150,"1");

        source.AddNewAsset("hub");
        source.AddNewAsset("pc");


        source.CreateAccount("Alex","123",1);
        source.CreateAccount("Levi","123",2);

        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,2,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){

            assertEquals(0,org.getQuantity(), "Assets were add added to org");

        }
        for(OrgDetails org: source.orgDetailsList){

            assertEquals(150,org.getCredits(), "Credits were added to org");

        }
        assertTrue(source.orderList.size() == 2, "Orders were removed from orderList");
        assertTrue(source.orderHistoryList.size() == 0, "Orders were added to orderHistoryList");
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseMatch(){
        Marketplace mk = new Marketplace(source);
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(0,"2");

        source.AddNewAsset("hub");
        source.AddNewAsset("pc");


        source.CreateAccount("Alex","123",1);
        source.CreateAccount("Levi","123",2);

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
            if(order.getOrderType() == "B"){
                assertEquals(10,order.getQuantity(), "Buy order has wrong q");
            }
            if(order.getOrderType() == "S"){
                assertEquals(10,order.getQuantity(), "Sell order has wrong q");
            }
        }
        assertEquals(2,source.orderHistoryList.size(), "Sell order should have two orders");
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseNoMatch(){
        Marketplace mk = new Marketplace(source);
        source.InsertNewOrgIntoOrgDetails(150,"1");
        source.InsertNewOrgIntoOrgDetails(150,"2");

        source.AddNewAsset("hub");


        source.CreateAccount("Alex","123",1);
        source.CreateAccount("Levi","123",2);

        source.AddOrder(100,1,13,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
        for(OrgAssets org: source.orgAssetsList){

            assertEquals(0,org.getQuantity(), "Assets were add added to org");

        }
        for(OrgDetails org: source.orgDetailsList){

            assertEquals(150,org.getCredits(), "Credits were added to org");

        }
        assertTrue(source.orderList.size() == 2, "Orders were removed from orderList");
        assertTrue(source.orderHistoryList.size() == 0, "Orders were added to orderHistoryList");
    }

    @Test
    void assetCount(){
        source.AddNewAsset("Test");
        int[] result = new int[1];
        result[0] = 1;
        assertEquals(result[0], source.GetAssetCount().get(0));
    }

}
