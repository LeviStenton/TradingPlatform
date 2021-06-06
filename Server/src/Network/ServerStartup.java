package Network;

import Database.Marketplace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ServerStartup extends Thread{

    /**
     * Starts the server up
     *
     * @param args the arguments taken by main()
     * @throws IOException if anything main runs breaks, throw this
     */
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

    /**
     * Runs the threading for the server
     */
    public void run() {
        System.out.println("Working1");
        Marketplace mk = new Marketplace();
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

    /**
     * Creates and starts the GUI for the server
     *
     * @param server the server that the GUI displays the details of
     */
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
