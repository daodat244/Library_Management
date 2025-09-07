package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.dto.TacGiaDTO;
import com.mycompany.demo_frontend.service.TacGiaService;
import com.mycompany.demo_frontend.view.PanelTacGia;
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

public class TacGiaController {
    private PanelTacGia panel;
    private TacGiaService tacGiaService = new TacGiaService();

    public TacGiaController(PanelTacGia panel) {
        this.panel = panel;
        initEventHandlers();
    }
    private void initEventHandlers(){
        panel.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddTacGia();
            }
        });
        panel.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateTacGia();
            }
        });
        panel.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteTacGia();
            }
        });
        panel.getTxtTimKiem().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterTacGia();
            }

            public void removeUpdate(DocumentEvent e) {
                filterTacGia();
            }

            public void changedUpdate(DocumentEvent e) {
                filterTacGia();
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
        panel.getTableTacGia().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showTacGiaInfoToForm();
            }
        });
    }
    
    public void handleAddTacGia() {
        String tenTG = panel.getTxtTenTacGia().getText().trim();
        String sdt = panel.getTxtSdt().getText().trim();
        String email = panel.getTxtEmail().getText().trim();
        String namSinh = panel.getTxtNamSinh().getText().trim();
        String queQuan = panel.getTxtQueQuan().getText().trim();
        String moTa = panel.getTxaMoTa().getText().trim();

        // Kiểm tra đầu vào
        if (tenTG.isEmpty()) {
            showMessage("Tên Tác giả không để trống.");
            return;
        }
        if (!tenTG.matches("^[\\p{L}\\s]+$")) { // \\p{L} = mọi chữ cái Unicode, hỗ trợ cả tên tiếng Việt
            showMessage("Tên Tác giả chỉ được chứa chữ cái và khoảng trắng.");
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
        
        if (namSinh.isEmpty()) {
            showMessage("Năm sinh không được để trống.");
            return;
        }
        if (!namSinh.matches("\\d{4}")) {
            showMessage("Năm sinh phải gồm đúng 4 chữ số.");
            return;
        }
        int namHienTai = java.time.Year.now().getValue();
        int nam = Integer.parseInt(namSinh);
        if (nam > namHienTai) {
            showMessage("Năm sinh không được lớn hơn năm hiện tại (" + namHienTai + ").");
            return;
        }
        
        if (queQuan.isEmpty()) {
            showMessage("Quê quán không được để trống.");
            return;
        }

        TacGiaDTO dto = new TacGiaDTO();
        dto.setTentacgia(tenTG);
        dto.setSdt(sdt);
        dto.setEmail(email);
        dto.setNamsinh(Integer.parseInt(namSinh));
        dto.setQuequan(queQuan);
        dto.setMota(moTa);

        try {
            TacGiaDTO result = tacGiaService.addTacGia(dto);
            if (result != null) {
                JOptionPane.showMessageDialog(panel, "Thêm Tác giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData(); // cập nhật lại bảng danh sách
                clearData(); // nếu bạn có hàm clear
            } else {
                JOptionPane.showMessageDialog(panel, "Thêm Tác giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi thêm Tác giả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleUpdateTacGia() {
        int selectedRow = panel.getTableTacGia().getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Vui lòng chọn Tác giả cần sửa.");
            return;
        }

        // Lấy mã phiếu từ bảng
        int matacgia;
        try {
            matacgia = Integer.parseInt(panel.getTableTacGia().getValueAt(selectedRow, 0).toString());
        } catch (NumberFormatException e) {
            showMessage("Không thể đọc mã Tác giả này. Hủy thao tác.");
            return;
        }

        // Lấy dữ liệu từ giao diện
        String tenTG = panel.getTxtTenTacGia().getText().trim();
        String sdt = panel.getTxtSdt().getText().trim();
        String email = panel.getTxtEmail().getText();
        String namSinh = panel.getTxtNamSinh().getText();
        String queQuan = panel.getTxtQueQuan().getText();
        String moTa = panel.getTxaMoTa().getText();

        if (tenTG.isEmpty()) {
            showMessage("Tên tác giả không để trống.");
            return;
        }
        if (!tenTG.matches("^[\\p{L}\\s]+$")) { // \\p{L} = mọi chữ cái Unicode, hỗ trợ cả tên tiếng Việt
            showMessage("Tên tác giả chỉ được chứa chữ cái và khoảng trắng.");
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
        if (namSinh.isEmpty()) {
            showMessage("Năm sinh không được để trống.");
            return;
        }
        if (!namSinh.matches("\\d{4}")) {
            showMessage("Năm sinh phải gồm đúng 4 chữ số.");
            return;
        }
        int namHienTai = java.time.Year.now().getValue();
        int nam = Integer.parseInt(namSinh);
        if (nam > namHienTai) {
            showMessage("Năm sinh không được lớn hơn năm hiện tại (" + namHienTai + ").");
            return;
        }
        
        if (queQuan.isEmpty()) {
            showMessage("Quê quán không được để trống.");
            return;
        }

        // Tạo đối tượng DTO mới để gửi lên API
        TacGiaDTO dto = new TacGiaDTO();
        dto.setMatacgia(matacgia); // cập nhật đúng ID
        dto.setTentacgia(tenTG);
        dto.setSdt(sdt);
        dto.setEmail(email);
        dto.setNamsinh(Integer.parseInt(namSinh));
        dto.setQuequan(queQuan);

        try {
            boolean success = tacGiaService.updateTacGia(matacgia, dto);
            if (success) {
                showMessage("Cập nhật tác giả thành công.");
                loadTableData(); // nếu có hàm reload lại bảng
                clearData();      // nếu có hàm xóa form
            } else {
                showMessage("Cập nhật thất bại. Vui lòng thử lại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Đã xảy ra lỗi khi cập nhật tác giả.");
        }

    }
    
    private void handleDeleteTacGia() {
        int selectedRow = panel.getTableTacGia().getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Vui lòng chọn tác giả cần xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog( panel, "Bạn có chắc chắn muốn xóa tác giả này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int matacgia;
        try {
            matacgia = Integer.parseInt(panel.getTableTacGia().getValueAt(selectedRow, 0).toString());
        } catch (NumberFormatException e) {
            showMessage("Không thể đọc mã tác. Hủy thao tác.");
            return;
        }

        boolean success;
        try {
            success = tacGiaService.deleteTacGia(matacgia);
        } catch (Exception ex) {
            ex.printStackTrace(); // ghi log nếu cần
            showMessage("Đã xảy ra lỗi khi xóa tác giả.");
            return;
        }

        if (success) {
            ((DefaultTableModel) panel.getTableTacGia().getModel()).removeRow(selectedRow);
            showMessage("Xóa tác giả thành công.");
        } else {
            showMessage("Xóa tác giả thất bại. Vui lòng thử lại.");
        }
    }
    
    private void filterTacGia() {
        String keyword = panel.getTxtTimKiem().getText().trim();
        String searchType = panel.getCBTimKiem().getSelectedItem().toString();

        TacGiaService tacGiaService = new TacGiaService();

        try {
            List<TacGiaDTO> listTacGia;

            // Nếu không nhập gì thì gọi getAll (tránh gọi API search rỗng)
            if (keyword.isEmpty()) {
                listTacGia = tacGiaService.getAllTacGia();
            } else {
                listTacGia = tacGiaService.searchTacGia(keyword, searchType);
            }

            // Cập nhật bảng
            DefaultTableModel model = (DefaultTableModel) panel.getTableTacGia().getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            for (TacGiaDTO tacgia : listTacGia) {
                model.addRow(new Object[]{
                    tacgia.getMatacgia(),
                    tacgia.getTentacgia(),
                    tacgia.getNamsinh(),
                    tacgia.getSdt(),
                    tacgia.getEmail(),
                    tacgia.getQuequan(),
                    tacgia.getMota()
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

                String tenTG = getCellString(row.getCell(1));
                String namSinh = getCellString(row.getCell(2));
                String queQuan = getCellString(row.getCell(3));
                String moTa = getCellString(row.getCell(4));
                String sdt = getCellString(row.getCell(5));
                String email = getCellString(row.getCell(6));

                // Kiểm tra số điện thoại hợp lệ
                if (!sdt.matches("\\d{10}")) {
                    skippedCount++;
                    continue;
                }
                
                if (!namSinh.matches("\\d{4}")) {
                    showMessage("Năm sinh phải gồm đúng 4 chữ số.");
                    skippedCount++;
                    continue;
                }
                int namHienTai = java.time.Year.now().getValue();
                int nam = Integer.parseInt(namSinh);
                if (nam > namHienTai) {
                    showMessage("Năm sinh không được lớn hơn năm hiện tại (" + namHienTai + ").");
                    skippedCount++;
                    continue;
                }

                // Tạo DTO bỏ qua mã độc giả vì là tự tăng
                TacGiaDTO dto = new TacGiaDTO();
                dto.setTentacgia(tenTG);
                dto.setSdt(sdt);
                dto.setEmail(email);
                dto.setQuequan(queQuan);
                dto.setNamsinh(Integer.parseInt(namSinh));
                dto.setMota(moTa);

                try {
                    tacGiaService.addTacGia(dto);
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
        fileChooser.setSelectedFile(new File("DanhSachTacGia.xlsx"));

        int userSelection = fileChooser.showSaveDialog(panel);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Tác Giả");

            JTable table = panel.getTableTacGia();
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
    
    public void showTacGiaInfoToForm() {
        int selectedRow = panel.getTableTacGia().getSelectedRow();
        if (selectedRow == -1) return; // Không chọn dòng nào

        DefaultTableModel model = (DefaultTableModel) panel.getTableTacGia().getModel();

        // Giả sử thứ tự cột: matacgia, tentacgia, sdt, email, namsinh, quequan, mota
        panel.getTxtTenTacGia().setText(model.getValueAt(selectedRow, 1) != null ? model.getValueAt(selectedRow, 1).toString() : "");
        panel.getTxtSdt().setText(model.getValueAt(selectedRow, 2) != null ? model.getValueAt(selectedRow, 2).toString() : "");
        panel.getTxtEmail().setText(model.getValueAt(selectedRow, 3) != null ? model.getValueAt(selectedRow, 3).toString() : "");
        panel.getTxtNamSinh().setText(model.getValueAt(selectedRow, 4) != null ? model.getValueAt(selectedRow, 4).toString() : "");
        panel.getTxtQueQuan().setText(model.getValueAt(selectedRow, 5) != null ? model.getValueAt(selectedRow, 5).toString() : "");
        panel.getTxaMoTa().setText(model.getValueAt(selectedRow, 6) != null ? model.getValueAt(selectedRow, 6).toString() : "");
    }

    public void loadTableData() {
        try {
            List<TacGiaDTO> list = tacGiaService.getAllTacGia(); // gọi API
            DefaultTableModel model = (DefaultTableModel) panel.getTableTacGia().getModel();
            model.setRowCount(0); // xóa trắng bảng
            for (TacGiaDTO tg : list) {
                model.addRow(new Object[]{
                    tg.getMatacgia(),
                    tg.getTentacgia(),
                    tg.getSdt(),
                    tg.getEmail(),
                    tg.getNamsinh(),
                    tg.getQuequan(),
                    tg.getMota()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi tải danh sách Tác giả: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearData() {
        panel.getTxtTenTacGia().setText("");
        panel.getTxtSdt().setText("");
        panel.getTxtEmail().setText("");
        panel.getTxtQueQuan().setText("");
        panel.getTxtNamSinh().setText("");
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
