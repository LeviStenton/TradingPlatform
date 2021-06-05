package Network;

import Database.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSocket {
    /**
     * Server networking data
     */
    private static String HOSTNAME = "127.0.0.1";
    private static int PORT = 10000;
    private Socket sock;

    /**
     * Commands to issue to the server
     */
    //public static final String STORE = "Store";
    public static final String ORDER = "ORDER";
    public static final String LOGIN = "LOGIN";
    public static final String GETASSETS = "GETASSETS";
    public static final String GETORDERHISTORY = "GETORDERHISTORY";
    public static final String PASSWORD = "PASSWORD";
    public static final String CREATEACC = "CREATEACC";
    public static final String REMOVEACC = "REMOVEACC";
    public static final String ADMINPASS = "ADMINPASS";
    public static final String PROMOTE = "PROMOTE";
    public static final String REMOVEASSET = "REMOVEASSET";
    public static final String ADDASSET = "ADDASSET";
    public static final String CREDITS = "CREDITS";
    public static final String GETORGASSETS = "GETORGASSETS";
    public static final String GETALLUSERS = "GETALLUSERS";
    public static final String GETALLORDERS = "GETALLORDERS";
    public static final String GETALLORGASSETS = "GETALLORGASSETS";


    public ClientSocket(){
        NetworkConfig config = new NetworkConfig();
        System.out.println("IP: " + HOSTNAME + "\nPORT: " + PORT);
        HOSTNAME = config.getIP();
        PORT = config.getPORT();
        try {
            sock = new Socket(HOSTNAME, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendOrder(Order order){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(ORDER);
            objOutStream.writeObject(order);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User sendLogin(String login, String password){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(LOGIN);
            objOutStream.writeObject(login);
            objOutStream.writeObject(password);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return (User)objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Asset> getAssets(){
        List<Asset> assets = new ArrayList<Asset>();
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETASSETS);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){

                return (List<Asset>) objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return assets;
        }
    }

    public List<OrgAssets> getAllOrgAssets(){
        List<OrgAssets> assets = new ArrayList<OrgAssets>();
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETALLORGASSETS);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){

                return (List<OrgAssets>) objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return assets;
        }
    }

    public List<Order> getOrderHistory(){
        List<Order> assets = new ArrayList<Order>();
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETORDERHISTORY);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){

                return (List<Order>) objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return assets;
        }
    }

    public List<User> getAllUsers(){
        List<User> assets = new ArrayList<User>();
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETALLUSERS);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){

                return (List<User>) objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return assets;
        }
    }

    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<Order>();
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETALLORDERS);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){

                return (List<Order>) objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return orders;
        }
    }

    public boolean changePassword(String currentPass, String newPass, int userID){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(PASSWORD);
            objOutStream.writeObject(currentPass);
            objOutStream.writeObject(newPass);
            objOutStream.writeInt(userID);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return objInputStream.readBoolean();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean adminChangePassword(String username, String newPass){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(ADMINPASS);
            objOutStream.writeObject(username);
            objOutStream.writeObject(newPass);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return objInputStream.readBoolean();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createAccount(String username, String password){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(CREATEACC);
            objOutStream.writeObject(username);
            objOutStream.writeObject(password);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return objInputStream.readBoolean();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAccount(String username){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(REMOVEACC);
            objOutStream.writeObject(username);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return objInputStream.readBoolean();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean promoteAccount(String username, boolean admin){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(PROMOTE);
            objOutStream.writeObject(username);
            objOutStream.writeBoolean(admin);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return objInputStream.readBoolean();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addAsset(String assetName){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(ADDASSET);
            objOutStream.writeObject(assetName);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return objInputStream.readBoolean();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAsset(String assetName){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(REMOVEASSET);
            objOutStream.writeObject(assetName);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return objInputStream.readBoolean();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getOrgAssetQuantity(int orgID, int assetID){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETORGASSETS);
            objOutStream.writeInt(orgID);
            objOutStream.writeInt(assetID);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return objInputStream.readDouble();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int credits(String orgName){
        return 0;
    }
}
