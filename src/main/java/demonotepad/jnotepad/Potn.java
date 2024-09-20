/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demonotepad.jnotepad;

/**
 *
 * @author Administrator
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Potn extends JDialog {
    private JList<String> lstFont, lstStyle, lstSize; // Danh sách font, kiểu và kích thước
    private JButton btOK, btCancel; // Nút OK và Cancel
    private int[] fontStyle = {Font.PLAIN, Font.ITALIC, Font.BOLD, Font.BOLD + Font.ITALIC}; // Các kiểu font
    private JLabel lbPreView; // JLabel để hiển thị preview
    private Font font; // Font được chọn

    private Jnotepad parent; // Tham chiếu đến cửa sổ cha
    private JTextField searchField; // JTextField để tìm kiếm font

    public Potn(Frame owner, boolean modal) {
        super(owner, modal);
        setTitle("Font");
        parent = (Jnotepad) owner;
        createGUI();
        processEvent();
        setSize(360, 300);
        setLocationRelativeTo(owner); 
    }

    private void createGUI() {
        JPanel p = new JPanel();
        p.setLayout(null);

        // Tạo JTextField cho tìm kiếm font
        searchField = new JTextField();
        searchField.setBounds(5, 210, 130, 30);
        p.add(searchField);
        
        // Danh sách font
        lstFont = new JList<>();
        JScrollPane scrollPaneFont = new JScrollPane(lstFont);
        p.add(scrollPaneFont);
        scrollPaneFont.setBounds(5, 5, 130, 200);

        // Lấy danh sách font có sẵn
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        DefaultListModel<String> modelFont = new DefaultListModel<>();
        for (String f : fonts) {
            modelFont.addElement(f);
        }
        lstFont.setModel(modelFont);
        lstFont.setSelectedIndex(0);

        // Danh sách kiểu chữ
        lstStyle = new JList<>(new String[]{"Regular", "Italic", "Bold", "Bold Italic"});
        JScrollPane scrollPaneStyle = new JScrollPane(lstStyle);
        p.add(scrollPaneStyle);
        scrollPaneStyle.setBounds(140, 5, 150, 80);
        lstStyle.setSelectedIndex(0);

        // Danh sách kích thước chữ
        lstSize = new JList<>(new String[]{"8", "9", "10", "12", "14", "16", "18", "20", "24", "28", "36", "48", "72"});
        JScrollPane scrollPaneSize = new JScrollPane(lstSize);
        p.add(scrollPaneSize);
        scrollPaneSize.setBounds(295, 5, 40, 100);
        lstSize.setSelectedIndex(0);
       
        // Nút OK và Cancel
        p.add(btOK = new JButton("OK"));
        p.add(btCancel = new JButton("Cancel"));
        btOK.setBounds(145, 210, 80, 30);
        btCancel.setBounds(235, 210, 80, 30);

        // JLabel hiển thị preview font
        p.add(lbPreView = new JLabel("AaBbCcXxYyZz"));
        lbPreView.setBounds(145, 80, 150, 120);
        lbPreView.setFont(new Font("Arial", fontStyle[0], 20));
        
        add(p);
    }

    private void processEvent() {
        // Thêm ListSelectionListener cho các danh sách
        lstSize.addListSelectionListener(e -> preViewFont());
        lstFont.addListSelectionListener(e -> preViewFont());
        lstStyle.addListSelectionListener(e -> preViewFont());
        
        // Lắng nghe sự kiện thay đổi trên JTextField tìm kiếm
        searchField.addCaretListener(e -> filterFonts());

        // Xử lý sự kiện nút OK
        btOK.addActionListener(e -> {
            parent.txtEditor.setFont(font);
            this.dispose();
        });

        // Xử lý sự kiện nút Cancel
        btCancel.addActionListener(e -> dispose());
    }

    private void filterFonts() {
        String query = searchField.getText().toLowerCase(); // Lấy văn bản tìm kiếm
        DefaultListModel<String> filteredModel = new DefaultListModel<>();

        // Lấy danh sách font có sẵn
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String fontName : fonts) {
            // Kiểm tra nếu tên font chứa từ khóa tìm kiếm
            if (fontName.toLowerCase().contains(query)) {
                filteredModel.addElement(fontName);
            }
        }

        lstFont.setModel(filteredModel);
        if (!filteredModel.isEmpty()) {
            lstFont.setSelectedIndex(0); // Chọn mục đầu tiên nếu có kết quả
        }
    }

    private void preViewFont() {
        String fontName = lstFont.getSelectedValue(); // Lấy tên font
        int styleIndex = lstStyle.getSelectedIndex(); // Lấy kiểu font
        int size = Integer.parseInt(lstSize.getSelectedValue()); // Lấy kích thước font
        lbPreView.setFont(font = new Font(fontName, fontStyle[styleIndex], size)); // Cập nhật font cho JLabel preview
        lbPreView.setText("AaBbCcXxYyZz"); // Hiển thị văn bản mẫu
    }

    public static void main(String[] args) {
        Potn dlg = new Potn(null, true);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }
}
