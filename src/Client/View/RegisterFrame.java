package Client.View;

import Client.Controller.AuthController;
import common.Response;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class RegisterFrame extends JFrame {
    private final AuthController authController;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public RegisterFrame(AuthController authController) {
        this.authController = authController;
        init();
    }

    private void init() {
        setTitle("Memory Game - Register");
        setSize(420, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblTitle = new JLabel("Create New Account", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfUser = new JTextField(20);
        JPasswordField pfPass = new JPasswordField(20);
        JTextField tfEmail = new JTextField(20);


        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tfUser, gbc);


        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(pfPass, gbc);


        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tfEmail, gbc);

        JButton btnRegister = new JButton("Register");
        JButton btnBack = new JButton("Back");

        btnRegister.setBackground(new Color(41, 128, 185));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setPreferredSize(new Dimension(100, 35));

        btnBack.setBackground(new Color(41, 128, 185));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setPreferredSize(new Dimension(100, 35));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnRegister.addActionListener(e -> {
            String u = tfUser.getText().trim();
            String pw = new String(pfPass.getPassword());
            String email = tfEmail.getText().trim();
            if (u.isEmpty() || pw.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
                return;
            }

            if (!Pattern.matches(EMAIL_REGEX, email)) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ! Vui lòng nhập đúng định dạng (vd: user@gmail.com)");
                return;
            }

            Response res = authController.register(u, pw, email);
            JOptionPane.showMessageDialog(this, res.getMessage());
            if (res.isSuccess()) {
                new LoginFrame(authController).setVisible(true);
                dispose();
            }
        });


        btnBack.addActionListener(e -> {
            new LoginFrame(authController).setVisible(true);
            this.dispose();
        });
    }
}
