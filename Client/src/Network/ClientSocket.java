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
    public static final String GETORGASSETS = "GETORGASSETS";
    public static final String GETALLUSERS = "GETALLUSERS";
    public static final String GETALLORDERS = "GETALLORDERS";
    public static final String GETALLORGDETAILS = "GETALLORGDETAILS";
    public static final String GETALLORGASSETS = "GETALLORGASSETS";
    public static final String REMOVECREDITS = "REMOVECREDITS";
    public static final String REMOVEORGASSET = "REMOVEORGASSET";
    public static final String GETORGDETAILS = "GETORGDETAILS";

    /**
     * The constructor for the client's networking
     */
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

    /**
     * Remove an asset from an organization
     *
     * @param assetID the ID of the asset
     * @param amount the quantity to remove
     * @param user the user removing the asset
     * @param operator +, -, or = the sum of assets
     */
    public void removeOrgAsset(int assetID,double amount, User user, String operator){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(REMOVEORGASSET);
            objOutStream.writeInt(assetID);
            objOutStream.writeDouble(amount);
            objOutStream.writeInt(user.getOrgID());
            objOutStream.writeObject(operator);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a number of credits from an organization a user belongs to
     *
     * @param amount the amount to remove
     * @param user the user to remove credits from their organization
     * @param operator +, -, or = the sum of assets
     */
    public void removeCredits(double amount, User user, String operator){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(REMOVECREDITS);
            objOutStream.writeDouble(amount);
            objOutStream.writeInt(user.getOrgID());
            objOutStream.writeObject(operator);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send an order to the database
     *
     * @param order the order to be sent
     */
    public void sendOrder(Order order){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(ORDER);
            objOutStream.writeObject(order);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempt a login by a user using text fields
     *
     * @param login the username of the user
     * @param password the password of the user
     * @return the user that the details matched to, if any
     */
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

    /**
     * Gets all Asset objects and their details from the database
     *
     * @return the list of Assets
     */
    public List<Asset> getAssets(){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETASSETS);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return (List<Asset>) objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all the Assets belonging to an organization
     *
     * @return the list of Assets
     */
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

    /**
     * Get a list of all organizations as OrgDetails objects from the database
     *
     * @return the list of organizations
     */
    public List<OrgDetails> getAllOrgDetails(){
        List<OrgDetails> assets = new ArrayList<OrgDetails>();
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETALLORGDETAILS);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){

                return (List<OrgDetails>) objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return assets;
        }
    }

    /**
     * Get a list of the history of orders from the database
     *
     * @return the history of orders
     */
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

    /**
     * Gets a list of all users in the database
     *
     * @return the list of users
     */
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

    /**
     * Gets a list of all order currently trading from the database
     *
     * @return the list of trading orders
     */
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

    /**
     * Changes the password from the user's perspective
     *
     * @param currentPass the user's current password
     * @param newPass the user's new password
     * @param userID the user's ID number
     * @return whether changing the password was successful or not
     */
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

    /**
     * Changes a user's password from the admin control panel
     *
     * @param username the username of the user
     * @param newPass their new password
     * @return whether changing the password was successful or not
     */
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

    /**
     * Creating a new user account and write it to the database
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return whether creating the account was successful or not
     */
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

    /**
     * Remove a user's account from the database
     *
     * @param username the username of the user to remove
     * @return whether removing the account was successful or not
     */
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

    /**
     * Promote a user to an admin or demote them to a user
     *
     * @param username the username of the user
     * @param admin whether the user is admin or not
     * @return whether promoting/demoting the user was successful or not
     */
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

    /**
     * Add a new asset to the database
     *
     * @param assetName the name of the asset to add
     * @return whether adding the asset was successful or not
     */
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

    /**
     * Remove an asset from the database
     *
     * @param assetName the name of the asset to remove
     * @return
     */
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

    /**
     * Get the quantity of an asset that belong to an organization
     *
     * @param orgID the ID number of the organization
     * @param assetID the ID number of the asset
     * @return the quantity of the asset
     */
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

    /**
     * Get a list of all organization in the database as OrgDetails objects
     *
     * @return a list of OrgDetails
     */
    public List<OrgDetails> getAllOrgs(){
        try (ObjectOutputStream objOutStream = new ObjectOutputStream(sock.getOutputStream())) {
            objOutStream.writeObject(GETORGDETAILS);
            objOutStream.flush();
            try(ObjectInputStream objInputStream = new ObjectInputStream(sock.getInputStream())){
                return (List<OrgDetails>) objInputStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
