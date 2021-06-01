package Network;

import java.io.*;

public class NetworkConfig {
    private static final String FILENAME = "config.txt";
    private static String IP = "127.0.0.1";
    private static int PORT = 10000;

    public String getIP() { return IP; }
    public int getPORT() { return  PORT; }

    public NetworkConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String[] readIP = reader.readLine().split(" ");
            PORT = Integer.parseInt(readIP[readIP.length-1]);
            String[] readPORT = reader.readLine().split(" ");
            IP = readPORT[readPORT.length-1];
        }
        catch (IOException | NullPointerException e) {
            updateFile();
        }
        catch (NumberFormatException nE){
            System.out.println("Port or IP is an invalid format. Must be a number.");
        }
    }

    public void updateFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))){
            writer.write("port = " + PORT + "\n");
            writer.write("IP = " + IP + "\n");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
