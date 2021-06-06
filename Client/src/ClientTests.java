import Database.Order;
import Database.User;
import Network.ClientSocket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BLACK BOX TESTS --------------------------------------------------------------------------
 * Black box testing is when you write tests to ensure your method performs
 * exactly as specified in it's specification (eg javadocs)
 */
class BlackBoxTests {
    ClientSocket sock = new ClientSocket();

    @Test
    void buyOrder() {

    }

    @Test
    void sellOrder() {

    }

    @Test
    void accountCreation() {

    }

    @Test
    void removeAccount() {
        String username = "Eggs";
        assertTrue(sock.removeAccount(username));
    }

    @Test
    void loginVerification() {
        String username = "LeviStenton";
        String password = "test";
        User user = sock.sendLogin(username, password);
        System.out.println(user.getUserName() + " " + user.getAdmin());
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
    void orderValidation() {

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
    void editORder() {

    }

    @Test
    void configReading() {

    }
}

/**
 * GLASS/WHITE BOX TESTS ------------------------------------------------------------------
 * Glass/White box testing is when you write tests based on looking at the actual
 * code for the function to ensure each portion is called correctly
 */
class WhiteBoxTests {
    ClientSocket sock = new ClientSocket();

    @Test
    void buyOrder(){

    }

    @Test
    void sellOrder(){

    }

    @Test
    void accountCreation(){

    }

    @Test
    void removeAccount(){
        String username = "Eggs";
        assertTrue(sock.removeAccount(username));
    }

    @Test
    void loginVerification(){
        String username = "LeviStenton";
        String password = "test";
        User user = sock.sendLogin(username, password);
        System.out.println(user.getUserName() + " " + user.getAdmin());
    }

    @Test
    void addAsset(){
        String assetName = "test";
        assertTrue(sock.addAsset(assetName));
    }

    @Test
    void removeAsset(){
        String assetName = "test";
        assertTrue(sock.removeAsset(assetName));
    }

    @Test
    void orderValidation(){

    }

    @Test
    void accountPromotion(){
        String username = "LeviStenton";
        assertTrue(sock.promoteAccount(username, true));
    }

    @Test
    void changePassword(){
        int userID = 1;
        String currentPass = "test";
        String newPass = "test123";
        assertTrue(sock.changePassword(currentPass, newPass, userID));
    }

    @Test
    void adminChangePassword(){
        String username = "LeviStenton";
        String newPass = "test";
        assertTrue(sock.adminChangePassword(username, newPass));
    }

    @Test
    void addingOrder(){
        Order order = new Order(1,1, 1, "1", 1, 1);
        sock.sendOrder(order);
    }

    @Test
    void removingOrder(){
        Order order = new Order(1,1, 1, "1", 1, 1);
        sock.sendOrder(order);
    }

    @Test
    void editORder(){

    }

    @Test
    void configReading(){

    }
}
