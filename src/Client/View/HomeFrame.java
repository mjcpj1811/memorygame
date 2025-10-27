package Client.View;

import Client.Controller.AuthController;
import common.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class HomeFrame extends JFrame {
    private final AuthController authController;

    public HomeFrame(AuthController authController) {
        this.authController = authController;
        init();
    }

    private void init() {
        setTitle("Memory Game - Home page");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblTitle = new JLabel("Memory Game", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setPreferredSize(new Dimension(100, 35));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        add(headerPanel, BorderLayout.NORTH);

        btnLogout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            Response res = authController.logout();
            if (choice == JOptionPane.YES_OPTION && res.isSuccess()) {
                dispose();
                new LoginFrame(authController).setVisible(true);
            }
        });
    }
}
