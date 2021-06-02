import GUI.Home;
import Network.ClientSocket;

public class ClientStart {

    /**
     * Commands to issue to the server
     */
    public static final String STORE = "Store";
    public static final String RETRIEVE = "Retrieve";

    public static void main(String[] args){
        Home home = new Home();
        ClientSocket sock = new ClientSocket();
        sock.retrieve(RETRIEVE);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                home.setVisible(true);
            }
        });
    }

}
