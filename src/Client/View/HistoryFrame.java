package Client.View;

import Client.Controller.HistoryController;
import common.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class HistoryFrame extends JFrame {
    private final HistoryController historyController;
    private DefaultTableModel tableModel;
    private JTable historyTable;

    public HistoryFrame(HistoryController historyController) {
        this.historyController = historyController;
        init();
        loadHistory();
    }

    private void init() {
        setTitle("Memory Game - Match History");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Title
        JLabel lblTitle = new JLabel("Match History", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(52, 73, 94));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Table setup
        String[] columnNames = {"Match ID", "Player 1", "Player 2", "Score", "Time P1 (s)", "Time P2 (s)", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(tableModel);
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        historyTable.setRowHeight(25);
        historyTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        historyTable.getTableHeader().setBackground(new Color(52, 73, 94));
        historyTable.getTableHeader().setForeground(Color.WHITE);

        // Set column widths
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        historyTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        historyTable.getColumnModel().getColumn(6).setPreferredWidth(180);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(41, 128, 185));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(120, 40));
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnClose = new JButton("Close");
        btnClose.setBackground(new Color(231, 76, 60));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.setPreferredSize(new Dimension(120, 40));
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 14));

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Actions
        btnRefresh.addActionListener(e -> loadHistory());
        btnClose.addActionListener(e -> dispose());
    }

    private void loadHistory() {
        try {
            Response response = historyController.getMatchHistory();
            
            if (response.isSuccess()) {
                tableModel.setRowCount(0); // Clear existing data
                
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> historyList = (List<Map<String, Object>>) response.get("history");
                
                if (historyList != null) {
                    for (Map<String, Object> match : historyList) {
                        String matchId = (String) match.get("match_id");
                        String player1 = (String) match.get("player1");
                        String player2 = (String) match.get("player2");
                        String score = (String) match.get("score");
                        String time1 = match.get("time1") != null ? String.format("%.2f", match.get("time1")) : "0.00";
                        String time2 = match.get("time2") != null ? String.format("%.2f", match.get("time2")) : "0.00";
                        String date = (String) match.get("date");

                        tableModel.addRow(new Object[]{matchId, player1, player2, score, time1, time2, date});
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Loaded " + tableModel.getRowCount() + " matches successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading history: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

