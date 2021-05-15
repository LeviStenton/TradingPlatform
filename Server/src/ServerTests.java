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
        assertTrue(source.loginAttempt("LeviStenton", "password1"));
    }

    @Test
    void CountAssets(){
        DBSource source = new DBSource();
        System.out.println(source.GetAssetCount());
        assertTrue(source.GetAssetCount() > 0);
    }

    @Test
    void RunMarketPlaceLoop(){
        Marketplace mk = new Marketplace();
        mk.GroupAssets();
    }
}
