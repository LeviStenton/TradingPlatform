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
    void LoginVerify() {
        DBSource source = new DBSource();
        ;
        assertTrue(source.loginAttempt("LeviStenton", "password1"));
    }

    @Test
    void CountAssets() {
        DBSource source = new DBSource();
        System.out.println(source.GetAssetCount());
        //assertTrue(source.GetAssetCount() > 0);
    }

    @Test
    void RunMarketPlaceLoopNormalCase() {
        Marketplace mk = new Marketplace();
        DBSource source = new DBSource();
        source.AddOrder(100, 1, 15, "B", 10, 1);
        source.AddOrder(101, 1, 15, "S", 10, 2);
        mk.GroupAssets();
    }

    @Test
    void RunMarketPlaceLoopNoCase() {
        Marketplace mk = new Marketplace();
        DBSource source = new DBSource();
        source.AddOrder(100, 1, 15, "B", 10, 1);
        source.AddOrder(101, 2, 15, "S", 10, 2);
        mk.GroupAssets();
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseMatch() {
        Marketplace mk = new Marketplace();
        DBSource source = new DBSource();
        source.AddOrder(100, 1, 20, "B", 20, 1);
        source.AddOrder(101, 1, 15, "S", 10, 2);
        mk.GroupAssets();
    }

    @Test
    void RunMarketPlaceLoopDifferentQuantityCaseNoMatch() {
        Marketplace mk = new Marketplace();
        DBSource source = new DBSource();
        source.AddOrder(100, 1, 13, "B", 20, 1);
        source.AddOrder(101, 1, 15, "S", 10, 2);
        mk.GroupAssets();
    }

    @Test
    void InsertAsset() {
        DBSource source = new DBSource();
        //source.InsertOrgAsset(1,1,10);
    }
}
