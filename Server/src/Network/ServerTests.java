package Network;

import src.Database.DBSource;
import src.Database.Marketplace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTests {

    @Test
    void CreateAccount() {
        DBSource source = new DBSource();
        source.CreateAccount("LeviStenton", "password1", 123456);
    }

    @Test
<<<<<<< HEAD:Server/src/Network/ServerTests.java
    void LoginVerify() {
        DBSource source = new DBSource();
        ;
        assertTrue(source.loginAttempt("LeviStenton", "password1"));
=======
    void LoginVerify(){
        DBSource source = new DBSource();        ;
        assertTrue(source.loginAttempt("Alexander", "1000:2bed40233126488f9226952f04b413a2:540229735fbf3a9885e1469340f3e2d6077cdff016c3322027cb3073dd14efedc85ae7dcbb8bb4abb4a5736fca4c2232d37b99a76faa2c8d0381299366af8b0b"));
>>>>>>> Marketplace_Loop:Server/src/ServerTests.java
    }

    @Test
    void CountAssets() {
        DBSource source = new DBSource();
        System.out.println(source.GetAssetCount());
        //assertTrue(source.GetAssetCount() > 0);
    }

    @Test
<<<<<<< HEAD:Server/src/Network/ServerTests.java
    void RunMarketPlaceLoopNormalCase() {
        Marketplace mk = new Marketplace();
        DBSource source = new DBSource();
        source.AddOrder(100, 1, 15, "B", 10, 1);
        source.AddOrder(101, 1, 15, "S", 10, 2);
=======
    void RunMarketPlaceLoopNormalCase(){
        DBSource source = new DBSource();
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,1,15,"S",10,2);
>>>>>>> Marketplace_Loop:Server/src/ServerTests.java
        mk.GroupAssets();
    }

    @Test
<<<<<<< HEAD:Server/src/Network/ServerTests.java
    void RunMarketPlaceLoopNoCase() {
        Marketplace mk = new Marketplace();
        DBSource source = new DBSource();
        source.AddOrder(100, 1, 15, "B", 10, 1);
        source.AddOrder(101, 2, 15, "S", 10, 2);
=======
    void RunMarketPlaceLoopNoCase(){
        DBSource source = new DBSource();
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,2,15,"S",10,2);
>>>>>>> Marketplace_Loop:Server/src/ServerTests.java
        mk.GroupAssets();
    }

    @Test
<<<<<<< HEAD:Server/src/Network/ServerTests.java
    void RunMarketPlaceLoopDifferentQuantityCaseMatch() {
        Marketplace mk = new Marketplace();
        DBSource source = new DBSource();
        source.AddOrder(100, 1, 20, "B", 20, 1);
        source.AddOrder(101, 1, 15, "S", 10, 2);
=======
    void RunMarketPlaceLoopDifferentQuantityCaseMatch(){
        DBSource source = new DBSource();
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,20,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
>>>>>>> Marketplace_Loop:Server/src/ServerTests.java
        mk.GroupAssets();
    }

    @Test
<<<<<<< HEAD:Server/src/Network/ServerTests.java
    void RunMarketPlaceLoopDifferentQuantityCaseNoMatch() {
        Marketplace mk = new Marketplace();
        DBSource source = new DBSource();
        source.AddOrder(100, 1, 13, "B", 20, 1);
        source.AddOrder(101, 1, 15, "S", 10, 2);
=======
    void RunMarketPlaceLoopDifferentQuantityCaseNoMatch(){
        DBSource source = new DBSource();
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,13,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
>>>>>>> Marketplace_Loop:Server/src/ServerTests.java
        mk.GroupAssets();
    }

    @Test
    void InsertAsset() {
        DBSource source = new DBSource();
        //source.InsertOrgAsset(1,1,10);
    }
}
