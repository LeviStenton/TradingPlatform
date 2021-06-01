package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerConnection {
    private static int PORT = 10000;
    private static final int SOCKET_TIMEOUT = 100;
    private AtomicBoolean running = new AtomicBoolean(true);

    public ServerConnection() {
        NetworkConfig config = new NetworkConfig();
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
            // Write what to do with the stored objects here
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
