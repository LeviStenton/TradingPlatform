package Network;

import Database.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerConnection<handleConnection> {
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
<<<<<<< HEAD
    public static final String GETALLORGDETAILS = "GETALLORGDETAILS";
    public static final String REMOVECREDITS = "REMOVECREDITS";
    public static final String REMOVEORGASSET = "REMOVEORGASSET";


=======
    public static final String GETORGDETAILS = "GETORGDETAILS";
>>>>>>> Client_GUI

    // Database connection
    DBSource db;

    private AtomicBoolean running = new AtomicBoolean(true);

    public ServerConnection() {
        NetworkConfig config = new NetworkConfig();
        db = new DBSource();

        PORT = config.getPORT();
    }

//    public void run() {
//        System.out.println("Working1");
//        DBSource source = new DBSource();
//        Marketplace mk = new Marketplace(source);
//        while(true){
//            System.out.println("Working");
//            mk.GroupAssets();
//            try {
//                sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * Handles the connection received from ServerSocket
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
                    Double amount = (Double) objInStream.readObject();
                    int orgID = (int) objInStream.readObject();
                    db.ChangeOrgCredits(amount,orgID,"-");
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
<<<<<<< HEAD
                case REMOVEORGASSET:
                    int assetID2 = (int) objInStream.readObject();
                    Double amount2 = (Double) objInStream.readObject();
                    int orgID3 = (int) objInStream.readObject();
                    db.InsertOrgAsset(orgID3,assetID2,amount2,"-");
                    break;
=======
                case GETORGDETAILS:
                    try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                        outStream.writeObject(db.GetAllOrgs());
                    } break;
>>>>>>> Client_GUI
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
            for (; ; ) {
                if (!running.get()) {
                    // The server is no longer running
                    break;
                }
                try {
                    Socket socket = serverSocket.accept();
                    //handleConnection thread = new handleConnection();
                    //thread.start();
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
