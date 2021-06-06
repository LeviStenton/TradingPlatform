import Database.Asset;
import Database.Order;
import Database.DatabaseStorage;
import GUI.Login;
import Network.ClientSocket;

import java.util.ArrayList;
import java.util.List;

public class ClientStart extends Thread{
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

//            for(Asset asset : DatabaseStorage.getAssetList()){
//                System.out.println(asset.getAssetName());
//            }


//            for(Order order : DatabaseStorage.getOrderHistory()){
//                System.out.println(order.getPrice());
//            }
//            System.out.println("");
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("This code is running in a thread");
    }


}
