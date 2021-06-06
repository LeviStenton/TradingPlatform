import Database.DatabaseStorage;
import Database.Order;
import Database.User;
import Network.ClientSocket;
import Network.NetworkConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BLACK BOX TESTS --------------------------------------------------------------------------
 * Black box testing is when you write tests to ensure your method performs
 * exactly as specified in it's specification (eg javadocs)
 */
class BlackBoxTests {
    ClientSocket sock = new ClientSocket();

    @Test
    void buyOrder() {
        sock.sendOrder(new Order(1, 10, "B", 1, 1 ));
    }

    @Test
    void sellOrder() {
        sock.sendOrder(new Order(1, 10, "S", 1, 1 ));
    }

    @Test
    void accountCreation() {
        assertTrue(sock.createAccount("Levi", "test"));
    }

    @Test
    void removeAccount() {
        assertTrue(sock.removeAccount("Eggs"));
    }

    @Test
    void loginVerification() {
        String username = "LeviStenton";
        String password = "test123";
        assertNotNull(sock.sendLogin(username, password));
    }

    @Test
    void addAsset() {
        String assetName = "test";
        assertTrue(sock.addAsset(assetName));
    }

    @Test
    void removeAsset() {
        String assetName = "test";
        assertTrue(sock.removeAsset(assetName));
    }

    @Test
    void accountPromotion() {
        String username = "LeviStenton";
        assertTrue(sock.promoteAccount(username, true));
    }

    @Test
    void changePassword() {
        int userID = 1;
        String currentPass = "test";
        String newPass = "test123";
        assertTrue(sock.changePassword(currentPass, newPass, userID));
    }

    @Test
    void adminChangePassword() {
        String username = "LeviStenton";
        String newPass = "test";
        assertTrue(sock.adminChangePassword(username, newPass));
    }

    @Test
    void addingOrder() {
        Order order = new Order(1, 1, 1, "1", 1, 1);
        sock.sendOrder(order);
    }

    @Test
    void removingOrder() {
        Order order = new Order(1, 1, 1, "1", 1, 1);
        sock.sendOrder(order);
    }

    @Test
    void configReading() {
        NetworkConfig config = new NetworkConfig();
        assertEquals("127.0.0.1", config.getIP());
        assertEquals(10000, config.getPORT());
    }
}

