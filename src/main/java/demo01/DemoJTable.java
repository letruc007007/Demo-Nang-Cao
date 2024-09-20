/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo01;

/**
 *
 * @author Administrator
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoJTable extends JFrame {
    private JTextField txtAccountName, txtAccountNumber; // TextField cho tên tài khoản và số tài khoản
    private JButton btnAdd, btnDelete; // Nút Thêm và Xóa
    private JTable table; // Bảng hiển thị thông tin tài khoản
    private DefaultTableModel tableModel; // Mô hình bảng

    public DemoJTable() {
        setTitle("Quản lý Tài khoản Khách hàng");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createGUI();
        initializeData();
    }

    private void createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel để chứa các label và textfield
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Tên tài khoản:"));
        txtAccountName = new JTextField();
        inputPanel.add(txtAccountName);
        
        inputPanel.add(new JLabel("Số tài khoản:"));
        txtAccountNumber = new JTextField();
        inputPanel.add(txtAccountNumber);

        btnAdd = new JButton("Thêm");
        btnDelete = new JButton("Xoá");
        
        inputPanel.add(btnAdd);
        inputPanel.add(btnDelete);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Tạo bảng và cuộn
        String[] columnNames = {"Tên tài khoản", "Số tài khoản"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        
        // Xử lý sự kiện nút Thêm
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountName = txtAccountName.getText().trim();
                String accountNumber = txtAccountNumber.getText().trim();

                if (accountName.isEmpty() || accountNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(DemoJTable.this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Thêm vào bảng
                tableModel.addRow(new Object[]{accountName, accountNumber});
                txtAccountName.setText("");
                txtAccountNumber.setText("");
            }
        });

        // Xử lý sự kiện nút Xoá
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DemoJTable.this, "Vui lòng chọn một mục để xoá!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                int confirm = JOptionPane.showConfirmDialog(DemoJTable.this, "Bạn có chắc chắn muốn xoá mục đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow);
                }
            }
        });
    }

    private void initializeData() {
        // Khởi tạo dữ liệu mẫu cho bảng
        tableModel.addRow(new Object[]{"Nguyễn Văn A", "123456789"});
        tableModel.addRow(new Object[]{"Trần Thị B", "987654321"});
        tableModel.addRow(new Object[]{"Lê Văn C", "456123789"});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DemoJTable demo = new DemoJTable();
            demo.setVisible(true);
        });
    }
}
