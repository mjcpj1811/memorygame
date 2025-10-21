package Client.View;

import Client.Controller.AuthController;
import Server.DAO.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChallengeFrame extends JFrame {
    private final AuthController authController;
    private final String username;

    private JTextField tfSearch;
    private JTable tablePlayers;
    private DefaultTableModel tableModel;
    private List<String> playerList;

    public ChallengeFrame(AuthController authController, String username) throws SQLException {
        this.authController = authController;
        this.username = username;
        initUI();
    }

    private void initUI() throws SQLException {
        setTitle("🎯 Thách đấu người chơi");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

// ===== Header =====
        JLabel lblTitle = new JLabel("Thách đấu người chơi", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(41, 128, 185));

        JLabel lblSubtitle = new JLabel("Xin chào, " + username + " 👋", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(Color.DARK_GRAY);

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);

// ===== Ô tìm kiếm =====
        tfSearch = new JTextField();
        tfSearch.setPreferredSize(new Dimension(250, 30));
        JButton btnSearch = new JButton("🔍 Tìm kiếm");
        styleButton(btnSearch, new Color(52, 152, 219));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearch);

// ===== Bảng danh sách người chơi =====
        tableModel = new DefaultTableModel(new Object[]{"👤 Người chơi online"}, 0);
        tablePlayers = new JTable(tableModel);
        tablePlayers.setRowHeight(28);
        tablePlayers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablePlayers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablePlayers.getTableHeader().setBackground(new Color(41, 128, 185));
        tablePlayers.getTableHeader().setForeground(Color.WHITE);
        tablePlayers.setSelectionBackground(new Color(174, 214, 241));
        tablePlayers.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(tablePlayers);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                "Danh sách người chơi",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(44, 62, 80)
        ));

        // ===== Nút chức năng =====
        JButton btnChallenge = new JButton("🎯 Thách đấu");
        JButton btnBack = new JButton("↩ Quay lại");

        styleButton(btnChallenge, new Color(46, 204, 113));
        styleButton(btnBack, new Color(231, 76, 60));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnChallenge);
        buttonPanel.add(btnBack);

        // ===== Layout chính =====
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(10, 20, 10, 20));
        content.add(headerPanel, BorderLayout.NORTH);
        content.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(content);

        // ===== Dữ liệu mẫu =====
        UserDAO userDAO = new UserDAO();
        playerList = userDAO.getAll();
        loadPlayers(playerList);

        // ===== Sự kiện =====
        btnSearch.addActionListener(e -> searchPlayer());
        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) searchPlayer();
            }
        });

        btnChallenge.addActionListener(e -> challengeSelectedPlayer());
        btnBack.addActionListener(e -> {
            dispose();
            new MainFrame(authController, username).setVisible(true);
        });

    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 35));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(bg.darker()); }
            public void mouseExited(MouseEvent e) { button.setBackground(bg); }
        });
    }

    private void loadPlayers(List<String> players) {
        tableModel.setRowCount(0);
        for (String p : players) {
            if (!p.equalsIgnoreCase(username))
                tableModel.addRow(new Object[]{p});
        }
    }

    private void searchPlayer() {
        String keyword = tfSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadPlayers(playerList);
            return;
        }
        List<String> filtered = playerList.stream()
                .filter(p -> p.toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        loadPlayers(filtered);
    }

    private void challengeSelectedPlayer() {
        int selectedRow = tablePlayers.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người chơi để thách đấu!", "⚠ Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String opponent = (String) tableModel.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(this, "🎮 Đã gửi lời thách đấu tới: " + opponent, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }
}
