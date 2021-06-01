package Server;

import java.io.*;
import java.sql.Connection;

public class NetworkConfig {
    private static final String FILENAME = "config.txt";
    private int PORT = 10000;

    /**
     * Returns the port number specified from the configuration file
     *
     * @return The port number
     */
    public int getPORT(){ return this.PORT; }

    /**
     * Constructor that reads from the configuration file
     * and sets the PORT number from what it reads.
     */
    public NetworkConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String[] read = reader.readLine().split(" ");
            PORT = Integer.parseInt(read[read.length-1]);
        }
        catch (IOException | NullPointerException e) {
            updateFile("port = " + PORT + "\n");
        }
        catch (NumberFormatException nE){
            System.out.println("Port is an invalid format. Must be an integer.");
        }
    }

    /**
     * Updates the configuration file
     * @param line The string that is written to the configuration file
     */
    public void updateFile(String line){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))){
            writer.write(line);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
