package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.ChiTietPhieuTraRequest;
import com.mycompany.demo_frontend.dto.DetailedPhieuTraResponse;
import com.mycompany.demo_frontend.dto.DetailedPhieuTraResponse.ChiTietTraSach;
import com.mycompany.demo_frontend.dto.PhieuMuonResponse;
import com.mycompany.demo_frontend.dto.PhieuTraRequest;
import com.mycompany.demo_frontend.dto.SachDTO;
import com.mycompany.demo_frontend.service.PhieuTraService;
import com.mycompany.demo_frontend.service.SachService;
import com.mycompany.demo_frontend.view.PanelPhieuTra;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class PhieuTraController {

    private final PanelPhieuTra view;
    private final PhieuTraService service;
    private final SachService sachService;
    private final Map<String, String> tenSachToMaSachMap = new HashMap<>();

    public PhieuTraController(PanelPhieuTra view, PhieuTraService service, SachService sachService) {
        this.view = view;
        this.service = service;
        this.sachService = sachService;
        initComponents();
        if (AuthStorage.isLoggedIn()) {
            loadPhieuMuonChuaTra();
            loadPhieuTra();
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập để sử dụng chức năng!");
        }
        setupListeners();
        
        view.getTxtTimKiem().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchPhieuTra();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchPhieuTra();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchPhieuTra();
            }
        });
    }

    private void initComponents() {
        view.getTxtMaPhieu().setEditable(false);
        view.getTxtTenDocGia().setEditable(false);
        view.getTxtTenNhanVien().setEditable(false);
        view.getTxtMaDocGia().setEditable(false);
        view.getDateTimeNgayMuon().setEnabled(false);
        view.getDateTimeNgayHenTra().setEnabled(false);
        view.getTxtGhiChu().setEditable(false);
        String[] trangThaiOptions = {"Nguyên vẹn", "Hư hỏng/Mất"};
        view.getCbTrangThai1().setModel(new DefaultComboBoxModel<>(trangThaiOptions));
        view.getCbTrangThai2().setModel(new DefaultComboBoxModel<>(trangThaiOptions));
        view.getCbTrangThai3().setModel(new DefaultComboBoxModel<>(trangThaiOptions));
        view.getCbTrangThai4().setModel(new DefaultComboBoxModel<>(trangThaiOptions));
        view.getCbTrangThai5().setModel(new DefaultComboBoxModel<>(trangThaiOptions));
    }

    private void setupListeners() {
        for (JButton button : List.of(
               
                view.getBtnXuatDuLieu(), view.getBtnLamMoi(), view.getBtnTraSach())) {
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
        }
        view.getJTable1().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!AuthStorage.isLoggedIn()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập để sử dụng chức năng!");
                    return;
                }
                int row = view.getJTable1().getSelectedRow();
                if (row >= 0) {
                    int maphieu = (int) view.getJTable1().getValueAt(row, 0);
                    loadPhieuMuonDetail(maphieu);
                }
            }
        });

        view.getTablePhieuTra().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!AuthStorage.isLoggedIn()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập để sử dụng chức năng!");
                    return;
                }
                int row = view.getTablePhieuTra().getSelectedRow();
                if (row >= 0) {
                    int maphieutra = (int) view.getTablePhieuTra().getValueAt(row, 0);
                    loadPhieuTraDetail(maphieutra);
                }
            }
        });
        view.getBtnXuatDuLieu().addActionListener(this::exportToExcel);
        view.getBtnLamMoi().addActionListener(this::refresh);
        view.getBtnTraSach().addActionListener(this::traSachAction);
        view.getJTextField1().addActionListener(this::searchPhieuMuon);
        view.getJComboBox1().addActionListener(this::searchPhieuMuon);
    }

    public void loadPhieuMuonChuaTra() {
        try {
            List<PhieuMuonResponse> list = service.getPhieuMuonChuaTra();
            DefaultTableModel model = (DefaultTableModel) view.getJTable1().getModel();
            model.setRowCount(0);
            // Lọc chỉ các phiếu có trạng thái "Chưa trả"
            List<PhieuMuonResponse> filteredList = list.stream()
                    .filter(pm -> "Chưa trả".equals(pm.getTrangthai()))
                    .collect(Collectors.toList());
            if (filteredList.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không có phiếu mượn chưa trả nào!");
                System.out.println("Danh sách phiếu mượn chưa trả rỗng.");
                return;
            }
            for (PhieuMuonResponse pm : filteredList) {
                model.addRow(new Object[]{
                    pm.getMaphieu(),
                    pm.getTendocgia(),
                    pm.getNgaymuon(),
                    pm.getNgaytradukien()
                });
                System.out.println("Thêm phiếu mượn: Mã phiếu = " + pm.getMaphieu());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải danh sách phiếu mượn: " + e.getMessage());
            System.err.println("Lỗi trong loadPhieuMuonChuaTra: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadPhieuTra() {
        try {
            if (!AuthStorage.isLoggedIn()) {
                JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập để tải danh sách phiếu trả!");
                System.out.println("Chưa đăng nhập, không thể tải danh sách phiếu trả.");
                return;
            }
            List<DetailedPhieuTraResponse> list = service.getAllPhieuTra();
            DefaultTableModel model = (DefaultTableModel) view.getTablePhieuTra().getModel();
            model.setRowCount(0);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không có phiếu trả nào!");
                System.out.println("Danh sách phiếu trả rỗng.");
                return;
            }
            for (DetailedPhieuTraResponse pt : list) {
                model.addRow(new Object[]{
                    pt.getMaphieutra(),
                    pt.getMaphieu(),
                    pt.getTendocgia(),
                    pt.getTennv(),
                    String.join(", ", pt.getChiTietTraSachList().stream().map(ChiTietTraSach::getMasach).toList()),
                    pt.getNgaymuon(),
                    pt.getNgaytradukien(),
                    pt.getNgaytrathucte(),
                    pt.getTongphiphat(),
                    pt.getGhichu()
                });
                System.out.println("Thêm phiếu trả: Mã phiếu trả = " + pt.getMaphieutra() + ", Mã phiếu mượn = " + pt.getMaphieu());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải danh sách phiếu trả: " + e.getMessage());
            System.err.println("Lỗi trong loadPhieuTra: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadPhieuMuonDetail(int maphieu) {
        SwingWorker<PhieuMuonResponse, Void> worker = new SwingWorker<>() {
            @Override
            protected PhieuMuonResponse doInBackground() throws Exception {
                return service.getPhieuMuonById(maphieu);
            }

            @Override
            protected void done() {
                try {
                    PhieuMuonResponse pm = get();
                    if (pm == null) {
                        JOptionPane.showMessageDialog(view, "Không tìm thấy thông tin phiếu mượn.");
                        clearDetailPanel();
                        return;
                    }

                    tenSachToMaSachMap.clear(); // Clear the map for new data
                    view.getTxtMaPhieu().setText(String.valueOf(pm.getMaphieu()));
                    view.getTxtTenDocGia().setText(pm.getTendocgia());
                    view.getTxtTenNhanVien().setText(pm.getTennv());
                    view.getTxtMaDocGia().setText(String.valueOf(pm.getMadocgia()));
                    view.getDateTimeNgayMuon().setDateTimeStrict(pm.getNgaymuon());
                    view.getDateTimeNgayHenTra().setDateTimeStrict(pm.getNgaytradukien());
                    view.getDateTimeNgayTraThucTe().setDateTimeStrict(LocalDateTime.now());
                    view.getTxtGhiChu().setText("");

                    List<String> maSachList = pm.getMaSachList();
                    JLabel[] labels = {view.getJLabel13(), view.getJLabel14(), view.getJLabel15(), view.getJLabel16(), view.getJLabel17()};
                    JComboBox[] comboBoxes = {view.getCbTrangThai1(), view.getCbTrangThai2(), view.getCbTrangThai3(), view.getCbTrangThai4(), view.getCbTrangThai5()};
                    if (maSachList != null && !maSachList.isEmpty()) {
                        List<SachDTO> sachList = sachService.getSachByMaSachList(maSachList);
                        if (sachList != null && !sachList.isEmpty()) {
                            for (SachDTO sach : sachList) {
                                tenSachToMaSachMap.put(sach.getTensach(), sach.getMasach());
                            }
                            for (int i = 0; i < 5; i++) {
                                if (i < sachList.size()) {
                                    labels[i].setText(sachList.get(i).getTensach());
                                    comboBoxes[i].setVisible(true);
                                    comboBoxes[i].setSelectedItem("Nguyên vẹn");
                                    view.setComboBoxesEditable(true);
                                } else {
                                    labels[i].setText("");
                                    comboBoxes[i].setVisible(false);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(view, "Không tìm thấy thông tin sách.");
                            for (int i = 0; i < 5; i++) {
                                labels[i].setText("");
                                comboBoxes[i].setVisible(false);
                            }
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            labels[i].setText("");
                            comboBoxes[i].setVisible(false);
                        }
                    }
                    view.getDateTimeNgayTraThucTe().setEnabled(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Lỗi khi tải chi tiết phiếu mượn: " + e.getMessage());
                    System.err.println("Lỗi trong loadPhieuMuonDetail: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void loadPhieuTraDetail(int maphieutra) {
        SwingWorker<DetailedPhieuTraResponse, Void> worker = new SwingWorker<>() {
            @Override
            protected DetailedPhieuTraResponse doInBackground() throws Exception {
                return service.getPhieuTraById(maphieutra);
            }

            @Override
            protected void done() {
                try {
                    DetailedPhieuTraResponse pt = get();
                    if (pt == null) {
                        JOptionPane.showMessageDialog(view, "Không tìm thấy thông tin phiếu trả.");
                        clearDetailPanel();
                        return;
                    }

                    tenSachToMaSachMap.clear();
                    view.getTxtMaPhieu().setText(String.valueOf(pt.getMaphieu()));
                    view.getTxtTenDocGia().setText(pt.getTendocgia());
                    view.getTxtTenNhanVien().setText(pt.getTennv());
                    view.getTxtMaDocGia().setText(String.valueOf(pt.getMadocgia()));
                    view.getDateTimeNgayMuon().setDateTimeStrict(pt.getNgaymuon());
                    view.getDateTimeNgayHenTra().setDateTimeStrict(pt.getNgaytradukien());
                    view.getDateTimeNgayTraThucTe().setDateTimeStrict(pt.getNgaytrathucte());
                    view.getTxtGhiChu().setText(pt.getGhichu());
                    view.getTxtGhiChu().setEditable(false);

                    List<ChiTietTraSach> chiTietList = pt.getChiTietTraSachList() != null ? pt.getChiTietTraSachList() : List.of();
                    JLabel[] labels = {view.getJLabel13(), view.getJLabel14(), view.getJLabel15(), view.getJLabel16(), view.getJLabel17()};
                    JComboBox[] comboBoxes = {view.getCbTrangThai1(), view.getCbTrangThai2(), view.getCbTrangThai3(), view.getCbTrangThai4(), view.getCbTrangThai5()};
                    if (!chiTietList.isEmpty()) {
                        List<String> maSachList = chiTietList.stream().map(ChiTietTraSach::getMasach).collect(Collectors.toList());
                        List<SachDTO> sachList = sachService.getSachByMaSachList(maSachList);
                        if (sachList != null && !sachList.isEmpty()) {
                            for (SachDTO sach : sachList) {
                                tenSachToMaSachMap.put(sach.getTensach(), sach.getMasach());
                            }
                            for (int i = 0; i < Math.min(5, chiTietList.size()); i++) {
                                String maSach = chiTietList.get(i).getMasach(); // Biến tạm thời cố định
                                String tenSach = sachList.stream()
                                        .filter(sach -> sach.getMasach().equals(maSach))
                                        .map(SachDTO::getTensach)
                                        .findFirst()
                                        .orElse(maSach); // Fallback nếu không tìm thấy
                                labels[i].setText(tenSach);
                                comboBoxes[i].setVisible(true);
                                comboBoxes[i].setSelectedItem(chiTietList.get(i).getTrangthai());
                            }
                            // Ẩn các label và combo box còn lại
                            for (int i = chiTietList.size(); i < 5; i++) {
                                labels[i].setText("");
                                comboBoxes[i].setVisible(false);
                            }
                        } else {
                            JOptionPane.showMessageDialog(view, "Không tìm thấy thông tin sách.");
                            for (int i = 0; i < 5; i++) {
                                labels[i].setText("");
                                comboBoxes[i].setVisible(false);
                            }
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            labels[i].setText("");
                            comboBoxes[i].setVisible(false);
                        }
                    }
                    view.setComboBoxesEditable(false);
                    view.getDateTimeNgayTraThucTe().setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Lỗi khi tải chi tiết phiếu trả: " + e.getMessage());
                    System.err.println("Lỗi trong loadPhieuTraDetail: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void traSachAction(ActionEvent e) {
        if (!AuthStorage.isLoggedIn()) {
            JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập để sử dụng chức năng!");
            return;
        }
        try {
            int selectedRow = view.getJTable1().getSelectedRow();
            if (selectedRow < 0) {
                throw new IllegalArgumentException("Vui lòng chọn một phiếu mượn từ danh sách!");
            }
            PhieuTraRequest request = new PhieuTraRequest();
            String maPhieuText = view.getTxtMaPhieu().getText();
            if (maPhieuText.isEmpty()) {
                throw new IllegalArgumentException("Mã phiếu mượn không được để trống!");
            }
            request.setMaphieu(Integer.parseInt(maPhieuText));
            LocalDateTime ngayTraThucTe = view.getDateTimeNgayTraThucTe().getDateTimeStrict();
            if (ngayTraThucTe == null) {
                throw new IllegalArgumentException("Vui lòng chọn ngày trả thực tế!");
            }
            if (ngayTraThucTe.isBefore(view.getDateTimeNgayMuon().getDateTimeStrict())) {
                throw new IllegalArgumentException("Ngày trả thực tế không được trước ngày mượn!");
            }
            request.setNgaytrathucte(ngayTraThucTe);
            request.setGhiChu(view.getTxtGhiChu().getText());
            List<ChiTietPhieuTraRequest> chiTietList = new ArrayList<>();
            JLabel[] labels = {view.getJLabel13(), view.getJLabel14(), view.getJLabel15(), view.getJLabel16(), view.getJLabel17()};
            JComboBox[] comboBoxes = {view.getCbTrangThai1(), view.getCbTrangThai2(), view.getCbTrangThai3(), view.getCbTrangThai4(), view.getCbTrangThai5()};
            for (int i = 0; i < 5; i++) {
                if (comboBoxes[i].isVisible() && !labels[i].getText().isEmpty()) {
                    ChiTietPhieuTraRequest chiTiet = new ChiTietPhieuTraRequest();
                    String tenSach = labels[i].getText();
                    String maSach = tenSachToMaSachMap.getOrDefault(tenSach, tenSach); // Map tensach back to masach
                    chiTiet.setMasach(maSach);
                    chiTiet.setTrangthai(comboBoxes[i].getSelectedItem().toString());
                    chiTietList.add(chiTiet);
                }
            }
            request.setChiTietList(chiTietList);
            if (chiTietList.isEmpty()) {
                throw new IllegalArgumentException("Không có sách nào để trả!");
            }
            service.traSach(request);
            JOptionPane.showMessageDialog(view, "Trả sách thành công!");
            loadPhieuMuonChuaTra();
            loadPhieuTra();
            clearDetailPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi trả sách: " + ex.getMessage());
            System.err.println("Lỗi trong traSachAction: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void searchPhieuTra() {
        if (!AuthStorage.isLoggedIn()) {
            JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập để sử dụng chức năng!");
            return;
        }
        try {
            String searchText = view.getTxtTimKiem().getText().trim();
            String criteria = view.getCbTimKiem().getSelectedItem().toString();

            List<DetailedPhieuTraResponse> filteredList = searchText.isEmpty()
                    ? service.getAllPhieuTra()
                    : service.searchPhieuTra(searchText, criteria);
            
            DefaultTableModel model = (DefaultTableModel) view.getTablePhieuTra().getModel();
            model.setRowCount(0);

            for (DetailedPhieuTraResponse pt : filteredList) {
                model.addRow(new Object[]{
                    pt.getMaphieutra(),
                    pt.getMaphieu(),
                    pt.getTendocgia(),
                    pt.getTennv(),
                    String.join(", ", pt.getChiTietTraSachList().stream().map(ChiTietTraSach::getMasach).toList()),
                    pt.getNgaymuon(),
                    pt.getNgaytradukien(),
                    pt.getNgaytrathucte(),
                    pt.getTongphiphat(),
                    pt.getGhichu()
                });
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm phiếu trả: " + ex.getMessage());
        }
    }

    public void clearDetailPanel() {
        view.getTxtMaPhieu().setText("");
        view.getTxtTenDocGia().setText("");
        view.getTxtTenNhanVien().setText("");
        view.getTxtMaDocGia().setText("");
        view.getDateTimeNgayMuon().setDateTimePermissive(null);
        view.getDateTimeNgayHenTra().setDateTimePermissive(null);
        view.getDateTimeNgayTraThucTe().setDateTimePermissive(null);
        view.getTxtGhiChu().setText("");
        JLabel[] labels = {view.getJLabel13(), view.getJLabel14(), view.getJLabel15(), view.getJLabel16(), view.getJLabel17()};
        JComboBox[] comboBoxes = {view.getCbTrangThai1(), view.getCbTrangThai2(), view.getCbTrangThai3(), view.getCbTrangThai4(), view.getCbTrangThai5()};
        for (int i = 0; i < 5; i++) {
            labels[i].setText("");
            comboBoxes[i].setVisible(false);
        }
        view.getDateTimeNgayTraThucTe().setEnabled(true);
        view.setComboBoxesEditable(true);
        tenSachToMaSachMap.clear(); // Clear the map
    }

    private void searchPhieuMuon(ActionEvent e) {
        if (!AuthStorage.isLoggedIn()) {
            JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập để sử dụng chức năng!");
            return;
        }
        try {
            String searchText = view.getJTextField1().getText();
            String criteria = view.getJComboBox1().getSelectedItem().toString();
            List<PhieuMuonResponse> list = service.getPhieuMuonChuaTra();
            DefaultTableModel model = (DefaultTableModel) view.getJTable1().getModel();
            model.setRowCount(0);
            // Lọc chỉ các phiếu có trạng thái "Chưa trả"
            List<PhieuMuonResponse> filteredList = list.stream()
                    .filter(pm -> "Chưa trả".equals(pm.getTrangthai()))
                    .collect(Collectors.toList());
            if (filteredList.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không có phiếu mượn chưa trả nào!");
                return;
            }
            for (PhieuMuonResponse pm : filteredList) {
                if (criteria.equals("Mã phiếu") && String.valueOf(pm.getMaphieu()).contains(searchText)
                        || criteria.equals("Tên độc giả") && pm.getTendocgia().toLowerCase().contains(searchText.toLowerCase())) {
                    model.addRow(new Object[]{
                        pm.getMaphieu(),
                        pm.getTendocgia(),
                        pm.getNgaymuon(),
                        pm.getNgaytradukien()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm: " + ex.getMessage());
            System.err.println("Lỗi trong searchPhieuMuon: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void exportToExcel(ActionEvent e) {
        if (!AuthStorage.isLoggedIn()) {
            JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập để sử dụng chức năng!");
            return;
        }
        try {
            // Lấy dữ liệu từ tablePhieuTra hoặc từ service.getAllPhieuTra()
            List<DetailedPhieuTraResponse> list = service.getAllPhieuTra();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không có dữ liệu phiếu trả để xuất!");
                return;
            }

            // Tạo workbook Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Danh sách phiếu trả");

            // Tạo header
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "Mã phiếu trả", "Mã phiếu mượn", "Tên độc giả", "Tên nhân viên",
                "Mã sách", "Ngày mượn", "Ngày hẹn trả", "Ngày trả thực tế",
                "Phí phạt", "Ghi chú"
            };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                // Định dạng header
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = (Font) workbook.createFont();
                font.setBold(true);
                headerStyle.setFont((org.apache.poi.ss.usermodel.Font) font);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (DetailedPhieuTraResponse pt : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(pt.getMaphieutra());
                row.createCell(1).setCellValue(pt.getMaphieu());
                row.createCell(2).setCellValue(pt.getTendocgia());
                row.createCell(3).setCellValue(pt.getTennv());
                row.createCell(4).setCellValue(String.join(", ", pt.getChiTietTraSachList().stream().map(ChiTietTraSach::getMasach).toList()));
                row.createCell(5).setCellValue(pt.getNgaymuon() != null ? pt.getNgaymuon().toString() : "");
                row.createCell(6).setCellValue(pt.getNgaytradukien() != null ? pt.getNgaytradukien().toString() : "");
                row.createCell(7).setCellValue(pt.getNgaytrathucte() != null ? pt.getNgaytrathucte().toString() : "");
                row.createCell(8).setCellValue(pt.getTongphiphat());
                row.createCell(9).setCellValue(pt.getGhichu() != null ? pt.getGhichu() : "");
            }

            // Tự động điều chỉnh kích thước cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Mở JFileChooser để chọn nơi lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setSelectedFile(new File("DanhSachPhieuTra.xlsx"));
            if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // Đảm bảo file có đuôi .xlsx
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                // Lưu file
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(view, "Xuất file Excel thành công: " + filePath);
                }
                workbook.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi xuất file Excel: " + ex.getMessage());
            System.err.println("Lỗi trong exportToExcel: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void refresh(ActionEvent e) {
        try {
            view.getTxtTimKiem().setText("");
            loadPhieuMuonChuaTra();
            loadPhieuTra();
            clearDetailPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi làm mới: " + ex.getMessage());
            System.err.println("Lỗi trong refresh: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}
