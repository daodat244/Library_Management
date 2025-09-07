package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.dto.SachDTO;
import com.mycompany.demo_frontend.dto.SachInputDTO;
import com.mycompany.demo_frontend.dto.TacGiaDTO;
import com.mycompany.demo_frontend.dto.NhaXuatBanDTO;
import com.mycompany.demo_frontend.dto.TheLoaiDTO;
import com.mycompany.demo_frontend.service.SachService;
import com.mycompany.demo_frontend.view.PanelSach;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SachController {
    private final PanelSach panel;
    private final SachService sachService;

    public SachController(PanelSach panel) {
        this.panel = panel;
        this.sachService = new SachService();
        initEventHandlers();
        loadComboBoxes();
        loadTableData();
    }

    private void initEventHandlers() {
        panel.getBtnThem().addActionListener(this::handleAddSach);
        panel.getBtnSua().addActionListener(this::handleUpdateSach);
        panel.getBtnXoa().addActionListener(this::handleDeleteSach);
        panel.getBtnNhapDuLieu().addActionListener(this::handleImportExcel);
        panel.getBtnXuatDuLieu().addActionListener(this::handleExportExcel);
        panel.getTxtTimKiem().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { handleSearch(); }
            @Override
            public void removeUpdate(DocumentEvent e) { handleSearch(); }
            @Override
            public void changedUpdate(DocumentEvent e) { handleSearch(); }
        });
        panel.getTableSach().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleTableClick();
            }
        });
    }

    private void loadComboBoxes() {
        try {
            List<TacGiaDTO> tacGiaList = sachService.getAllTacGia();
            panel.getCbTacGia().removeAllItems();
            for (TacGiaDTO tacGia : tacGiaList) {
                panel.getCbTacGia().addItem(tacGia.getMatacgia() + " - " + tacGia.getTentacgia());
            }

            List<NhaXuatBanDTO> nxbList = sachService.getAllNhaXuatBan();
            panel.getCbNXB().removeAllItems();
            for (NhaXuatBanDTO nxb : nxbList) {
                panel.getCbNXB().addItem(nxb.getManxb() + " - " + nxb.getTennxb());
            }

            List<TheLoaiDTO> theLoaiList = sachService.getAllTheLoai();
            panel.getCbTheLoai().removeAllItems();
            for (TheLoaiDTO theLoai : theLoaiList) {
                panel.getCbTheLoai().addItem(theLoai.getMatheloai() + " - " + theLoai.getTentheloai());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi tải dữ liệu combo box: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.out.println("Combo box load error: " + e.getMessage());
        }
    }

    public void loadTableData() {
        try {
            List<SachDTO> sachList = sachService.getAllSach();
            System.out.println("Loading table with " + sachList.size() + " books");
            DefaultTableModel model = (DefaultTableModel) panel.getTableSach().getModel();
            model.setRowCount(0);
            for (SachDTO sach : sachList) {
                model.addRow(new Object[]{
                    sach.getMasach(),
                    sach.getTensach(),
                    sach.getTentacgia(),
                    sach.getTennxb(),
                    sach.getTentheloai(),
                    sach.getNamxb(),
                    sach.getSotrang(),
                    sach.getSoluong()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi tải danh sách sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.out.println("Table load error: " + e.getMessage());
        }
    }

    private void handleAddSach(ActionEvent e) {
        SachInputDTO dto = getSachInputDTO(true);
        if (dto == null) {
            JOptionPane.showMessageDialog(panel, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SachDTO result = sachService.addSach(dto);
        if (result != null) {
            JOptionPane.showMessageDialog(panel, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(panel, "Thêm sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateSach(ActionEvent e) {
    int row = panel.getTableSach().getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(panel, "Vui lòng chọn một sách để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String maSach = panel.getTableSach().getValueAt(row, 0).toString();
    SachInputDTO dto = getSachInputDTO(false);
    if (dto == null) {
        JOptionPane.showMessageDialog(panel, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Đặt masach từ dòng được chọn, không cho phép lấy từ txtMaSach
    dto.setMasach(maSach);
    boolean success = sachService.updateSach(maSach, dto);
    if (success) {
        JOptionPane.showMessageDialog(panel, "Sửa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        loadTableData();
        clearFields();
    } else {
        JOptionPane.showMessageDialog(panel, "Sửa sách thất bại! Vui lòng kiểm tra thông tin hoặc kết nối API.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        System.out.println("Update failed for maSach: " + maSach + ", dto: " + dto.getTensach());
    }
}

private void handleDeleteSach(ActionEvent e) {
        int row = panel.getTableSach().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn một sách để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(panel, "Bạn có chắc muốn xóa sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String maSach = panel.getTableSach().getValueAt(row, 0).toString();
        boolean success = sachService.deleteSach(maSach);
        if (success) {
            JOptionPane.showMessageDialog(panel, "Xóa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearFields();
        } else {
            
        }
    }
    private void handleSearch() {
        String searchText = panel.getTxtTimKiem().getText().trim();
        String criteria = (String) panel.getCbTimKiem().getSelectedItem();
        if (criteria == null) criteria = "Mã sách"; // Default criteria
        List<SachDTO> sachList;
        if (searchText.isEmpty()) {
            sachList = sachService.getAllSach();
        } else {
            sachList = sachService.searchSach(searchText, criteria);
            System.out.println("Search with criteria: " + criteria + ", text: " + searchText + ", result size: " + sachList.size());
        }
        DefaultTableModel model = (DefaultTableModel) panel.getTableSach().getModel();
        model.setRowCount(0);
        for (SachDTO sach : sachList) {
            model.addRow(new Object[]{
                sach.getMasach(),
                sach.getTensach(),
                sach.getTentacgia(),
                sach.getTennxb(),
                sach.getTentheloai(),
                sach.getNamxb(),
                sach.getSotrang(),
                sach.getSoluong()
            });
        }
    }

    private void handleTableClick() {
        int row = panel.getTableSach().getSelectedRow();
        if (row < 0) return;

        try {
            panel.getTxtMaSach().setText(panel.getTableSach().getValueAt(row, 0).toString());
            panel.getTxtMaSach().setEnabled(false);
            panel.getTxtTenSach().setText(panel.getTableSach().getValueAt(row, 1).toString());
            panel.getTxtNamXB().setText(panel.getTableSach().getValueAt(row, 5) != null ? panel.getTableSach().getValueAt(row, 5).toString() : "");
            panel.getTxtSoTrang().setText(panel.getTableSach().getValueAt(row, 6) != null ? panel.getTableSach().getValueAt(row, 6).toString() : "");
            panel.getTxtSoLuong().setText(panel.getTableSach().getValueAt(row, 7) != null ? panel.getTableSach().getValueAt(row, 7).toString() : "");

            String tenTacGia = panel.getTableSach().getValueAt(row, 2).toString();
            String tenNXB = panel.getTableSach().getValueAt(row, 3).toString();
            String tenTheLoai = panel.getTableSach().getValueAt(row, 4).toString();

            for (int i = 0; i < panel.getCbTacGia().getItemCount(); i++) {
                String item = panel.getCbTacGia().getItemAt(i);
                if (item.contains(tenTacGia)) {
                    panel.getCbTacGia().setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < panel.getCbNXB().getItemCount(); i++) {
                String item = panel.getCbNXB().getItemAt(i);
                if (item.contains(tenNXB)) {
                    panel.getCbNXB().setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < panel.getCbTheLoai().getItemCount(); i++) {
                String item = panel.getCbTheLoai().getItemAt(i);
                if (item.contains(tenTheLoai)) {
                    panel.getCbTheLoai().setSelectedIndex(i);
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi chọn sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.out.println("Table click error: " + e.getMessage());
        }
    }

    private void handleImportExcel(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu sách");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) { return f.isDirectory() || f.getName().toLowerCase().endsWith(".xlsx"); }
            public String getDescription() { return "Excel Files (*.xlsx)"; }
        });

        if (fileChooser.showOpenDialog(panel) != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            int successCount = 0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                SachInputDTO dto = new SachInputDTO();
                dto.setMasach(getCellValue(row.getCell(0)));
                dto.setTensach(getCellValue(row.getCell(1)));
                dto.setMatacgia(getCellIntValue(row.getCell(2)));
                dto.setManxb(getCellIntValue(row.getCell(3)));
                dto.setMatheloai(getCellIntValue(row.getCell(4)));
                dto.setNamxb(getCellIntValue(row.getCell(5)));
                dto.setSotrang(getCellIntValue(row.getCell(6)));
                dto.setSoluong(getCellIntValue(row.getCell(7)));

                if (validateSachInput(dto, true) && sachService.addSach(dto) != null) {
                    successCount++;
                }
            }
            JOptionPane.showMessageDialog(panel, "Nhập thành công " + successCount + " sách!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi nhập Excel: " + e1.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.out.println("Import Excel error: " + e1.getMessage());
        }
    }

    private void handleExportExcel(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("danh_sach_sach.xlsx"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) { return f.isDirectory() || f.getName().toLowerCase().endsWith(".xlsx"); }
            public String getDescription() { return "Excel Files (*.xlsx)"; }
        });

        if (fileChooser.showSaveDialog(panel) != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        if (!file.getName().endsWith(".xlsx")) file = new File(file.getPath() + ".xlsx");

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("DanhSachSach");
            DefaultTableModel model = (DefaultTableModel) panel.getTableSach().getModel();

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã sách", "Tên sách", "Tác giả", "NXB", "Thể loại", "Năm XB", "Số trang", "Số lượng"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    row.createCell(j).setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                }
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            JOptionPane.showMessageDialog(panel, "Xuất Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(panel, "Lỗi khi xuất Excel: " + e1.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.out.println("Export Excel error: " + e1.getMessage());
        }
    }

    private SachInputDTO getSachInputDTO(boolean isAdd) {
        try {
            SachInputDTO dto = new SachInputDTO();
            String maSach = panel.getTxtMaSach().getText().trim();
            String tenSach = panel.getTxtTenSach().getText().trim();
            String tacGiaItem = (String) panel.getCbTacGia().getSelectedItem();
            String nxbItem = (String) panel.getCbNXB().getSelectedItem();
            String theLoaiItem = (String) panel.getCbTheLoai().getSelectedItem();
            String namXB = panel.getTxtNamXB().getText().trim();
            String soTrang = panel.getTxtSoTrang().getText().trim();
            String soLuong = panel.getTxtSoLuong().getText().trim();

            dto.setMasach(maSach);
            dto.setTensach(tenSach);
            dto.setMatacgia(tacGiaItem != null ? Integer.parseInt(tacGiaItem.split(" - ")[0]) : null);
            dto.setManxb(nxbItem != null ? Integer.parseInt(nxbItem.split(" - ")[0]) : null);
            dto.setMatheloai(theLoaiItem != null ? Integer.parseInt(theLoaiItem.split(" - ")[0]) : null);
            dto.setNamxb(namXB.isEmpty() ? null : Integer.parseInt(namXB));
            dto.setSotrang(soTrang.isEmpty() ? null : Integer.parseInt(soTrang));
            dto.setSoluong(soLuong.isEmpty() ? null : Integer.parseInt(soLuong));

            if (!validateSachInput(dto, isAdd)) return null;
            return dto;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Vui lòng nhập số hợp lệ cho các trường số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.out.println("Number format error: " + e.getMessage());
            return null;
        }
    }

    private boolean validateSachInput(SachInputDTO dto, boolean isAdd) {
        if (isAdd && (dto.getMasach() == null || dto.getMasach().isEmpty())) {
            JOptionPane.showMessageDialog(panel, "Mã sách không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dto.getTensach() == null || dto.getTensach().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Tên sách không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dto.getMatacgia() == null) {
            JOptionPane.showMessageDialog(panel, "Tác giả không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dto.getManxb() == null) {
            JOptionPane.showMessageDialog(panel, "Nhà xuất bản không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dto.getMatheloai() == null) {
            JOptionPane.showMessageDialog(panel, "Thể loại không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dto.getSoluong() == null || dto.getSoluong() < 0) {
            JOptionPane.showMessageDialog(panel, "Số lượng phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dto.getNamxb() != null && dto.getNamxb() > java.time.Year.now().getValue()) {
            JOptionPane.showMessageDialog(panel, "Năm xuất bản không được lớn hơn năm hiện tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dto.getSotrang() == null || dto.getSotrang() <= 0) { // Thêm kiểm tra số trang không null và lớn hơn 0
            JOptionPane.showMessageDialog(panel, "Số trang phải là số lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void clearFields() {
        panel.getTxtMaSach().setText("");
        panel.getTxtMaSach().setEnabled(true);
        panel.getTxtTenSach().setText("");
        panel.getTxtNamXB().setText("");
        panel.getTxtSoTrang().setText("");
        panel.getTxtSoLuong().setText("");
        if (panel.getCbTacGia().getItemCount() > 0) panel.getCbTacGia().setSelectedIndex(0);
        if (panel.getCbNXB().getItemCount() > 0) panel.getCbNXB().setSelectedIndex(0);
        if (panel.getCbTheLoai().getItemCount() > 0) panel.getCbTheLoai().setSelectedIndex(0);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf((int) cell.getNumericCellValue());
            default: return null;
        }
    }

    private Integer getCellIntValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case NUMERIC: return (int) cell.getNumericCellValue();
            case STRING:
                String value = cell.getStringCellValue().trim();
                return value.isEmpty() ? null : Integer.parseInt(value);
            default: return null;
        }
    }
}

