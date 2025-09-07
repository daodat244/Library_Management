package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.dto.SachDTO;
import com.mycompany.demo_frontend.dto.ThongKeDocGiaDTO;
import com.mycompany.demo_frontend.dto.ThongKeNhanVienDTO;
import com.mycompany.demo_frontend.dto.ThongKePhieuMuonDTO;
import com.mycompany.demo_frontend.dto.ThongKeSachHongMatChiTietDTO;
import com.mycompany.demo_frontend.dto.ThongKeSachHongMatDTO;
import com.mycompany.demo_frontend.dto.ThongKeTacGiaDTO;
import com.mycompany.demo_frontend.service.ThongKeService;
import com.mycompany.demo_frontend.view.FrameThongKeDocGia;
import com.mycompany.demo_frontend.view.FrameThongKeNXB;
import com.mycompany.demo_frontend.view.FrameThongKeNhanVien;
import com.mycompany.demo_frontend.view.FrameThongKePhieuMuon;
import com.mycompany.demo_frontend.view.FrameThongKePhieuTra;
import com.mycompany.demo_frontend.view.FrameThongKeSach;
import com.mycompany.demo_frontend.view.FrameThongKeTacGia;
import com.mycompany.demo_frontend.view.FrameThongKeTheLoai;
import com.mycompany.demo_frontend.view.PanelThongKe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.util.Collections.list;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ThongKeController {

    private final ThongKeService thongKeService;
    private PanelThongKe viewThongKe = null;
    private FrameThongKeTheLoai viewThongKeTL = null;
    private FrameThongKeNXB viewThongKeNXB = null;
    private FrameThongKeSach viewThongKeSach = null;
    private FrameThongKeTacGia viewThongKeTG = null;
    private FrameThongKeDocGia viewThongKeDG = null;
    private FrameThongKeNhanVien viewThongKeNV = null;
    private FrameThongKePhieuMuon viewThongKePM = null;
    private FrameThongKePhieuTra viewThongKePT = null;

    public ThongKeController(PanelThongKe viewThongKe) {
        this.viewThongKe = viewThongKe;
        this.thongKeService = new ThongKeService();
        loadThongKe();
        initThongKeHandlers();
    }
    
    // Constructor cho FrameThongKeNXB (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeNXB viewNXB) {
        this.viewThongKeNXB = viewNXB;
        this.thongKeService = new ThongKeService();
        loadDataNXB();
    }
    // Constructor cho FrameThongKeTL (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeTheLoai viewTL) {
        this.viewThongKeTL = viewTL;
        this.thongKeService = new ThongKeService();
        loadDataTL();
    }
    // Constructor cho FrameThongKeSach (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeSach viewSach) {
        this.viewThongKeSach = viewSach;
        this.thongKeService = new ThongKeService();
        loadDataSach();
    }
    // Constructor cho FrameThongKeTacGia (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeTacGia viewTG) {
        this.viewThongKeTG = viewTG;
        this.thongKeService = new ThongKeService();
        loadDataTG();
    }
    // Constructor cho FrameThongKeDocGia (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeDocGia viewDG) {
        this.viewThongKeDG = viewDG;
        this.thongKeService = new ThongKeService();
        loadDataDG();
    }
    // Constructor cho FrameThongKeDocGia (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeNhanVien viewNV) {
        this.viewThongKeNV = viewNV;
        this.thongKeService = new ThongKeService();
        loadDataNV();
    }
    // Constructor cho FrameThongKeDocGia (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKePhieuMuon viewPM) {
        this.viewThongKePM = viewPM;
        this.thongKeService = new ThongKeService();
        loadDataPM();
    }
    // Constructor cho FrameThongKeNXB (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKePhieuTra viewPT) {
        this.viewThongKePT = viewPT;
        this.thongKeService = new ThongKeService();
        loadDataPT();
    }
    
    // ------------------------------------------
    // Dành cho PanelThongKe (Tổng quát)
    // ------------------------------------------
    public void initThongKeHandlers() {
        viewThongKe.getBtnNXB().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeNXB frame = new FrameThongKeNXB();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnTheLoai().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeTheLoai frame = new FrameThongKeTheLoai();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnSach().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeSach frame = new FrameThongKeSach();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnTacGia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeTacGia frame = new FrameThongKeTacGia();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnDocGia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeDocGia frame = new FrameThongKeDocGia();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnNhanVien().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeNhanVien frame = new FrameThongKeNhanVien();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnPhieuMuon().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKePhieuMuon frame = new FrameThongKePhieuMuon();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnPhieuTra().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKePhieuTra frame = new FrameThongKePhieuTra();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
        
    private void loadThongKe() {
        int soLuongNXB = thongKeService.getSoLuongNXB();
        int soLuongTheLoai = thongKeService.getSoLuongTheLoai();
        Long soLuongDauSach = thongKeService.getTongSoLuongSach();
        int soLuongTacGia = thongKeService.getSoLuongTacGia();
        int soLuongDocGia = thongKeService.getSoLuongDocGia();
        int soLuongNhanVien = thongKeService.getSoLuongNhanVien();
        int soLuongPhieuMuon = thongKeService.getSoLuongPhieuMuon();
        int soLuongPhieuTra = thongKeService.getSoLuongPhieuTra();
        
        viewThongKe.getLblNXB().setText(String.valueOf(soLuongNXB));
        viewThongKe.getLblTheLoai().setText(String.valueOf(soLuongTheLoai));
        viewThongKe.getLblDauSach().setText(String.valueOf(soLuongDauSach));
        viewThongKe.getLblTacGia().setText(String.valueOf(soLuongTacGia));
        viewThongKe.getLblDocGia().setText(String.valueOf(soLuongDocGia));
        viewThongKe.getLblNhanVien().setText(String.valueOf(soLuongNhanVien));
        viewThongKe.getLblPhieuMuon().setText(String.valueOf(soLuongPhieuMuon));
        viewThongKe.getLblPhieuTra().setText(String.valueOf(soLuongPhieuTra));
    }
    
    //------------------------------------
    //  Thống kê Thể loại
    //------------------------------------
    
    private void loadDataTL(){
        int soLuongTL = thongKeService.getSoLuongTheLoai();
            
        viewThongKeTL.getLblTongTheLoai().setText(String.valueOf(soLuongTL));
    }
    
    public void hienThiThongKeTheoTheLoaiBang() {
        List<Object[]> data = thongKeService.getDanhSachSachTheoTheLoai();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Thể loại", "Tên sách", "Số lượng"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeTL.getTblSachTheLoai().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    public void hienThiThongKeSLSachTheoTheLoai() {
        List<Object[]> data = thongKeService.getTongSoSachTheoTheLoai();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Thể loại", "Số lượng"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeTL.getTblSLSachTheLoai().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    //------------------------------------
    //  Thống kê Nhà xuất bản
    //------------------------------------
    
    private void loadDataNXB(){
        int soLuongNXB = thongKeService.getSoLuongNXB();
            
        viewThongKeNXB.getLblTongNXB().setText(String.valueOf(soLuongNXB));
    }
    
    public void hienThiThongKeSLSachTheoNXB() {
        List<Object[]> data = thongKeService.getThongKeSLSachTheoNXB();
        DefaultTableModel model = new DefaultTableModel(new String[]{"NXB", "Số lượng đầu sách", "Số lượng sách"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeNXB.getTblSLSachNXB().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    //------------------------------------
    //  Thống kê Sách
    //------------------------------------
    
    private void loadDataSach(){
        Long soLuongDauSach = thongKeService.getSoLuongDauSach();
        Long soLuongSach = thongKeService.getTongSoLuongSach();
        Long sachDangMuon = thongKeService.getTongSoLuongSachDangMuon();
        Long sachConLai = thongKeService.getTongSoLuongSachConTrongKho();
        
        viewThongKeSach.getLblDauSach().setText(String.valueOf(soLuongDauSach));
        viewThongKeSach.getLblSoLuongSach().setText(String.valueOf(soLuongSach));
        viewThongKeSach.getLblDangMuon().setText(String.valueOf(sachDangMuon));
        viewThongKeSach.getLblSachConLai().setText(String.valueOf(sachConLai));
    }
    
    public void getSachDuocMuonNhieuNhat() {
        List<SachDTO> dsSach = thongKeService.getSachDuocMuonNhieuNhat();
        String[] columnNames = {"Mã sách", "Tên sách", "Tên tác giả", "Nhà XB", "Thể loại", "Năm XB"};
        Object[][] data = new Object[dsSach.size()][6];
        for (int i = 0; i < dsSach.size(); i++) {
            SachDTO s = dsSach.get(i);
            data[i][0] = s.getMasach();
            data[i][1] = s.getTensach();
            data[i][2] = s.getTentacgia();
            data[i][3] = s.getTennxb();
            data[i][4] = s.getTentheloai();
            data[i][5] = s.getNamxb();
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        viewThongKeSach.getTblRankSach().setModel(model);
    }
    
    //------------------------------------
    //  Thống kê Tác giả
    //------------------------------------
    
    private void loadDataTG(){
        int soLuongTacGia = thongKeService.getSoLuongTacGia();
        
        viewThongKeTG.getLblTongTacGia().setText(String.valueOf(soLuongTacGia));
    }
    
    public void getSachTheoTacGia(){
        List<ThongKeTacGiaDTO> ds = thongKeService.getSachTheoTacGia();
        String[] columns = {"Mã tác giả", "Tên tác giả", "Số lượng đầu sách", "Tổng số lượng sách"};
        Object[][] data = new Object[ds.size()][4];

        for (int i = 0; i < ds.size(); i++) {
            ThongKeTacGiaDTO t = ds.get(i);
            data[i][0] = t.getMatacgia();
            data[i][1] = t.getTentacgia();
            data[i][2] = t.getSoLuongDauSach();
            data[i][3] = t.getTongSoLuongSach();
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        viewThongKeTG.getTblTacGiaSach().setModel(model);
    }
    
    //------------------------------------
    //  Thống kê Độc giả
    //------------------------------------
    
    private void loadDataDG(){
        int soLuongDocGia = thongKeService.getSoLuongDocGia();
        
        viewThongKeDG.getLblTongDocGia().setText(String.valueOf(soLuongDocGia));
    }
    
    public void getThongKeDocGia(){
        List<ThongKeDocGiaDTO> ds = thongKeService.getThongKeDocGia();
        String[] columns = {"Mã độc giả", "Tên độc giả", "Phiếu mượn", "Phiếu đã trả", "Phiếu chưa trả", "Phí phạt"};
        Object[][] data = new Object[ds.size()][6];

        for (int i = 0; i < ds.size(); i++) {
            ThongKeDocGiaDTO d = ds.get(i);
            data[i][0] = d.getMadocgia();
            data[i][1] = d.getTendocgia();
            data[i][2] = d.getTongSoPhieuMuon();
            data[i][3] = d.getSoPhieuMuonDaTra();
            data[i][4] = d.getSoPhieuMuonChuaTra();
            data[i][5] = d.getSoPhiPhat();
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        viewThongKeDG.getTblDG().setModel(model);
    }
    
    //------------------------------------
    //  Thống kê Nhân Viên
    //------------------------------------
    
    private void loadDataNV(){
        int soLuongNhanVien = thongKeService.getSoLuongNhanVien();
        
        viewThongKeNV.getLblTongNhanVien().setText(String.valueOf(soLuongNhanVien));
    }
    
    public void getTblTongSoPhieuDaTao(){
        List<ThongKeNhanVienDTO> ds = thongKeService.getPhieuMuonNhanVienDaTao();
        String[] columns = {"Mã nhân viên", "Tên nhân viên", "Số phiếu mượn đã tạo"};
        Object[][] data = new Object[ds.size()][3];

        for (int i = 0; i < ds.size(); i++) {
            ThongKeNhanVienDTO d = ds.get(i);
            data[i][0] = d.getManv();
            data[i][1] = d.getTennv();
            data[i][2] = d.getSoPhieuMuonDaTao();
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        viewThongKeNV.getTblTongSoPhieuDaTao().setModel(model);
    }
    
    //------------------------------------
    //  Thống kê Phiếu Mượn
    //------------------------------------
    
    private void loadDataPM(){
        int soLuongPhieuMuon = thongKeService.getSoLuongPhieuMuon();
        
        viewThongKePM.getLblTongPhieuMuon().setText(String.valueOf(soLuongPhieuMuon));
    }
    
    public void getSoPhieuMuonQuaHan(){
        List<ThongKePhieuMuonDTO> ds = thongKeService.getSoPhieuMuonQuaHan();
        String[] columns = {"Mã phiếu", "Mã độc giả", "Tên độc giả", "Ngày mượn", "Ngày trả dự kiến", "Số ngày quá hạn"};
        Object[][] data = new Object[ds.size()][6];
        for (int i = 0; i < ds.size(); i++) {
            ThongKePhieuMuonDTO pm = ds.get(i);
            data[i][0] = pm.getMaphieu();
            data[i][1] = pm.getMadocgia();
            data[i][2] = pm.getTendocgia();
            data[i][3] = pm.getNgaymuon();
            data[i][4] = pm.getNgaytradukien();
            data[i][5] = pm.getSoNgayQuaHan();
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        viewThongKePM.getTblPhieuMuon().setModel(model);
    }
    
    //------------------------------------
    //  Thống kê Phiếu Mượn
    //------------------------------------
    
    private void loadDataPT(){
        int soLuongPhieuTra = thongKeService.getSoLuongPhieuTra();
        
        viewThongKePT.getLblTongPhieuTra().setText(String.valueOf(soLuongPhieuTra));
    }
    
    public void getSoSachHongMat(){
        List<ThongKeSachHongMatDTO> ds = thongKeService.getSoSachHongMat();
        String[] columns = {"Mã sách", "Tên sách", "Số lượng"};
        Object[][] data = new Object[ds.size()][3];
        for (int i = 0; i < ds.size(); i++) {
            ThongKeSachHongMatDTO pm = ds.get(i);
            data[i][0] = pm.getMasach();
            data[i][1] = pm.getTensach();
            data[i][2] = pm.getSoLuong();
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        viewThongKePT.getTblSachTra().setModel(model);
    }
    
    public void getSoSachHongMatChiTiet(){
        List<ThongKeSachHongMatChiTietDTO> ds = thongKeService.getSoSachHongMatChiTiet();
        String[] columns = {"Mã phiếu", "Mã phiếu", "Tên độc giả", "Ngày mượn", "Ngày trả thực tế", "Mã sách", "Tên sách"};
        Object[][] data = new Object[ds.size()][7];
        for (int i = 0; i < ds.size(); i++) {
            ThongKeSachHongMatChiTietDTO pm = ds.get(i);
            data[i][0] = pm.getMaphieu();
            data[i][1] = pm.getMaphieu();
            data[i][2] = pm.getTendocgia();
            data[i][3] = pm.getNgaymuon();
            data[i][4] = pm.getNgaytrathucte();
            data[i][5] = pm.getMasach();
            data[i][6] = pm.getTensach();
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        viewThongKePT.getTblSachTraChiTiet().setModel(model);
    }
}

