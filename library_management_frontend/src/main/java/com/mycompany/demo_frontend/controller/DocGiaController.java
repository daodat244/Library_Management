package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.dto.DocGiaDTO;
import com.mycompany.demo_frontend.service.DocGiaService;
import com.mycompany.demo_frontend.view.PanelDocGia;
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

public class DocGiaController {
    private PanelDocGia panel;
    private DocGiaService docGiaService = new DocGiaService();

    public DocGiaController(PanelDocGia panel) {
        this.panel = panel;
        initEventHandlers();
    }
    
    private void initEventHandlers(){
        panel.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddDocGia();
            }
        });
        panel.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateDocGia();
            }
        });
        panel.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteDocGia();
            }
        });
        panel.getTxtTimKiem().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterDocGia();
            }

            public void removeUpdate(DocumentEvent e) {
                filterDocGia();
            }

            public void changedUpdate(DocumentEvent e) {
                filterDocGia();
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
        
    }
    
    public void handleAddDocGia() {
        String tenDG = panel.getTxtTenDG().getText().trim();
        String sdt = panel.getTxtSdt().getText().trim();
        String email = panel.getTxtEmail().getText().trim();
        String diaChi = panel.getTxtDiaChi().getText().trim();

        // Kiểm tra đầu vào
        if (tenDG.isEmpty()) {
            showMessage("Tên độc giả không để trống.");
            return;
        }
        if (!tenDG.matches("^[\\p{L}\\s]+$")) { // \\p{L} = mọi chữ cái Unicode, hỗ trợ cả tên tiếng Việt
            showMessage("Tên độc giả chỉ được chứa chữ cái và khoảng trắng.");
            return;
        }

        if (sdt.isEmpty()) {
            showMessage("Số điện thoại không để trống.");
            return;
        }
        if (!sdt.matches("\\d{10}")) {
            showMessage("Số điện thoại phải gồm đúng 10 chữ số (0–9).");
            return;
        }
        if (email.isEmpty()) {
            showMessage("Email không để trống.");
            return;
        }
        if (diaChi.isEmpty()) {
            showMessage("Địa chỉ của bạn trống.");
            return;
        }

        DocGiaDTO dto = new DocGiaDTO();
        dto.setTendocgia(tenDG);
        dto.setSdt(sdt);
        dto.setEmail(email);
        dto.setDiachi(diaChi);

        try {
            DocGiaDTO result = docGiaService.addDocGia(dto);
            if (result != null) {
                JOptionPane.showMessageDialog(panel, "Thêm độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData(); // cập nhật lại bảng danh sách
                clearData(); // nếu bạn có hàm clear
            } else {
                JOptionPane.showMessageDialog(panel, "Thêm độc giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi thêm độc giả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleUpdateDocGia() {
        int selectedRow = panel.getTableDG().getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Vui lòng chọn Độc giả cần sửa.");
            return;
        }

        // Lấy mã phiếu từ bảng
        int madocgia;
        try {
            madocgia = Integer.parseInt(panel.getTableDG().getValueAt(selectedRow, 0).toString());
        } catch (NumberFormatException e) {
            showMessage("Không thể đọc mã độc giả này. Hủy thao tác.");
            return;
        }

        // Lấy dữ liệu từ giao diện
        String tenDG = panel.getTxtTenDG().getText().trim();
        String sdt = panel.getTxtSdt().getText().trim();
        String email = panel.getTxtEmail().getText();
        String diaChi = panel.getTxtDiaChi().getText();

        if (tenDG.isEmpty()) {
            showMessage("Tên độc giả không để trống.");
            return;
        }
        if (!tenDG.matches("^[\\p{L}\\s]+$")) { // \\p{L} = mọi chữ cái Unicode, hỗ trợ cả tên tiếng Việt
            showMessage("Tên độc giả chỉ được chứa chữ cái và khoảng trắng.");
            return;
        }

        if (sdt.isEmpty()) {
            showMessage("Số điện thoại không để trống.");
            return;
        }
        if (!sdt.matches("\\d{10}")) {
            showMessage("Số điện thoại phải gồm đúng 10 chữ số (0–9).");
            return;
        }
        if (email.isEmpty()) {
            showMessage("Email không để trống.");
            return;
        }
        if (diaChi.isEmpty()) {
            showMessage("Địa chỉ của bạn trống.");
            return;
        }

        // Tạo đối tượng DTO mới để gửi lên API
        DocGiaDTO dto = new DocGiaDTO();
        dto.setMadocgia(madocgia); // cập nhật đúng ID
        dto.setTendocgia(tenDG);
        dto.setSdt(sdt);
        dto.setEmail(email);
        dto.setDiachi(diaChi);

        try {
            boolean success = docGiaService.updateDocGia(madocgia, dto);
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

    private void handleDeleteDocGia() {
        int selectedRow = panel.getTableDG().getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Vui lòng chọn Độc giả cần xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog( panel, "Bạn có chắc chắn muốn xóa độc giả này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int madocgia;
        try {
            madocgia = Integer.parseInt(panel.getTableDG().getValueAt(selectedRow, 0).toString());
        } catch (NumberFormatException e) {
            showMessage("Không thể đọc mã độc giả. Hủy thao tác.");
            return;
        }

        boolean success;
        try {
            success = docGiaService.deleteDocGia(madocgia);
        } catch (Exception ex) {
            ex.printStackTrace(); // ghi log nếu cần
            showMessage("Đã xảy ra lỗi khi xóa độc giả.");
            return;
        }

        if (success) {
            ((DefaultTableModel) panel.getTableDG().getModel()).removeRow(selectedRow);
            showMessage("Xóa độc giả thành công.");
        } else {
            showMessage("Xóa độc giả thất bại. Vui lòng thử lại.");
        }
    }
    
    private void filterDocGia() {
        String keyword = panel.getTxtTimKiem().getText().trim();
        String searchType = panel.getCBTimKiem().getSelectedItem().toString();

        DocGiaService docGiaService = new DocGiaService();

        try {
            List<DocGiaDTO> listDocGia;

            // Nếu không nhập gì thì gọi getAll (tránh gọi API search rỗng)
            if (keyword.isEmpty()) {
                listDocGia = docGiaService.getAllDocGia();
            } else {
                listDocGia = docGiaService.searchDocGia(keyword, searchType);
            }

            // Cập nhật bảng
            DefaultTableModel model = (DefaultTableModel) panel.getTableDG().getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            for (DocGiaDTO docgia : listDocGia) {
                model.addRow(new Object[]{
                    docgia.getMadocgia(),
                    docgia.getTendocgia(),
                    docgia.getSdt(),
                    docgia.getEmail(),
                    docgia.getDiachi()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Lỗi khi tìm kiếm độc giả: " + e.getMessage());
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

                String tenDocGia = getCellString(row.getCell(1));
                String soDienThoai = getCellString(row.getCell(2));
                String email = getCellString(row.getCell(3));
                String diaChi = getCellString(row.getCell(4));

                // Kiểm tra số điện thoại hợp lệ
                if (!soDienThoai.matches("\\d{10}")) {
                    skippedCount++;
                    continue;
                }

                // Tạo DTO bỏ qua mã độc giả vì là tự tăng
                DocGiaDTO dto = new DocGiaDTO();
                dto.setTendocgia(tenDocGia);
                dto.setSdt(soDienThoai);
                dto.setEmail(email);
                dto.setDiachi(diaChi);

                try {
                    docGiaService.addDocGia(dto);
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
        fileChooser.setSelectedFile(new File("DanhSachDocGia.xlsx"));

        int userSelection = fileChooser.showSaveDialog(panel);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Độc Giả");

            JTable table = panel.getTableDG();
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
    
    public void loadTableData() {
        try {
            List<DocGiaDTO> list = docGiaService.getAllDocGia(); // gọi API
            DefaultTableModel model = (DefaultTableModel) panel.getTableDG().getModel();
            model.setRowCount(0); // xóa trắng bảng
            for (DocGiaDTO dg : list) {
                model.addRow(new Object[]{
                    dg.getMadocgia(),
                    dg.getTendocgia(),
                    dg.getSdt(),
                    dg.getEmail(),
                    dg.getDiachi()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi tải danh sách độc giả: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearData() {
        panel.getTxtTenDG().setText("");
        panel.getTxtSdt().setText("");
        panel.getTxtEmail().setText("");
        panel.getTxtDiaChi().setText("");
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

