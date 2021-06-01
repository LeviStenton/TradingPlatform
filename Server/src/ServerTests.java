import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

public class ServerTests {

    @Test
    void CreateAccount(){
        DBSource source = new DBSource();
        source.CreateAccount("LeviStenton", "password1", 123456);
    }

    @Test
    void LoginVerify(){
        DBSource source = new DBSource();        ;
        assertTrue(source.loginAttempt("Alexander", "1000:2bed40233126488f9226952f04b413a2:540229735fbf3a9885e1469340f3e2d6077cdff016c3322027cb3073dd14efedc85ae7dcbb8bb4abb4a5736fca4c2232d37b99a76faa2c8d0381299366af8b0b"));
    }

    @Test
    void CountAssets(){
        DBSource source = new DBSource();
        System.out.println(source.GetAssetCount());
        //assertTrue(source.GetAssetCount() > 0);
    }

    @Test
    void RunMarketPlaceLoopNormalCase(){
        DBSource source = new DBSource();
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
    }

    @Test
    void RunMarketPlaceLoopNoCase(){
        DBSource source = new DBSource();
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,15,"B",10,1);
        source.AddOrder(101,2,15,"S",10,2);
        mk.GroupAssets();
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseMatch(){
        DBSource source = new DBSource();
        Marketplace mk = new Marketplace(source);
        source.AddOrder(100,1,20,"B",20,1);
        source.AddOrder(101,1,15,"S",10,2);
        mk.GroupAssets();
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseNoMatch(){
        DBSource source = new DBSource();
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
}
