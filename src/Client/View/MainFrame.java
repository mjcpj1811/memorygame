package Client.View;

import Client.Controller.AuthController;
import Server.DAO.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    private AuthController authController;
    private String username;

    private JTable tablePlayers;
    private DefaultTableModel tableModel;
    private JTextField tfSearch;
    private List<String> playerList;

    public MainFrame(AuthController authController, String username) {
        this.authController = authController;
        this.username = username;

        setTitle("🏠 Trang chủ & Thách đấu");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // ===== Header =====
        JLabel lblTitle = new JLabel("Xin chào, " + username + " 👋", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(41, 128, 185));
        lblTitle.setBorder(new EmptyBorder(10, 15, 10, 0));

        // ===== Khu vực chính: chia 2 phần =====
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(550);
        splitPane.setBorder(null);

        // ===== Panel bên trái: nội dung trang chủ =====
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.WHITE);
        homePanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel lblWelcome = new JLabel("🎮 Chào mừng bạn đến với Game Arena!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblWelcome.setForeground(new Color(52, 73, 94));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        homePanel.add(lblWelcome, BorderLayout.NORTH);

        JTextArea taInfo = new JTextArea(
                "Bạn có thể:\n" +
                        "• Xem danh sách người chơi online/offline\n" +
                        "• Gửi thách đấu trực tiếp\n" +
                        "• Cập nhật trạng thái bản thân\n" +
                        "• Quay lại trang đăng nhập"
        );
        taInfo.setEditable(false);
        taInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taInfo.setBackground(Color.WHITE);
        homePanel.add(taInfo, BorderLayout.CENTER);

        // ===== Panel bên phải: danh sách người chơi =====
        JPanel challengePanel = createChallengePanel();

        splitPane.setLeftComponent(homePanel);
        splitPane.setRightComponent(challengePanel);

        // ===== Layout chính =====
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(lblTitle, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createChallengePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // ===== Ô tìm kiếm =====
        tfSearch = new JTextField();
        JButton btnSearch = new JButton("🔍 Tìm kiếm");
        styleButton(btnSearch, new Color(52, 152, 219));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearch);

        // ===== Bảng người chơi =====
        tableModel = new DefaultTableModel(new Object[]{"👤 Người chơi", "Trạng thái"}, 0);
        tablePlayers = new JTable(tableModel);
        tablePlayers.setRowHeight(26);
        tablePlayers.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tablePlayers);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách người chơi"));

        // ===== Nút chức năng =====
        JButton btnChallenge = new JButton("🎯 Thách đấu");
        JButton btnRefresh = new JButton("🔄 Cập nhật");
        JButton btnLogout = new JButton("🚪 Đăng xuất");

        styleButton(btnChallenge, new Color(46, 204, 113));
        styleButton(btnRefresh, new Color(241, 196, 15));
        styleButton(btnLogout, new Color(231, 76, 60));

        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnChallenge);
        btnPanel.add(btnRefresh);
        btnPanel.add(btnLogout);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        // ===== Load dữ liệu =====
        loadPlayersFromDB();

        return panel;
    }

    private void loadPlayersFromDB() {
        try {
            UserDAO userDAO = new UserDAO();
            playerList = (List<String>) userDAO.getAll();
            tableModel.setRowCount(0);
            for (String p : playerList) {
                String status = Math.random() > 0.5 ? "Online" : "Offline"; // demo
                tableModel.addRow(new Object[]{p, status});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
}
