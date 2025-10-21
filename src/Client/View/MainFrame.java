package Client.View;

import Client.Controller.AuthController;
import Server.DAO.UserDAO;
import Server.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {

    private JTable tablePlayers;
    private DefaultTableModel tableModel;
    private JTextField tfSearch;

    public MainFrame(String username) {
        setTitle("Trang chủ & Thách đấu");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // ===== Header =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 247, 250));
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel lblTitle = new JLabel("Xin chào, " + username + " 👋");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(41, 128, 185));

        JButton btnLogout = new JButton("🚪 Đăng xuất");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        // ===== Khu vực chính =====
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(10, 15, 15, 15));

        // ===== Thanh tìm kiếm =====
        tfSearch = new JTextField();
        tfSearch.setPreferredSize(new Dimension(250, 35));
        JButton btnSearch = new JButton("🔍 Tìm kiếm");
        styleButton(btnSearch, new Color(52, 152, 219));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearch);

        // ===== Bảng danh sách người chơi =====
        tableModel = new DefaultTableModel(new Object[]{"👤 Người chơi", "Trạng thái"}, 0);
        tablePlayers = new JTable(tableModel);
        tablePlayers.setRowHeight(35);
        tablePlayers.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tablePlayers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tablePlayers.getTableHeader().setBackground(new Color(41, 128, 185));
        tablePlayers.getTableHeader().setForeground(Color.WHITE);
        tablePlayers.setSelectionBackground(new Color(174, 214, 241));

        JScrollPane scroll = new JScrollPane(tablePlayers);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                "Danh sách người chơi",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(44, 62, 80)
        ));

        // ===== Nút chức năng bên trái =====
        JButton btnChallenge = new JButton("🎯 Thách đấu");
        JButton btnBXH = new JButton("📊 Bảng xếp hạng");
        JButton btnHistory = new JButton("🕓 Lịch sử đấu");

        styleButton(btnChallenge, new Color(46, 204, 113));
        styleButton(btnBXH, new Color(241, 196, 15));
        styleButton(btnHistory, new Color(231, 76, 60));

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(3, 1, 20, 20));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setPreferredSize(new Dimension(180, 0));
        btnPanel.add(btnChallenge);
        btnPanel.add(btnBXH);
        btnPanel.add(btnHistory);

        // ===== Gộp bố cục =====
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.WEST);

        // ===== Layout chính =====
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // ===== Load dữ liệu =====
        loadPlayersFromDB();
    }

    private void loadPlayersFromDB() {
        try {
            UserDAO userDAO = new UserDAO();
            List<User> playerList = userDAO.getAll();
            tableModel.setRowCount(0);
            for (User p : playerList) {
                String status = Math.random() > 0.5 ? "🟢 Online" : "⚪ Offline";
                tableModel.addRow(new Object[]{p.getUsername(), status});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(160, 60));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame("bang").setVisible(true));
    }
}
