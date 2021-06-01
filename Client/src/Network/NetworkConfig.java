package Network;

import java.io.*;

public class NetworkConfig {
    private static final String FILENAME = "config.txt";
    private static String IP = "";
    private static String PORT = "";

    public NetworkConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String[] readIP = reader.readLine().split(" ");
            PORT = readIP[readIP.length-1];
            String[] readPORT = reader.readLine().split(" ");
            IP = readPORT[readPORT.length-1];
        }
        catch (IOException | NullPointerException e) {
            updateFile();
        }
    }

    public void updateFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))){
            writer.write("port = " + PORT + "\n");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
