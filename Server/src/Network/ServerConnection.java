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

public class ServerConnection {
    private static int PORT;
    private static final int SOCKET_TIMEOUT = 100;

    /**
     * Commands to issue to the server
     */
    public static final String ORDER = "ORDER";
    public static final String LOGIN = "LOGIN";
    public static final String GETASSETS = "GETASSETS";
    public static final String PASSWORD = "PASSWORD";

    // Database connection
    DBSource db;

    private AtomicBoolean running = new AtomicBoolean(true);

    public ServerConnection() {
        NetworkConfig config = new NetworkConfig();
        db = new DBSource();

        PORT = config.getPORT();
    }

    /**
     * Handles the connection received from ServerSocket
     *
     * @param socket The socket used to communicate with the currently connected client
     */
    private void handleConnection(Socket socket) throws Exception {
        try (ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream())) {
            String command = (String) objInStream.readObject();
            if(command.equals(ORDER)){
                Order order = (Order) objInStream.readObject();
                db.AddOrder(order);
            }
            else if(command.equals(LOGIN)){
                String username = (String) objInStream.readObject();
                String password = (String) objInStream.readObject();
                System.out.println("Attempted login with: " +  username + " " + password);
                try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                    outStream.writeObject(db.loginAttempt(username, password));
                }
            }
            else if(command.equals(GETASSETS)){
                try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
                    outStream.writeObject(db.GetAllAssets());
                }
            }
//            else if(command.equals(RETRIEVE))
//                try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())){
//                    // send information to the client through here
//                }
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
