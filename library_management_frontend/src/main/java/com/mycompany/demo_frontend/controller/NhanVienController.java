package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.dto.NhanVienDTO;
import com.mycompany.demo_frontend.service.NhanVienService;
import com.mycompany.demo_frontend.view.PanelNhanVien;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class NhanVienController {
    private PanelNhanVien panel;
    private NhanVienService nhanVienService = new NhanVienService();
    
    public NhanVienController(PanelNhanVien panel) {
        this.panel = panel;
        initEventHandlers();
    }
    
    private void initEventHandlers(){
        panel.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddNV();
            }
        });
        panel.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateNV();
            }
        });
        panel.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteNV();
            }
        });
        panel.getTxtTimKiem().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterNV();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterNV();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterNV();
            }
        });
        panel.getBtnNhap().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleImportData();
            }
        });
        panel.getBtnXuat().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExportData();
            }
        });
        panel.getTblNhanVien().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showNVInfoToForm();
            }
        });
    }
    
    public void handleAddNV() {
        // 1. Lấy dữ liệu từ UI
        String tenNV = panel.getTxtTenNV().getText().trim();
        String sdt = panel.getTxtSdt().getText().trim();
        String queQuan = panel.getTxtQueQuan().getText().trim();
        String username = panel.getTxtTaiKhoan().getText().trim();
        String password = panel.getTxtMatKhau().getText(); // Không trim để tránh mất ký tự đầu/cuối user nhập
        LocalDate date = panel.getDTPNgaySinh().getDate(); // Giả sử bạn dùng JDatePicker hoặc tương tự
        String gioiTinh = panel.getRadNam().isSelected() ? "Nam" : (panel.getRadNu().isSelected() ? "Nữ" : "");
        String role = panel.getRadQL().isSelected() ? "QUAN_LY" : (panel.getRadNV().isSelected() ? "NHAN_VIEN" : "");

        // 2. Kiểm tra đầu vào (validate)
        if (tenNV.isEmpty()) {
            showMessage("Tên nhân viên không được để trống.");
            return;
        }
        if (!tenNV.matches("^[\\p{L}\\s]+$")) {
            showMessage("Tên nhân viên chỉ được chứa chữ cái và khoảng trắng.");
            return;
        }
        if (sdt.isEmpty()) {
            showMessage("Số điện thoại không được để trống.");
            return;
        }
        if (!sdt.matches("\\d{10,11}")) {
            showMessage("Số điện thoại phải gồm 10 hoặc 11 chữ số.");
            return;
        }
        if (date == null) {
            showMessage("Bạn chưa chọn ngày sinh.");
            return;
        }
        if (queQuan.isEmpty()) {
            showMessage("Quê quán không được để trống.");
            return;
        }
        if (gioiTinh.isEmpty()) {
            showMessage("Bạn phải chọn giới tính.");
            return;
        }
        if (role.isEmpty()) {
            showMessage("Bạn phải chọn vai trò (Role).");
            return;
        }
        if (username.isEmpty()) {
            showMessage("Tài khoản không được để trống.");
            return;
        }
        if (password.isEmpty()) {
            showMessage("Mật khẩu không được để trống.");
            return;
        }

        // 3. Tạo DTO (convert java.util.Date sang java.sql.Date nếu backend yêu cầu)
        NhanVienDTO dto = new NhanVienDTO();
        dto.setTennv(tenNV);
        dto.setSdt(sdt);
        dto.setNgaysinh(date); // Nếu backend dùng java.sql.Date, thì dùng: new java.sql.Date(date.getTime())
        dto.setQuequan(queQuan);
        dto.setGioitinh(gioiTinh);
        dto.setRole(role);
        dto.setUsername(username);
        dto.setPassword(password);

        // 4. Gọi service để thêm mới
        try {
            NhanVienDTO result = nhanVienService.addNhanVien(dto);
            if (result != null) {
                JOptionPane.showMessageDialog(panel, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData(); // Refresh lại bảng
                clearData(); // Nếu có hàm clear form, gọi ở đây
            } else {
                JOptionPane.showMessageDialog(panel, "Thêm nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi thêm nhân viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public void handleUpdateNV() {
        int selectedRow = panel.getTblNhanVien().getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Vui lòng chọn Nhân viên cần sửa.");
            return;
        }

        // Lấy mã nhân viên từ dòng đã chọn
        int manv;
        try {
            manv = Integer.parseInt(panel.getTblNhanVien().getValueAt(selectedRow, 0).toString());
        } catch (NumberFormatException e) {
            showMessage("Không thể đọc mã Nhân viên. Hủy thao tác.");
            return;
        }

        // Lấy dữ liệu từ form
        String tenNV = panel.getTxtTenNV().getText().trim();
        String sdt = panel.getTxtSdt().getText().trim();
        String queQuan = panel.getTxtQueQuan().getText().trim();
        String username = panel.getTxtTaiKhoan().getText().trim();
        String password = panel.getTxtMatKhau().getText(); // Nếu cho phép đổi mật khẩu, nếu không thì để rỗng/null
        LocalDate localDate = panel.getDTPNgaySinh().getDate();
        String gioiTinh = panel.getRadNam().isSelected() ? "Nam" : (panel.getRadNu().isSelected() ? "Nữ" : "");
        String role = panel.getRadQL().isSelected() ? "QUAN_LY" : (panel.getRadNV().isSelected() ? "NHAN_VIEN" : "");

        // Kiểm tra dữ liệu đầu vào
        if (tenNV.isEmpty()) {
            showMessage("Tên nhân viên không để trống.");
            return;
        }
        if (!tenNV.matches("^[\\p{L}\\s]+$")) {
            showMessage("Tên nhân viên chỉ được chứa chữ cái và khoảng trắng.");
            return;
        }
        if (sdt.isEmpty()) {
            showMessage("Số điện thoại không để trống.");
            return;
        }
        if (!sdt.matches("0\\d{9}")) {
            showMessage("Số điện thoại phải bắt đầu bằng số 0 và gồm đúng 10 chữ số.");
            return;
        }
        if (localDate == null) {
            showMessage("Bạn chưa chọn ngày sinh.");
            return;
        }
        if (queQuan.isEmpty()) {
            showMessage("Quê quán không được để trống.");
            return;
        }
        if (gioiTinh.isEmpty()) {
            showMessage("Bạn phải chọn giới tính.");
            return;
        }
        if (role.isEmpty()) {
            showMessage("Bạn phải chọn vai trò (Role).");
            return;
        }

        // Tạo DTO để cập nhật
        NhanVienDTO dto = new NhanVienDTO();
        dto.setManv(manv);
        dto.setTennv(tenNV);
        dto.setSdt(sdt);
        dto.setNgaysinh(localDate); // Nếu DTO dùng LocalDate
        dto.setQuequan(queQuan);
        dto.setGioitinh(gioiTinh);
        dto.setRole(role);
        dto.setUsername(username);
        // Nếu muốn cho phép đổi mật khẩu, set vào dto. Nếu không, để null hoặc không truyền thuộc tính này.
        if (!password.isEmpty()) {
            dto.setPassword(password);
        }

        try {
            boolean success = nhanVienService.updateNhanVien(manv, dto);
            if (success) {
                showMessage("Cập nhật Nhân viên thành công.");
                loadTableData();
                clearData(); // Nếu có hàm clear form
            } else {
                showMessage("Cập nhật thất bại. Vui lòng thử lại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Đã xảy ra lỗi khi cập nhật Nhân viên.");
        }
    }
    // Hàm showMessage như cũ

    
    private void handleDeleteNV() {
        int selectedRow = panel.getTblNhanVien().getSelectedRow();

        if (selectedRow == -1) {
            showMessage("Vui lòng chọn Nhà xuất bản cần xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog( panel, "Bạn có chắc chắn muốn xóa Nhà xuất bản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int manv;
        try {
            manv = Integer.parseInt(panel.getTblNhanVien().getValueAt(selectedRow, 0).toString());
        } catch (NumberFormatException e) {
            showMessage("Không thể đọc mã Nhà xuất bản. Hủy thao tác.");
            return;
        }

        boolean success;
        try {
            success = nhanVienService.deleteNhanVien(manv);
        } catch (Exception ex) {
            ex.printStackTrace(); // ghi log nếu cần
            showMessage("Đã xảy ra lỗi khi xóa nhà xuất bản.");
            return;
        }

        if (success) {
            ((DefaultTableModel) panel.getTblNhanVien().getModel()).removeRow(selectedRow);
            showMessage("Xóa Nhà xuất bản thành công.");
        } else {
            showMessage("Xóa nhà xuất bản thất bại. Vui lòng thử lại.");
        }
    }
    
    private void filterNV() {
        String keyword = panel.getTxtTimKiem().getText().trim(); // Tên ô tìm kiếm nhân viên
        String searchType = panel.getCBTimKiem().getSelectedItem().toString().trim(); // Tên combobox chọn tiêu chí

        try {
            List<NhanVienDTO> listNV;

            // Nếu không nhập gì thì load lại toàn bộ danh sách
            if (keyword.isEmpty()) {
                listNV = nhanVienService.getAllNhanViens();
            } else {
                listNV = nhanVienService.searchNhanVien(keyword, searchType);
            }

            // Cập nhật lại bảng
            DefaultTableModel model = (DefaultTableModel) panel.getTblNhanVien().getModel();
            model.setRowCount(0);

            for (NhanVienDTO nv : listNV) {
                model.addRow(new Object[]{
                    nv.getManv(),
                    nv.getTennv(),
                    nv.getSdt(),
                    nv.getNgaysinh(),
                    nv.getGioitinh(),
                    nv.getRole(),
                    nv.getQuequan(),
                    nv.getUsername(),
                    "*******" // Password luôn hiển thị như này!
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
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

            XSSFSheet sheet = workbook.getSheetAt(0); // Sheet đầu tiên

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua dòng tiêu đề
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String tenNV = getCellString(row.getCell(1));
                String sdt = getCellString(row.getCell(2));
                String ngaySinhStr = getCellString(row.getCell(3));
                String gioiTinh = getCellString(row.getCell(4));
                String role = getCellString(row.getCell(5));
                String queQuan = getCellString(row.getCell(6));
                String username = getCellString(row.getCell(7));
                String password = getCellString(row.getCell(8));

                // Validate dữ liệu cơ bản
                if (tenNV.isEmpty() || sdt.isEmpty() || ngaySinhStr.isEmpty() || gioiTinh.isEmpty() || 
                    role.isEmpty() || queQuan.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    skippedCount++;
                    continue;
                }

                // Validate số điện thoại: bắt đầu từ 0, đúng 10 số
                if (!sdt.matches("0\\d{9}")) {
                    skippedCount++;
                    continue;
                }

                // Validate ngày sinh, convert về LocalDate
                LocalDate ngaySinh;
                try {
                    ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception ex) {
                    skippedCount++;
                    continue;
                }

                // Tạo DTO
                NhanVienDTO dto = new NhanVienDTO();
                dto.setTennv(tenNV);
                dto.setSdt(sdt);
                dto.setNgaysinh(ngaySinh);
                dto.setGioitinh(gioiTinh);
                dto.setRole(role);
                dto.setQuequan(queQuan);
                dto.setUsername(username);
                dto.setPassword(password); // Password plaintext

                try {
                    nhanVienService.addNhanVien(dto); // Gửi lên backend, backend sẽ tự hash password
                    importedCount++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    skippedCount++;
                }
            }

            JOptionPane.showMessageDialog(panel,
                "Import hoàn tất!\nThành công: " + importedCount + "\nBỏ qua: " + skippedCount);

            loadTableData(); // Reload lại bảng danh sách nhân viên

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Lỗi đọc file: " + e.getMessage());
        }
    }

    private void handleExportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("DanhSachNhanVien.xlsx"));

        int userSelection = fileChooser.showSaveDialog(panel);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Nhân viên");

            JTable table = panel.getTblNhanVien();
            TableModel model = table.getModel();

            // Xác định index của cột password (giả sử tên cột là "Mật khẩu")
            int passwordColIndex = -1;
            for (int col = 0; col < model.getColumnCount(); col++) {
                if (model.getColumnName(col).equalsIgnoreCase("Mật khẩu")) {
                    passwordColIndex = col;
                    break;
                }
            }

            // Ghi dòng tiêu đề (bỏ qua password)
            XSSFRow headerRow = sheet.createRow(0);
            int exportCol = 0;
            for (int col = 0; col < model.getColumnCount(); col++) {
                if (col == passwordColIndex) continue;
                headerRow.createCell(exportCol++).setCellValue(model.getColumnName(col));
            }

            // Ghi dữ liệu (bỏ qua password)
            for (int row = 0; row < model.getRowCount(); row++) {
                XSSFRow excelRow = sheet.createRow(row + 1);
                exportCol = 0;
                for (int col = 0; col < model.getColumnCount(); col++) {
                    if (col == passwordColIndex) continue;
                    Object value = model.getValueAt(row, col);
                    excelRow.createCell(exportCol++).setCellValue(value != null ? value.toString() : "");
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
    
    // Đặt trong NhanVienController
    public void showNVInfoToForm() {
        int selectedRow = panel.getTblNhanVien().getSelectedRow();
        if (selectedRow == -1) return; // Không chọn dòng nào

        DefaultTableModel model = (DefaultTableModel) panel.getTblNhanVien().getModel();

        panel.getTxtTenNV().setText(model.getValueAt(selectedRow, 1).toString());
        panel.getTxtSdt().setText(model.getValueAt(selectedRow, 2).toString());

        // Ngày sinh: Nếu dùng JDatePicker hoặc JXDatePicker (hoặc trường hợp bạn dùng LocalDate)
        try {
            Object value = model.getValueAt(selectedRow, 3);
            if (value != null && !value.toString().isEmpty()) {
                // Nếu đang lưu LocalDate hoặc String yyyy-MM-dd
                java.time.LocalDate date = java.time.LocalDate.parse(value.toString());
                panel.getDTPNgaySinh().setDate(date); // Nếu DTPNgaySinh là kiểu hỗ trợ LocalDate
            }
        } catch (Exception ex) {
            panel.getDTPNgaySinh().setText(""); // Hoặc setDate(null) nếu hỗ trợ
        }

        panel.getTxtQueQuan().setText(model.getValueAt(selectedRow, 6).toString());
        panel.getTxtTaiKhoan().setText(model.getValueAt(selectedRow, 7).toString());
        // Không fill mật khẩu (panel.getTxtMatKhau().setText("");)

        // Giới tính
        String gioiTinh = model.getValueAt(selectedRow, 4).toString();
        panel.getRadNam().setSelected("Nam".equalsIgnoreCase(gioiTinh));
        panel.getRadNu().setSelected("Nữ".equalsIgnoreCase(gioiTinh));

        // Role
        String role = model.getValueAt(selectedRow, 5).toString();
        panel.getRadQL().setSelected("QUAN_LY".equalsIgnoreCase(role));
        panel.getRadNV().setSelected("NHAN_VIEN".equalsIgnoreCase(role));
    }
    
    public void loadTableData() {
        try {
            List<NhanVienDTO> list = nhanVienService.getAllNhanViens(); // gọi API lấy danh sách NV
            DefaultTableModel model = (DefaultTableModel) panel.getTblNhanVien().getModel();
            model.setRowCount(0); // Xóa bảng cũ

            for (NhanVienDTO nv : list) {
                model.addRow(new Object[]{
                    nv.getManv(),
                    nv.getTennv(),
                    nv.getSdt(),
                    nv.getNgaysinh(),    // Tùy định dạng, có thể cần format lại khi hiển thị
                    nv.getGioitinh(),
                    nv.getRole(),
                    nv.getQuequan(),
                    nv.getUsername(),
                    "*****" // Password: luôn hiển thị như này!
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi tải danh sách nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearData() {
        panel.getTxtTenNV().setText("");
        panel.getTxtSdt().setText("");
        panel.getDTPNgaySinh().setText("");
        panel.getTxtQueQuan().setText("");
        panel.getTxtTaiKhoan().setText("");
        panel.getTxtMatKhau().setText("");
    }
    
    // Hàm hiển thị thông báo (dùng cho validate input)
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
    
    // Hàm lấy chuỗi từ Cell
    private String getCellString(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }
}
