package Client.View;

import Client.Controller.AuthController;
import common.Response;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final AuthController authController;

    public LoginFrame(AuthController authController) {
        this.authController = authController;
        init();
    }

    private void init() {
        setTitle("Memory Game - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JLabel lblTitle = new JLabel("Welcome to Memory Game", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(52, 73, 94));


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfUser = new JTextField(20);
        JPasswordField pfPass = new JPasswordField(20);


        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tfUser, gbc);


        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(pfPass, gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(100, 35));

        btnRegister.setBackground(new Color(41, 128, 185));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);


        btnLogin.addActionListener(e -> {
            String u = tfUser.getText().trim();
            String pw = new String(pfPass.getPassword());
            if (u.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập username & password");
                return;
            }
            Response res = authController.login(u, pw);
            if (res.isSuccess()) {
                new HomeFrame(authController).setVisible(true);
                dispose();
            }
        });

        btnRegister.addActionListener(e -> {
            new RegisterFrame(authController).setVisible(true);
            dispose();
        });
    }
}
