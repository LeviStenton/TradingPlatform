import GUI.Home;
import Network.ClientSocket;
import Orders.Order;

public class ClientStart {

    public static void main(String[] args){
        Home home = new Home();
        ClientSocket sock = new ClientSocket();

        Order order = new Order(1, 1, 1, "1", 1, 1);
        sock.sendOrder(order);


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                home.setVisible(true);
            }
        });
    }

}
