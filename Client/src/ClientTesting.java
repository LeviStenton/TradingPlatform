import Database.AccountDetails;
import Database.Order;
import Network.ClientSocket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientTesting {
    ClientSocket sock = new ClientSocket();

    @Test
    void addingOrder(){
        Order order = new Order(1, 1, 1, "1", 1, 1);
        sock.sendOrder(order);
    }
    @Test
    void loginVerification(){
        String username = "LeviStenton";
        String password = "test";

        assertTrue(sock.sendLogin(username, password));
    }
}
