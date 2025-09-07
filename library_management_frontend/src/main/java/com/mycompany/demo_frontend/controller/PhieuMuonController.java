package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.NhanVienDTO;
import com.mycompany.demo_frontend.dto.PhieuMuonRequest;
import com.mycompany.demo_frontend.dto.PhieuMuonResponse;
import com.mycompany.demo_frontend.dto.SachDTO;
import com.mycompany.demo_frontend.service.PhieuMuonService;
import com.mycompany.demo_frontend.service.SachService;
import com.mycompany.demo_frontend.view.PanelPhieuMuon;
import com.mycompany.demo_frontend.view.PickDocGia;
import java.awt.HeadlessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class PhieuMuonController {
    private final PanelPhieuMuon panel;
    private final PhieuMuonService phieuMuonService;
    private final SachService sachService;
    private final NhanVienDTO currentNhanVien;
    private List<String> danhSachSachMuon = new ArrayList<>();
    private List<String> danhSachMaSachMuon = new ArrayList<>();
    

    public PhieuMuonController(PanelPhieuMuon panel, PhieuMuonService phieuMuonService, SachService sachService) {
        this.panel = panel;
        this.phieuMuonService = phieuMuonService;
        this.sachService = sachService;
        this.currentNhanVien = AuthStorage.getCurrentNhanVien();
        initEventHandlers();
        loadTablePhieuMuon();
        loadTableTimSach();
        setDefaultNhanVien();
    }

    private void setDefaultNhanVien() {
        if (currentNhanVien != null) {
            panel.getTxtTenNhanVien().setText(currentNhanVien.getTennv());
            panel.getTxtTenNhanVien().setEditable(false);
        }
    }

    private void initEventHandlers() {
        for (JButton button : List.of(
                panel.getBtnThem(), panel.getBtnSua(), panel.getBtnXoa(),
                panel.getBtnThemSachMuon(), panel.getBtnXoaSach(), panel.getBtnKiemtraDG(),
                panel.getBtnxuatdulieu(), panel.getBtnLamMoi())) {
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
        }
        panel.getBtnThem().addActionListener(e -> handleAddPhieuMuon());
        panel.getBtnSua().addActionListener(e -> handleUpdatePhieuMuon());
        panel.getBtnXoa().addActionListener(e -> handleDeletePhieuMuon());
        panel.getBtnThemSachMuon().addActionListener(e -> handleThemSachMuon());
        panel.getBtnXoaSach().addActionListener(e -> handleXoaSach());
        panel.getBtnxuatdulieu().addActionListener(e -> exportToExcel());


        panel.getBtnKiemtraDG().addActionListener(e -> {
            PickDocGia pickDocGia = new PickDocGia();
            String currentMadocgia = panel.getTxtMaDocGia().getText().trim();
            if (!currentMadocgia.isEmpty()) {
                try {
                    int madocgia = Integer.parseInt(currentMadocgia);
                    pickDocGia.selectDocGia(madocgia);
                } catch (NumberFormatException ignored) {
                }
            }
            pickDocGia.setVisible(true);
            pickDocGia.setDocGiaSelectedListener(madocgia -> {
                panel.getTxtMaDocGia().setText(String.valueOf(madocgia));
            });
        });

        panel.getBtnLamMoi().addActionListener(e -> handleLamMoi());
        

        panel.getTxtTimKiem().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterPhieuMuon();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterPhieuMuon();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterPhieuMuon();
            }
        });

        panel.getTxtSach().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterBooks();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterBooks();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterBooks();
            }
        });

        panel.getTablePhieuMuon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = panel.getTablePhieuMuon().getSelectedRow();
                if (row != -1) {
                    int maphieu = (int) panel.getTablePhieuMuon().getValueAt(row, 0);
                    loadPhieuMuonDetails(maphieu);
                }
            }
        });
    }

    public void clearForm() {
        panel.getTxtMaDocGia().setText("");
        panel.getTxtTenNhanVien().setText("");
        panel.getDtpMuon().clear();
        panel.getDtpTra().clear();
        panel.getTxtSach().setText("");
        panel.getTableTimSach().clearSelection();
        ((DefaultListModel<String>) panel.getJListSach().getModel()).clear();
        danhSachSachMuon.clear();
        danhSachMaSachMuon.clear();
    }

    public void handleLamMoi() {
        clearForm();
        setDefaultNhanVien();
        updateJListSach();
        loadTablePhieuMuon();
    }

    public void handleAddPhieuMuon() {
        String madocgia = panel.getTxtMaDocGia().getText().trim();
        LocalDateTime ngayMuon = panel.getDtpMuon().getDateTimeStrict();
        LocalDateTime ngayTra = panel.getDtpTra().getDateTimeStrict();

        if (!validateInputs(madocgia, ngayMuon, ngayTra)) {
            return;
        }

        PhieuMuonRequest requestDTO = new PhieuMuonRequest();
        requestDTO.setMadocgia(Integer.parseInt(madocgia));
        requestDTO.setManv(currentNhanVien.getManv());
        requestDTO.setNgaymuon(ngayMuon);
        requestDTO.setNgaytradukien(ngayTra);
        requestDTO.setMaSachList(new ArrayList<>(danhSachMaSachMuon));

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return phieuMuonService.createPhieuMuon(requestDTO);
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        showMessage("Thêm phiếu mượn thành công.");
                        loadTablePhieuMuon();
                        handleLamMoi();
                    } else {
                        showMessage("Thêm phiếu mượn thất bại.");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    showMessage("Lỗi khi thêm: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void handleUpdatePhieuMuon() {
        int selectedRow = panel.getTablePhieuMuon().getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Vui lòng chọn phiếu mượn cần sửa.");
            return;
        }

        int maphieu = (int) panel.getTablePhieuMuon().getValueAt(selectedRow, 0);
        String madocgia = panel.getTxtMaDocGia().getText().trim();
        LocalDateTime ngayMuon = panel.getDtpMuon().getDateTimeStrict();
        LocalDateTime ngayTra = panel.getDtpTra().getDateTimeStrict();

        if (!validateInputs(madocgia, ngayMuon, ngayTra)) {
            return;
        }

        PhieuMuonRequest requestDTO = new PhieuMuonRequest();
        requestDTO.setMadocgia(Integer.parseInt(madocgia));
        requestDTO.setManv(currentNhanVien.getManv());
        requestDTO.setNgaymuon(ngayMuon);
        requestDTO.setNgaytradukien(ngayTra);
        requestDTO.setMaSachList(new ArrayList<>(danhSachMaSachMuon));

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return phieuMuonService.updatePhieuMuon(maphieu, requestDTO);
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        showMessage("Cập nhật phiếu mượn thành công.");
                        loadTablePhieuMuon();
                        loadPhieuMuonDetails(maphieu);
                    } else {
                        showMessage("Cập nhật thất bại.");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    showMessage("Lỗi khi cập nhật: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    public void handleDeletePhieuMuon() {
        int selectedRow = panel.getTablePhieuMuon().getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Vui lòng chọn phiếu mượn cần xóa.");
            return;
        }

        int maphieu = (int) panel.getTablePhieuMuon().getValueAt(selectedRow, 0);
        SwingWorker<PhieuMuonResponse, Void> workerCheck = new SwingWorker<>() {
            @Override
            protected PhieuMuonResponse doInBackground() {
                return phieuMuonService.getPhieuMuonById(maphieu);
            }

            @Override
            protected void done() {
                try {
                    PhieuMuonResponse pm = get();
                    if ("Đã trả".equals(pm.getTrangthai())) {
                        showMessage("Không thể xóa phiếu mượn đã trả!");
                        return;
                    }
                    int confirm = JOptionPane.showConfirmDialog(panel, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        SwingWorker<Boolean, Void> workerDelete = new SwingWorker<>() {
                            @Override
                            protected Boolean doInBackground() {
                                return phieuMuonService.deletePhieuMuon(maphieu);
                            }

                            @Override
                            protected void done() {
                                try {
                                    if (get()) {
                                        ((DefaultTableModel) panel.getTablePhieuMuon().getModel()).removeRow(selectedRow);
                                        showMessage("Xóa phiếu mượn thành công.");
                                        loadTablePhieuMuon();
                                        handleLamMoi();
                                    } else {
                                        showMessage("Xóa thất bại.");
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    showMessage("Lỗi khi xóa: " + e.getMessage());
                                }
                            }
                        };
                        workerDelete.execute();
                    }
                } catch (HeadlessException | InterruptedException | ExecutionException e) {
                    showMessage("Lỗi khi kiểm tra: " + e.getMessage());
                }
            }
        };
        workerCheck.execute();
    }

    public void handleThemSachMuon() {
        int selectedRow = panel.getTableTimSach().getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Vui lòng chọn một sách.");
            return;
        }
        String maSach = panel.getTableTimSach().getValueAt(selectedRow, 0).toString();
        String tenSach = panel.getTableTimSach().getValueAt(selectedRow, 1).toString();

        if (danhSachMaSachMuon.contains(maSach)) {
            showMessage("Sách đã được chọn.");
            return;
        }
        if (danhSachMaSachMuon.size() >= 5) {
            showMessage("Chỉ được mượn tối đa 5 sách.");
            return;
        }
        danhSachMaSachMuon.add(maSach);
        danhSachSachMuon.add(tenSach);
        updateJListSach();
    }

    public void handleXoaSach() {
        int selectedIndex = panel.getJListSach().getSelectedIndex();
        if (selectedIndex == -1) {
            showMessage("Vui lòng chọn sách để xóa.");
            return;
        }
        danhSachMaSachMuon.remove(selectedIndex);
        danhSachSachMuon.remove(selectedIndex);
        updateJListSach();
    }

    private void filterBooks() {
        String keyword = panel.getTxtSach().getText().trim().toLowerCase();
        String searchType = panel.getCBTimSach().getSelectedItem().toString();
        String criteria = searchType.equals("Mã sách") ? "masach" : "tensach";
        List<SachDTO> listSach = keyword.isEmpty() ? sachService.getAllSach() : sachService.searchSach(keyword, criteria);
        DefaultTableModel model = (DefaultTableModel) panel.getTableTimSach().getModel();
        model.setRowCount(0);
        for (SachDTO sach : listSach) {
            model.addRow(new Object[]{sach.getMasach(), sach.getTensach()});
        }
    }

    private void filterPhieuMuon() {
        String keyword = panel.getTxtTimKiem().getText().trim();
        if (keyword.isEmpty()) {
            loadTablePhieuMuon();
            return;
        }
        String searchType = panel.getCBTimKiem().getSelectedItem().toString();
        String criteria = switch (searchType) {
            case "Mã phiếu" ->
                "Mã phiếu";
            case "Tên độc giả" ->
                "Tên độc giả";
            case "Tên nhân viên" ->
                "Tên nhân viên";
            default ->
                "";
        };
        if (criteria.isEmpty()) {
            showMessage("Vui lòng chọn tiêu chí tìm kiếm hợp lệ.");
            return;
        }
        List<PhieuMuonResponse> listPM = phieuMuonService.searchPhieuMuon(keyword, criteria);
        updateTable(listPM);
    }

    private boolean validateInputs(String madocgia, LocalDateTime ngayMuon, LocalDateTime ngayTra) {
        if (madocgia.isEmpty()) {
            showMessage("Vui lòng nhập mã độc giả hợp lệ.");
            return false;
        }
        if (ngayMuon == null || ngayTra == null || ngayMuon.isAfter(ngayTra)) {
            showMessage("Ngày mượn và trả không hợp lệ.");
            return false;
        }
        if (danhSachSachMuon.isEmpty() || danhSachMaSachMuon.isEmpty()) {
            showMessage("Vui lòng chọn ít nhất một quyển sách.");
            return false;
        }
        return true;
    }

    public void loadTablePhieuMuon() {
        List<PhieuMuonResponse> list = phieuMuonService.getAllPhieuMuon();
        updateTable(list);
    }

    private void updateTable(List<PhieuMuonResponse> list) {
        DefaultTableModel model = (DefaultTableModel) panel.getTablePhieuMuon().getModel();
        model.setRowCount(0);
        if (list != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            for (PhieuMuonResponse pm : list) {
                String maSachTongHop = pm.getMaSachList().stream().collect(Collectors.joining(", "));
                model.addRow(new Object[]{
                    pm.getMaphieu(),
                    pm.getTendocgia(),
                    pm.getTennv(),
                    maSachTongHop,
                    pm.getNgaymuon().format(formatter),
                    pm.getNgaytradukien().format(formatter),
                    pm.getTrangthai()
                });
            }
        }
    }

    private void loadPhieuMuonDetails(int maphieu) {
        SwingWorker<PhieuMuonResponse, Void> worker = new SwingWorker<>() {
            @Override
            protected PhieuMuonResponse doInBackground() {
                return phieuMuonService.getPhieuMuonById(maphieu);
            }

            @Override
            protected void done() {
                try {
                    PhieuMuonResponse pm = get();
                    if (pm == null) {
                        showMessage("Không tìm thấy thông tin phiếu mượn.");
                        handleLamMoi();
                        return;
                    }

                    panel.getTxtMaDocGia().setText(String.valueOf(pm.getMadocgia()));
                    panel.getTxtTenNhanVien().setText(pm.getTennv());
                    panel.getDtpMuon().setDateTimeStrict(pm.getNgaymuon());
                    panel.getDtpTra().setDateTimeStrict(pm.getNgaytradukien());

                    danhSachMaSachMuon.clear();
                    danhSachSachMuon.clear();
                    List<String> maSachList = pm.getMaSachList();
                    if (maSachList != null && !maSachList.isEmpty()) {
                        List<SachDTO> sachList = sachService.getSachByMaSachList(maSachList);
                        if (sachList != null && !sachList.isEmpty()) {
                            for (SachDTO sach : sachList) {
                                danhSachMaSachMuon.add(sach.getMasach());
                                danhSachSachMuon.add(sach.getTensach());
                            }
                            updateJListSach();
                        } else {
                            updateJListSach();
                            showMessage("Không tìm thấy thông tin sách.");
                        }
                    } else {
                        updateJListSach();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    showMessage("Lỗi khi tải chi tiết: " + e.getMessage());
                    handleLamMoi();
                }
            }
        };
        worker.execute();
    }

    public void loadTableTimSach() {
        List<SachDTO> list = sachService.getAllSach();
        DefaultTableModel model = (DefaultTableModel) panel.getTableTimSach().getModel();
        model.setRowCount(0);
        if (list != null) {
            for (SachDTO sach : list) {
                model.addRow(new Object[]{sach.getMasach(), sach.getTensach()});
            }
        }
    }

    private void updateJListSach() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < danhSachSachMuon.size(); i++) {
            listModel.addElement((i + 1) + ". " + danhSachSachMuon.get(i));
        }
        panel.getJListSach().setModel(listModel);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(panel, msg);
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("DanhSachPhieuMuon.xlsx"));

        int userSelection = fileChooser.showSaveDialog(panel);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // Thoát nếu người dùng nhấn Cancel
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

        // Kiểm tra nếu bảng rỗng
        DefaultTableModel model = (DefaultTableModel) panel.getTablePhieuMuon().getModel();
        if (model.getRowCount() == 0) {
            showMessage("Không có dữ liệu để xuất!");
            return;
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DanhSachPhieuMuon");

            // Tạo tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã Phiếu", "Tên độc giả", "Tên nhân viên", "Mã sách", "Ngày mượn", "Ngày hẹn trả", "Trạng thái"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);
                cell.setCellStyle(headerStyle);
            }

            // Lấy dữ liệu từ bảng
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    Object value = model.getValueAt(i, j);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // Tự động điều chỉnh kích thước cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Lưu file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                showMessage("Xuất dữ liệu thành công: " + filePath);
            }
        } catch (IOException e) {
            showMessage("Lỗi khi xuất dữ liệu: " + e.getMessage());
        }
    }
}
