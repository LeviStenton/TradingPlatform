import Database.AccountDetails;
import Network.ClientSocket;
import Database.Order;
import GUI.Login;

public class ClientStart {

    public static void main(String[] args){
        Login login = new Login();
        ClientSocket sock = new ClientSocket();



        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                login.setVisible(true);
            }
        });
    }

}
