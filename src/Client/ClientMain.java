package Client;

import Client.Controller.AuthController;
import Client.ClientConnection;
import Client.View.LoginFrame;

import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientConnection conn = new ClientConnection("localhost", 1234); // port server
            AuthController authController = new AuthController(conn);
            new LoginFrame(authController).setVisible(true);
        });
    }
}
