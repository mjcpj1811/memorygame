package Client;

import Client.Controller.HistoryController;
import Client.View.HistoryFrame;

import javax.swing.*;

public class HistoryMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientConnection conn = new ClientConnection("localhost", 1234);
            HistoryController historyController = new HistoryController(conn);
            new HistoryFrame(historyController).setVisible(true);
        });
    }
}

