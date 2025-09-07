package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.dto.DocGiaDTO;
import com.mycompany.demo_frontend.dto.TheLoaiDTO;
import com.mycompany.demo_frontend.service.TheLoaiService;
import com.mycompany.demo_frontend.view.PanelTheLoai;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TheLoaiController {
    private PanelTheLoai panel;
    private TheLoaiService theLoaiService = new TheLoaiService();

    public TheLoaiController(PanelTheLoai panel) {
        this.panel = panel;
        initEventHandlers();
    }
    
    private void initEventHandlers(){
        panel.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddTheLoai();
            }
        });
        panel.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateTheLoai();
            }
        });
        panel.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteTheLoai();
            }
        });
        panel.getTxtTimKiem().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterTheLoai();
            }

            public void removeUpdate(DocumentEvent e) {
                filterTheLoai();
            }

            public void changedUpdate(DocumentEvent e) {
                filterTheLoai();
            }
        });
        panel.getBtnNhapDuLieu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleImportData();
            }
        });
        panel.getBtnXuatDuLieu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExportData();
            }
        });
        panel.getTableTL().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showTheLoaiInfoToForm();
            }
        });
        
    }
    
    public void handleAddTheLoai() {
        String tenTL = panel.getTxtTenTL().getText().trim();
        String moTa = panel.getTxaMoTa().getText().trim();

        // Kiểm tra đầu vào
        if (tenTL.isEmpty()) {
            showMessage("Tên Thể loại không để trống.");
            return;
        }
        if (!tenTL.matches("^[\\p{L}\\s]+$")) { // \\p{L} = mọi chữ cái Unicode, hỗ trợ cả tên tiếng Việt
            showMessage("Tên thể loại chỉ được chứa chữ cái và khoảng trắng.");
            return;
        }

        if (moTa.isEmpty()) {
            showMessage("Mô tả đang bị trống.");
            return;
        }

        TheLoaiDTO dto = new TheLoaiDTO();
        dto.setTentheloai(tenTL);
        dto.setMota(moTa);

        try {
            TheLoaiDTO result = theLoaiService.addTheLoai(dto);
            if (result != null) {
                JOptionPane.showMessageDialog(panel, "Thêm thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData(); // cập nhật lại bảng danh sách
                clearData(); // nếu bạn có hàm clear
            } else {
                JOptionPane.showMessageDialog(panel, "Thêm thể loại thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi thêm thể loại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleUpdateTheLoai() {
        int selectedRow = panel.getTableTL().getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Vui lòng chọn thể loại cần sửa.");
            return;
        }

        // Lấy mã phiếu từ bảng
        int matheloai;
        try {
            matheloai = Integer.parseInt(panel.getTableTL().getValueAt(selectedRow, 0).toString());
        } catch (NumberFormatException e) {
            showMessage("Không thể đọc mã thể loại này. Hủy thao tác.");
            return;
        }

        // Lấy dữ liệu từ giao diện
        String tenTL = panel.getTxtTenTL().getText().trim();
        String moTa = panel.getTxaMoTa().getText().trim();

        if (tenTL.isEmpty()) {
            showMessage("Tên Thể loại không để trống.");
            return;
        }
        if (!tenTL.matches("^[\\p{L}\\s]+$")) { // \\p{L} = mọi chữ cái Unicode, hỗ trợ cả tên tiếng Việt
            showMessage("Tên thể loại chỉ được chứa chữ cái và khoảng trắng.");
            return;
        }

        if (moTa.isEmpty()) {
            showMessage("Mô tả đang bị trống.");
            return;
        }

        // Tạo đối tượng DTO mới để gửi lên API
        TheLoaiDTO dto = new TheLoaiDTO();
        dto.setMatheloai(matheloai);
        dto.setTentheloai(tenTL);
        dto.setMota(moTa);

        try {
            boolean success = theLoaiService.updateTheLoai(matheloai, dto);
            if (success) {
                showMessage("Cập nhật độc giả thành công.");
                loadTableData(); // nếu có hàm reload lại bảng
                clearData();      // nếu có hàm xóa form
            } else {
                showMessage("Cập nhật thất bại. Vui lòng thử lại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Đã xảy ra lỗi khi cập nhật độc giả.");
        }

    }
    
    private void handleDeleteTheLoai() {
        int selectedRow = panel.getTableTL().getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Vui lòng chọn thể loại cần xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog( panel, "Bạn có chắc chắn muốn xóa thể loại này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int matheloai;
        try {
            matheloai = Integer.parseInt(panel.getTableTL().getValueAt(selectedRow, 0).toString());
        } catch (NumberFormatException e) {
            showMessage("Không thể đọc mã thể loại. Hủy thao tác.");
            return;
        }

        boolean success;
        try {
            success = theLoaiService.deleteTheLoai(matheloai);
        } catch (Exception ex) {
            ex.printStackTrace(); // ghi log nếu cần
            showMessage("Đã xảy ra lỗi khi xóa thể loại.");
            return;
        }

        if (success) {
            ((DefaultTableModel) panel.getTableTL().getModel()).removeRow(selectedRow);
            showMessage("Xóa thể loại thành công.");
        } else {
            showMessage("Xóa thể loại thất bại. Vui lòng thử lại.");
        }
    }
    
    private void filterTheLoai() {
        String keyword = panel.getTxtTimKiem().getText().trim();
        String searchType = panel.getCBTimKiem().getSelectedItem().toString();

        TheLoaiService theLoaiService = new TheLoaiService();

        try {
            List<TheLoaiDTO> listTheLoai;

            // Nếu không nhập gì thì gọi getAll (tránh gọi API search rỗng)
            if (keyword.isEmpty()) {
                listTheLoai = theLoaiService.getAllTheLoai();
            } else {
                listTheLoai = theLoaiService.searchTheLoai(keyword, searchType);
            }

            // Cập nhật bảng
            DefaultTableModel model = (DefaultTableModel) panel.getTableTL().getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            for (TheLoaiDTO theLoai : listTheLoai) {
                model.addRow(new Object[]{
                    theLoai.getMatheloai(),
                    theLoai.getTentheloai(),
                    theLoai.getMota()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Lỗi khi tìm kiếm thể loại: " + e.getMessage());
        }
    }
    
    private void handleImportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để import");
        int result = fileChooser.showOpenDialog(panel);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        int importedCount = 0;
        int skippedCount = 0;

        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua dòng tiêu đề
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String tenTL = getCellString(row.getCell(1));
                String moTa = getCellString(row.getCell(2));

                // Kiểm tra số điện thoại hợp lệ
                if (tenTL.isEmpty()) {
                    showMessage("Tên Thể loại không để trống.");
                    skippedCount++;
                    continue;
                }
                if (!tenTL.matches("^[\\p{L}\\s]+$")) { // \\p{L} = mọi chữ cái Unicode, hỗ trợ cả tên tiếng Việt
                    showMessage("Tên thể loại chỉ được chứa chữ cái và khoảng trắng.");
                    skippedCount++;
                    continue;
                }

                // Tạo DTO bỏ qua mã độc giả vì là tự tăng
                TheLoaiDTO dto = new TheLoaiDTO();
                dto.setTentheloai(tenTL);
                dto.setMota(moTa);

                try {
                    theLoaiService.addTheLoai(dto);
                    importedCount++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    skippedCount++;
                }
            }

            JOptionPane.showMessageDialog(panel,
                "Import hoàn tất!\nThành công: " + importedCount + "\nBỏ qua: " + skippedCount);

            loadTableData(); // reload bảng

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Lỗi đọc file: " + e.getMessage());
        }
    }
    
    private void handleExportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("DanhSachTheLoai.xlsx"));

        int userSelection = fileChooser.showSaveDialog(panel);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Thể loại");

            JTable table = panel.getTableTL();
            TableModel model = table.getModel();

            // Ghi dòng tiêu đề
            XSSFRow headerRow = sheet.createRow(0);
            for (int col = 0; col < model.getColumnCount(); col++) {
                headerRow.createCell(col).setCellValue(model.getColumnName(col));
            }

            // Ghi dữ liệu
            for (int row = 0; row < model.getRowCount(); row++) {
                XSSFRow excelRow = sheet.createRow(row + 1);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    excelRow.createCell(col).setCellValue(value != null ? value.toString() : "");
                }
            }

            // Ghi file ra đĩa
            try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                workbook.write(out);
            }

            JOptionPane.showMessageDialog(panel, "Xuất dữ liệu thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Lỗi khi xuất file: " + e.getMessage());
        }
    }
    
    public void showTheLoaiInfoToForm() {
        int selectedRow = panel.getTableTL().getSelectedRow();
        if (selectedRow == -1) return; // Không chọn dòng nào

        DefaultTableModel model = (DefaultTableModel) panel.getTableTL().getModel();

        panel.getTxtTenTL().setText(model.getValueAt(selectedRow, 1) != null ? model.getValueAt(selectedRow, 1).toString() : "");
        panel.getTxaMoTa().setText(model.getValueAt(selectedRow, 2) != null ? model.getValueAt(selectedRow, 6).toString() : "");
    }
    
    public void loadTableData() {
        try {
            List<TheLoaiDTO> list = theLoaiService.getAllTheLoai(); // gọi API
            DefaultTableModel model = (DefaultTableModel) panel.getTableTL().getModel();
            model.setRowCount(0); // xóa trắng bảng
            for (TheLoaiDTO tl : list) {
                model.addRow(new Object[]{
                    tl.getMatheloai(),
                    tl.getTentheloai(),
                    tl.getMota(),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi tải danh sách độc giả: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearData() {
        panel.getTxtTenTL().setText("");
        panel.getTxaMoTa().setText("");
    }
    
    private String getCellString(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(panel, msg);
    }
}
