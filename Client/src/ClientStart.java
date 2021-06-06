import Database.DatabaseStorage;
import GUI.Login;
import Network.ClientSocket;

import java.io.IOException;

public class ClientStart extends Thread{

    /**
     * Starts the Client and its GUI up
     *
     * @param args the arguments taken by main()
     */
    public static void main(String[] args){
        Login login = new Login();
        ClientStart updateAssets = new ClientStart();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                login.setVisible(true);
            }
        });
        updateAssets.start();
    }

    /**
     * Manages threading for the client class
     */
    public void run() {
        ClientSocket socket;
        while (true){
            socket = new ClientSocket();
            DatabaseStorage.setAssetList(socket.getAssets());

            socket = new ClientSocket();
            DatabaseStorage.setOrderHistory(socket.getOrderHistory());

            socket = new ClientSocket();
            DatabaseStorage.setProfileList(socket.getAllUsers());

            socket = new ClientSocket();
            DatabaseStorage.setOrders(socket.getAllOrders());

            socket = new ClientSocket();
            DatabaseStorage.setOrgAssets(socket.getAllOrgAssets());

            socket = new ClientSocket();
            DatabaseStorage.setOrgDetails(socket.getAllOrgDetails());

            GUI.Home.UpdateHome();

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
