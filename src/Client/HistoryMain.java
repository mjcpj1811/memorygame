package Client;

import Client.Controller.HistoryController;
import Client.View.HistoryFrame;

import javax.swing.*;

public class HistoryMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HistoryController historyController = new HistoryController();
            new HistoryFrame(historyController).setVisible(true);
        });
    }
}