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
}
