package Client.View;

import Client.Controller.HistoryController;
import common.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class HistoryFrame extends JFrame {
    private final HistoryController historyController;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;

    public HistoryFrame(HistoryController historyController) {
        this.historyController = historyController;
        init();
        loadHistory();
    }

    private void init() {
        setTitle("Memory Game - Match History");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblTitle = new JLabel("Match History", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(52, 73, 94));

        String[] columnNames = {
                "Match ID",
                "Player 1",
                "Player 2",
                "Score (P1 - P2)",
                "Time P1 (s)",
                "Time P2 (s)",
                "Match Date"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(41, 128, 185));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(120, 40));
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnRefresh);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnRefresh.addActionListener((event) -> {
            loadHistory();
        });
    }

    private void loadHistory() {
        Response response = historyController.getHistory();

        if (!response.isSuccess()) {
            JOptionPane.showMessageDialog(this,
                    "Không thể tải lịch sử: " + response.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Map<String, Object>> historyList = historyController.getHistoryList(response);

        if (historyList == null || historyList.isEmpty()) {
            tableModel.setRowCount(0);
            JOptionPane.showMessageDialog(this,
                    "Chưa có lịch sử trận đấu nào",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Map<String, Object> match : historyList) {
            Object[] row = new Object[7];
            row[0] = match.get("matchId");
            row[1] = match.get("player1");
            row[2] = match.get("player2");

            int score1 = (Integer) match.get("score1");
            int score2 = (Integer) match.get("score2");
            row[3] = score1 + " - " + score2;

            row[4] = String.format("%.2f", match.get("time1"));
            row[5] = String.format("%.2f", match.get("time2"));

            Timestamp timestamp = (Timestamp) match.get("matchDate");
            row[6] = timestamp != null ? dateFormat.format(timestamp) : "N/A";

            tableModel.addRow(row);
        }
    }
}