package Network;

import Database.User;
import Database.Asset;
import Database.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket {
    /**
     * Server networking data
     */
    private static String HOSTNAME = "127.0.0.1";
    private static int PORT = 10000;

    /**
     * Commands to issue to the server
     */
    //public static final String STORE = "Store";
    public static final String ORDER = "ORDER";
    public static final String LOGIN = "LOGIN";
    public static final String GETASSETS = "GETASSETS";
    public static final String PASSWORD = "PASSWORD";

    public ClientSocket(){
        NetworkConfig config = new NetworkConfig();
        System.out.println("IP: " + HOSTNAME + "\nPORT: " + PORT);
        HOSTNAME = config.getIP();
        PORT = config.getPORT();
    }

    public void sendOrder(Order order){
        try {
            Socket socket = new Socket(HOSTNAME, PORT);
            try (ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream())) {
                objOutStream.writeObject(ORDER);
                objOutStream.writeObject(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User sendLogin(String login, String password){
        try {
            Socket socket = new Socket(HOSTNAME, PORT);
            try (ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream())) {
                objOutStream.writeObject(LOGIN);
                objOutStream.writeObject(login);
                objOutStream.writeObject(password);
                objOutStream.flush();
                try(ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream())){
                    return (User)objInputStream.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Asset[] getAssets(){
        Asset[] assets;
        try {
            Socket socket = new Socket(HOSTNAME, PORT);
            try (ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream())) {
                objOutStream.writeObject(GETASSETS);
                objOutStream.flush();
                try(ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream())){
                    return (Asset[]) objInputStream.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            assets = new Asset[0];
            return assets;
        }
    }

    public boolean changePassword(String currentPass, String newPass, int userID){
        try {
            Socket socket = new Socket(HOSTNAME, PORT);
            try (ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream())) {
                objOutStream.writeObject(PASSWORD);
                objOutStream.writeObject(currentPass);
                objOutStream.writeObject(newPass);
                objOutStream.writeInt(userID);
                objOutStream.flush();
                try(ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream())){
                    return objInputStream.readBoolean();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
