package Network;

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
    public static final String STORE = "Store";
    public static final String RETRIEVE = "Retrieve";

    public ClientSocket(){
        NetworkConfig config = new NetworkConfig();
        HOSTNAME = config.getIP();
        PORT = config.getPORT();
    }

    public void send(String command){
        try {
            Socket socket = new Socket(HOSTNAME, PORT);
            try (ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream())) {
                objOutStream.writeObject(command);
                // thing to send
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieve(String command){
        try{
            Socket socket = new Socket(HOSTNAME, PORT);
            try (ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream())) {
                objOutStream.writeObject(command);
                objOutStream.flush();
                try(ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream())){
                    // thing to retrieve
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
