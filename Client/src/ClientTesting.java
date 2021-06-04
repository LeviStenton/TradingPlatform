import Database.Profile;
import Database.Asset;
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
        Profile user = sock.sendLogin(username, password);
        System.out.println(user.getUserName() + " " + user.getAdmin());
    }

    @Test
    void getAllAssets(){
        Asset[] assets = sock.getAssets();
        for(int i = 0; i < assets.length; ++i){
            System.out.println(assets[i].getAssetName());
        }
    }
}
