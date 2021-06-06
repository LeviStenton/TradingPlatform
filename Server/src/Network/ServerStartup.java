package Network;

import Database.DBInterface;
import Database.DBSource;
import Database.Marketplace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Console;
import java.io.IOException;

public class ServerStartup extends Thread{
    public static void main(String[] args) throws IOException {
        ServerConnection server = new ServerConnection();
        ServerStartup marketplaceThread = new ServerStartup();

        SwingUtilities.invokeLater(() -> createAndShowGUI(server));
        try {
            marketplaceThread.start();

            server.start();


        } catch (IOException e) {
            // In the case of an exception, show an error message and terminate
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        null, e.getMessage(),
                        "Error starting server", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            });
        }
    }

    public void run() {
        System.out.println("Working1");
        DBSource source = new DBSource();
        Marketplace mk = new Marketplace(source);
        while(true){
            System.out.println("Working");
            mk.GroupAssets();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createAndShowGUI(ServerConnection server) {
        JDialog dialog = new JDialog();
        dialog.setTitle("src.Network server for Address Book");
        JButton shutdownButton = new JButton("Shut down server");
        // This button will simply close the dialog. CLosing the dialog
        // will shut down the server
        shutdownButton.addActionListener(e -> dialog.dispose());

        // When the dialog is closed, shut down the server
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                server.shutdown();
            }
        });

        // Create a label to show server info
        JLabel serverLabel = new JLabel("Server running on port " + ServerConnection.getPort());

        // Add the button and label to the dialog
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(shutdownButton, BorderLayout.SOUTH);
        dialog.getContentPane().add(serverLabel, BorderLayout.NORTH);
        dialog.pack();

        // Centre the dialog on the screen
        dialog.setLocationRelativeTo(null);

        // Show the dialog
        dialog.setVisible(true);
    }
}
