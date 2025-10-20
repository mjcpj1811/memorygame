package Client.View;

import Client.Controller.HistoryController;
import Server.model.MatchDetail;
import common.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryFrame extends JFrame {
    private final HistoryController historyController;
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterComboBox;
    private JTextField searchField;
    
    public HistoryFrame(HistoryController historyController) {
        this.historyController = historyController;
        init();
        loadMatchHistory();
    }
    
    private void init() {
        setTitle("Memory Game - Lịch sử trận đấu");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Tạo tiêu đề
        JLabel titleLabel = new JLabel("Lịch sử trận đấu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        
        // Tạo panel điều khiển
        JPanel controlPanel = createControlPanel();
        
        // Tạo bảng lịch sử
        createHistoryTable();
        JScrollPane scrollPane = new JScrollPane(historyTable);
        
        // Tạo panel nút
        JPanel buttonPanel = createButtonPanel();
        
        // Tạo panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        
        // ComboBox lọc
        filterComboBox = new JComboBox<>(new String[]{"Tất cả", "Top 10", "Top 20"});
        filterComboBox.setPreferredSize(new Dimension(100, 30));
        
        // TextField tìm kiếm
        searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 30));
        
        // Nút tìm kiếm
        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setBackground(new Color(41, 128, 185));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setPreferredSize(new Dimension(80, 30));
        
        // Nút làm mới
        JButton refreshButton = new JButton("Làm mới");
        refreshButton.setBackground(new Color(41, 128, 185));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(80, 30));
        
        panel.add(new JLabel("Lọc:"));
        panel.add(filterComboBox);
        panel.add(new JLabel("Tìm:"));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(refreshButton);
        
        // Event listeners
        filterComboBox.addActionListener(e -> applyFilter());
        searchButton.addActionListener(e -> searchByUsername());
        refreshButton.addActionListener(e -> loadMatchHistory());
        
        return panel;
    }
    
    private void createHistoryTable() {
        String[] columnNames = {"Match ID", "Người chơi 1", "Người chơi 2", "Điểm số", "Thời gian chơi", "Thời gian tạo", "Người chiến thắng"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(25);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Đặt độ rộng cột
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(90);  // Match ID
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Người chơi 1
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Người chơi 2
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Điểm số
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Thời gian chơi
        historyTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Thời gian tạo
        historyTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Người chiến thắng
        
        // Style cho bảng
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        historyTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);
        
        JButton closeButton = new JButton("Đóng");
        closeButton.setBackground(new Color(41, 128, 185));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(100, 35));
        
        panel.add(closeButton);
        
        // Event listeners
        closeButton.addActionListener(e -> dispose());
        
        return panel;
    }
    
    private void loadMatchHistory() {
        Response response = historyController.getMatchOverviews();
        if (response.isSuccess()) {
            @SuppressWarnings("unchecked")
            List<Object[]> overviews = (List<Object[]>) response.getData().get("overviews");
            updateTable(overviews);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void applyFilter() {
        String selectedFilter = (String) filterComboBox.getSelectedItem();
        Response response;
        
        // Giữ đơn giản: chỉ reload tất cả (có thể mở rộng sau)
        response = historyController.getMatchOverviews();
        
        if (response.isSuccess()) {
            @SuppressWarnings("unchecked")
            List<Object[]> overviews = (List<Object[]>) response.getData().get("overviews");
            updateTable(overviews);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchByUsername() {
        String username = searchField.getText().trim();
        if (username.isEmpty()) {
            loadMatchHistory();
            return;
        }
        
        // Tìm kiếm trong dữ liệu hiện tại
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        int rowCount = model.getRowCount();
        
        for (int i = 0; i < rowCount; i++) {
            String tableUsername = (String) model.getValueAt(i, 1);
            if (tableUsername.toLowerCase().contains(username.toLowerCase())) {
                historyTable.setRowSelectionInterval(i, i);
                historyTable.scrollRectToVisible(historyTable.getCellRect(i, 0, true));
                break;
            }
        }
    }
    
    private void updateTable(List<Object[]> overviews) {
        tableModel.setRowCount(0);
        
        if (overviews != null) {
            for (int i = 0; i < overviews.size(); i++) {
                Object[] o = overviews.get(i);
                Object[] row = {
                    o[0],
                    o[1],
                    o[3],
                    String.valueOf(o[2]) + " - " + String.valueOf(o[4]),
                    String.format("%.2f", (Double)o[5]) + " - " + String.format("%.2f", (Double)o[6]),
                    o[7] != null ? o[7].toString() : "N/A",
                    o[8]
                };
                tableModel.addRow(row);
            }
        }
    }
    
}
