package com.example.demo.service;

import com.example.demo.dto.DetailedPhieuTraResponse;
import com.example.demo.dto.ChiTietPhieuTraRequest;
import com.example.demo.dto.SachHuHongMatChiTietDTO;
import com.example.demo.dto.SachHuHongMatSoLuongDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhieuTraService {

    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    @Autowired
    private PhieuTraRepository phieuTraRepository;

    @Autowired
    private ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;

    @Autowired
    private ChiTietPhieuTraRepository chiTietPhieuTraRepository;

    @Autowired
    private SachRepository sachRepository;

    @Autowired
    private DocGiaRepository docGiaRepository; // Thêm repository cho Độc giả

    @Autowired
    private NhanVienRepository nhanVienRepository; // Thêm repository cho Nhân viên

    private void validateTraSachInput(int maphieu, LocalDateTime ngayTraThucTe, PhieuMuon phieuMuon, List<ChiTietPhieuTraRequest> chiTietList) {
        if(ngayTraThucTe == null){
            throw new IllegalArgumentException("Thời gian trả thực tế không được trống!");
        }       
        if (ngayTraThucTe.isBefore(phieuMuon.getNgaymuon())) {
            throw new IllegalArgumentException("Thời gian trả thực tế không được trước thời gian mượn!");
        }
        List<ChiTietPhieuMuon> muonList = chiTietPhieuMuonRepository.findByPhieuMuonMaphieu(maphieu);
        Set<String> muonSach = muonList.stream().map(ChiTietPhieuMuon::getMasach).collect(Collectors.toSet());
        Set<String> traSach = chiTietList.stream().map(ChiTietPhieuTraRequest::getMasach).collect(Collectors.toSet());
        if (!muonSach.equals(traSach)) {
            throw new IllegalArgumentException("Danh sách sách trả không khớp với sách mượn!");
        }
    }

    @Transactional
    public PhieuTra traSach(int maphieu, LocalDateTime ngayTraThucTe, String ghiChu, List<ChiTietPhieuTraRequest> chiTietList) {
        System.out.println("Processing traSach, maphieu: " + maphieu + ", ghiChu: " + ghiChu);
        PhieuMuon phieuMuon = phieuMuonRepository.findById(maphieu)
                .orElseThrow(() -> new IllegalArgumentException("Phiếu mượn không tồn tại!"));
        if ("Đã trả".equals(phieuMuon.getTrangthai())) {
            throw new IllegalArgumentException("Phiếu mượn đã được trả!");
        }

        validateTraSachInput(maphieu, ngayTraThucTe, phieuMuon, chiTietList);

        // Tính phí trễ hạn
        long daysLate = ChronoUnit.DAYS.between(phieuMuon.getNgaytradukien(), ngayTraThucTe);
        double phiTreHan = daysLate > 0 ? daysLate * 5000 : 0;
        System.out.println("Days late: " + daysLate + ", Phi tre han: " + phiTreHan);

        // Tạo và lưu phiếu trả
        PhieuTra phieuTra = new PhieuTra();
        phieuTra.setMaphieu(maphieu);
        phieuTra.setNgaytrathucte(ngayTraThucTe);
        phieuTra.setGhichu(ghiChu);
        phieuTra.setTongphiphat(phiTreHan);
        phieuTra = phieuTraRepository.save(phieuTra); // Lưu và lấy entity với maphieutra đã sinh

        // Xử lý chi tiết trả sách
        double tongPhiPhat = phiTreHan;
        for (ChiTietPhieuTraRequest chiTietRequest : chiTietList) {
            ChiTietPhieuTra chiTiet = new ChiTietPhieuTra();
            chiTiet.setPhieuTra(phieuTra);
            chiTiet.setMasach(chiTietRequest.getMasach());
            chiTiet.setTrangthai(chiTietRequest.getTrangthai());

            if ("Hư hỏng/Mất".equals(chiTietRequest.getTrangthai())) {
                chiTiet.setPhiphat(100000.0);
                tongPhiPhat += chiTiet.getPhiphat();
            } else if ("Nguyên vẹn".equals(chiTietRequest.getTrangthai())) {
                chiTiet.setPhiphat(0.0);
                Sach sach = sachRepository.findById(chiTietRequest.getMasach())
                        .orElseThrow(() -> new IllegalArgumentException("Sách không tồn tại!"));
                sach.setSoluong(sach.getSoluong() + 1);
                sachRepository.save(sach);
            } else {
                throw new IllegalArgumentException("Trạng thái sách không hợp lệ: " + chiTietRequest.getTrangthai());
            }
            chiTietPhieuTraRepository.save(chiTiet);
        }

        // Cập nhật tổng phí phạt
        if (tongPhiPhat != phiTreHan) {
            phieuTra.setTongphiphat(tongPhiPhat);
            phieuTraRepository.save(phieuTra);
        }
        System.out.println("Saved PhieuTra, maphieutra: " + phieuTra.getMaphieutra() + ", tongphiphat: " + tongPhiPhat + ", ghichu: " + phieuTra.getGhichu());

        // Cập nhật trạng thái phiếu mượn
        phieuMuon.setTrangthai("Đã trả");
        phieuMuonRepository.save(phieuMuon);

        return phieuTra;
    }

    public List<PhieuTra> findAllPhieuTra() {
        return phieuTraRepository.findAll();
    }

    public PhieuTra findPhieuTraById(int maphieutra) {
        return phieuTraRepository.findById(maphieutra)
                .orElseThrow(() -> new IllegalArgumentException("Phiếu trả không tồn tại!"));
    }

    @Transactional(readOnly = true)
    public List<DetailedPhieuTraResponse> findAllDetailedPhieuTra() {
        return phieuTraRepository.findAll().stream()
                .map(this::convertToDetailedResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DetailedPhieuTraResponse findDetailedPhieuTraById(int maphieutra) {
        PhieuTra phieuTra = phieuTraRepository.findById(maphieutra)
                .orElseThrow(() -> new IllegalArgumentException("Phiếu trả không tồn tại!"));
        return convertToDetailedResponse(phieuTra);
    }

    @Transactional(readOnly = true)
    public List<SachHuHongMatChiTietDTO> thongKeSachHuHongMatChiTiet() {
        return phieuTraRepository.thongKeSachHuHongMatChiTiet("Hư hỏng/Mất");
    }

    @Transactional(readOnly = true)
    public List<SachHuHongMatSoLuongDTO> thongKeSachHuHongMatSoLuong() {
        return phieuTraRepository.thongKeSachHuHongMatSoLuong("Hư hỏng/Mất");
    }
    
    @Transactional(readOnly = true)
    public double tinhTongPhiPhat() {
        Double result = phieuTraRepository.tinhTongPhiPhat();
        return result != null ? result : 0.0;
    }
    
    public List<DetailedPhieuTraResponse> searchPhieuTra(String searchText, String criteria) {
        List<PhieuTra> phieuTraList;

        if (searchText == null || searchText.trim().isEmpty()) {
            phieuTraList = phieuTraRepository.findAll();
        } else {
            switch (criteria) {
                case "Mã phiếu" -> {
                    try {
                        int maphieu = Integer.parseInt(searchText);
                        PhieuTra pt = phieuTraRepository.findById(maphieu).orElse(null);
                        phieuTraList = pt != null ? List.of(pt) : List.of();
                    } catch (NumberFormatException e) {
                        phieuTraList = List.of();
                    }
                }
                case "Tên độc giả" -> {
                    phieuTraList = phieuTraRepository.findByTenDocGia(searchText);
                }
                case "Tên nhân viên" -> {
                    phieuTraList = phieuTraRepository.findByTenNhanVien(searchText);
                }
                default ->
                    phieuTraList = List.of();
            }
        }

        return phieuTraList.stream()
                .map(this::convertToDetailedResponse)
                .collect(Collectors.toList());
    }

    private DetailedPhieuTraResponse convertToDetailedResponse(PhieuTra phieuTra) {
        DetailedPhieuTraResponse response = new DetailedPhieuTraResponse();
        response.setMaphieutra(phieuTra.getMaphieutra());
        response.setMaphieu(phieuTra.getMaphieu());
        response.setNgaytrathucte(phieuTra.getNgaytrathucte());
        response.setTongphiphat(phieuTra.getTongphiphat());
        response.setGhichu(phieuTra.getGhichu());

        PhieuMuon phieuMuon = phieuMuonRepository.findById(phieuTra.getMaphieu())
                .orElseThrow(() -> new IllegalArgumentException("Phiếu mượn không tồn tại!"));

        response.setMadocgia(phieuMuon.getMadocgia());
        DocGia docGia = docGiaRepository.findById(phieuMuon.getMadocgia())
                .orElseThrow(() -> new IllegalArgumentException("Độc giả không tồn tại!"));
        response.setTendocgia(docGia.getTendocgia());

        NhanVien nhanVien = nhanVienRepository.findById(phieuMuon.getManv())
                .orElseThrow(() -> new IllegalArgumentException("Nhân viên không tồn tại!"));
        response.setTennv(nhanVien.getTennv());

        response.setNgaymuon(phieuMuon.getNgaymuon());
        response.setNgaytradukien(phieuMuon.getNgaytradukien());
        response.setTrangthai(phieuMuon.getTrangthai());

        List<ChiTietPhieuTra> chiTietList = chiTietPhieuTraRepository.findByPhieuTraMaphieutra(phieuTra.getMaphieutra());
        List<DetailedPhieuTraResponse.ChiTietTraSach> chiTietTraSachList = chiTietList.stream().map(chiTiet -> {
            DetailedPhieuTraResponse.ChiTietTraSach chiTietTraSach = new DetailedPhieuTraResponse.ChiTietTraSach();
            chiTietTraSach.setMasach(chiTiet.getMasach());
            chiTietTraSach.setTrangthai(chiTiet.getTrangthai());
            chiTietTraSach.setPhiphat(chiTiet.getPhiphat());
            return chiTietTraSach;
        }).collect(Collectors.toList());
        response.setChiTietTraSachList(chiTietTraSachList);

        return response;
    }
}
