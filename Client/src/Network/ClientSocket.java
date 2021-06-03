package Network;

import Orders.Order;

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

//    public Object retrieve(String command){
//        try{
//            Socket socket = new Socket(HOSTNAME, PORT);
//            Object returnObj;
//            try (ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream())) {
//                objOutStream.writeObject(command);
//                objOutStream.flush();
//                try(ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream())){
//                    if(command == ORDER)
//                        return (Order)objInputStream.readObject();
//                    else
//                        return "Nothing to return";
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return e.toString();
//        }
//    }
}
