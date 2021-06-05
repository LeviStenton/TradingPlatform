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
        while (true){

            ClientSocket socket = new ClientSocket();
            DatabaseStorage.setAssetList(socket.getAssets());
            socket = new ClientSocket();
            DatabaseStorage.setOrderHistory(socket.getOrderHistory());

//            for(Asset asset : DatabaseStorage.getAssetList()){
//                System.out.println(asset.getAssetName());
//            }
            for(Order order : DatabaseStorage.getOrderHistory()){
                System.out.println(order.getAssetID());
            }
            System.out.println("");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("This code is running in a thread");
    }


}
