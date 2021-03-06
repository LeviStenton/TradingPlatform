package Network;

import Database.DBSource;
import Database.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerConnection {
    private static int PORT;
    private static final int SOCKET_TIMEOUT = 100;

    /**
     * Commands to issue to the server
     */
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
    public static final String GETALLORGASSETS = "GETALLORGASSETS";
    public static final String GETALLORGDETAILS = "GETALLORGDETAILS";
    public static final String REMOVECREDITS = "REMOVECREDITS";
    public static final String REMOVEORGASSET = "REMOVEORGASSET";
    public static final String GETORGDETAILS = "GETORGDETAILS";

    // Database connection
    DBSource db;

    private final AtomicBoolean running = new AtomicBoolean(true);

    /**
     * Sets the PORT of the server
     */
    public ServerConnection() {
        NetworkConfig config = new NetworkConfig();
        db = new DBSource();

        PORT = config.getPORT();
    }

    /**
     * Handles the connections received from ServerSocket and
     * passes them to the DBSource to handle their requests.
     *
     * @param socket The socket used to communicate with the currently connected client
     */
    private void handleConnection(Socket socket) throws Exception {
        try (ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream())) {
            String command = (String) objInStream.readObject();
            switch (command){
                case ORDER:
                    Order order = (Order) objInStream.readObject();
                    db.AddOrder(order);
                    break;
                case LOGIN:
                    String username = (String) objInStream.readObject();
                    String password = (String) objInStream.readObject();
                    System.out.println("Attempted login with: " +  username + " " + password);
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.loginAttempt(username, password));
                    } break;
                case GETASSETS:
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.GetAllAssets());
                    } break;
                case REMOVECREDITS:
                    double amount = objInStream.readDouble();
                    int orgID = objInStream.readInt();
                    String operator = (String) objInStream.readObject();
                    db.ChangeOrgCredits(amount,orgID,operator);
                     break;
                case GETORDERHISTORY:
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.GetOrderHistory());
                    } break;
                case GETALLUSERS:
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.GetAllUsers());
                    } break;
                case GETALLORDERS:
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.GetAllOrders());
                    } break;
                case GETALLORGASSETS:
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.GetAllOrgAssets());
                    } break;
                case GETALLORGDETAILS:
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.GetAllOrgDetails());
                    } break;
                case PASSWORD:
                    String currentPass = (String) objInStream.readObject();
                    String newPass = (String) objInStream.readObject();
                    int userID = objInStream.readInt();
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeBoolean(db.ChangeUserPassword(currentPass, newPass, userID));
                    } break;
                case CREATEACC:
                    boolean created = db.CreateAccount((String) objInStream.readObject(), (String) objInStream.readObject(), 0);
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeBoolean(created);
                    } break;
                case ADMINPASS:
                    String aUsername = (String) objInStream.readObject();
                    String aNewPass = (String) objInStream.readObject();
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeBoolean(db.AdminChangeUserPassword(aUsername, aNewPass));
                    } break;
                case PROMOTE:
                    String pUsername = (String) objInStream.readObject();
                    boolean pAdmin = objInStream.readBoolean();
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeBoolean(db.PromoteAccount(pUsername, pAdmin));
                    } break;
                case REMOVEACC:
                    String rUsername = (String) objInStream.readObject();
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeBoolean(db.RemoveAccount(rUsername));
                    } break;
                case ADDASSET:
                    String aAssetName = (String) objInStream.readObject();
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeBoolean(db.AddAsset(aAssetName));
                    } break;
                case REMOVEASSET:
                    String rAssetName = (String) objInStream.readObject();
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeBoolean(db.RemoveAsset(rAssetName));
                    } break;
                case GETORGASSETS:
                    int orgID2 = objInStream.readInt();
                    int assetID = objInStream.readInt();
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeDouble(db.GetOrgAssetQuantity(orgID2, assetID));
                    } break;
                case REMOVEORGASSET:
                    int assetID2 = objInStream.readInt();
                    double amount2 = objInStream.readDouble();
                    int orgID3 = objInStream.readInt();
                    String rOperator = (String) objInStream.readObject();
                    db.InsertOrgAsset(orgID3,assetID2,amount2,rOperator);
                    break;
                case GETORGDETAILS:
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.GetAllOrgs());
                    } break;
            }
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }



    /**
     * Returns the port the server is configured to use
     *
     * @return The port number
     */
    public static int getPort() {
        return PORT;
    }

    /**
     * Starts the server running on the default port
     */
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);
            // The server is no longer running
            while (running.get()) {
                try {
                    Socket socket = serverSocket.accept();
                    handleConnection(socket);
                } catch (SocketTimeoutException ignored) {
                    // Do nothing. A timeout is normal- we just want the socket to
                    // occasionally timeout so we can check if the server is still running
                } catch (Exception e) {
                    // We will report other exceptions by printing the stack trace, but we
                    // will not shut down the server. A exception can happen due to a
                    // client malfunction (or malicious client)
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // If we get an error starting up, show an error dialog then exit
            e.printStackTrace();
            System.exit(1);
        }

        // Close down the server
        System.exit(0);
    }

    /**
     * Requests the server to shut down
     */
    public void shutdown() {
        // Shut the server down
        running.set(false);
    }
}
