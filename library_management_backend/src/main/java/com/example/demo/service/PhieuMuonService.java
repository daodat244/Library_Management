  package com.example.demo.service;

import com.example.demo.dto.PhieuMuonResponse;
import com.example.demo.dto.ThongKePhieuMuonQuaHanDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@Service
public class PhieuMuonService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(PhieuMuonService.class);

    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    @Autowired
    private ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;

    @Autowired
    private SachRepository sachRepository;

    @Autowired
    private DocGiaRepository docGiaRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    private void validateInput(int madocgia, int manv, List<String> maSachList, LocalDateTime ngayMuon, LocalDateTime ngayTraDuKien) {
        logger.debug("Validating input: madocgia={}, manv={}, maSachList={}, ngayMuon={}, ngayTraDuKien={}",
                madocgia, manv, maSachList, ngayMuon, ngayTraDuKien);
        if (ngayMuon == null) {
            logger.error("Validation failed: Ngày mượn không được null!");
            throw new IllegalArgumentException("Ngày mượn không được null!");
        }
        if (ngayTraDuKien == null) {
            logger.error("Validation failed: Ngày trả dự kiến không được null!");
            throw new IllegalArgumentException("Ngày trả dự kiến không được null!");
        }
        if (!docGiaRepository.existsById(madocgia)) {
            logger.error("Validation failed: Độc giả không tồn tại! madocgia={}", madocgia);
            throw new IllegalArgumentException("Độc giả không tồn tại!");
        }
        if (!nhanVienRepository.existsById(manv)) {
            logger.error("Validation failed: Nhân viên không tồn tại! manv={}", manv);
            throw new IllegalArgumentException("Nhân viên không tồn tại!");
        }
        if (maSachList == null || maSachList.isEmpty()) {
            logger.error("Validation failed: Danh sách sách không được rỗng!");
            throw new IllegalArgumentException("Danh sách sách không được rỗng!");
        }
        if (maSachList.size() > 5) {
            logger.error("Validation failed: Không được mượn quá 5 đầu sách! size={}", maSachList.size());
            throw new IllegalArgumentException("Không được mượn quá 5 đầu sách!");
        }
        Set<String> uniqueSach = new HashSet<>(maSachList);
        if (uniqueSach.size() < maSachList.size()) {
            logger.error("Validation failed: Danh sách sách có trùng lặp! maSachList={}", maSachList);
            throw new IllegalArgumentException("Danh sách sách không được có trùng lặp!");
        }
        for (String masach : maSachList) {
            Sach sach = sachRepository.findById(masach)
                    .orElseThrow(() -> {
                        logger.error("Validation failed: Sách không tồn tại! masach={}", masach);
                        return new IllegalArgumentException("Sách " + masach + " không tồn tại!");
                    });
            if (sach.getSoluong() <= 0) {
                logger.error("Validation failed: Sách đã hết! masach={}", masach);
                throw new IllegalArgumentException("Sách " + masach + " đã hết!");
            }
        }
        if (ngayMuon.isAfter(ngayTraDuKien)) {
            logger.error("Validation failed: Thời gian trả dự kiến phải sau thời gian mượn! ngayMuon={}, ngayTraDuKien={}",
                    ngayMuon, ngayTraDuKien);
            throw new IllegalArgumentException("Thời gian trả dự kiến phải sau thời gian mượn!");
        }
        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(ngayMuon, ngayTraDuKien);
        if (daysDifference > 30) {
            logger.error("Validation failed: Thời gian trả dự kiến quá 30 ngày! daysDifference={}", daysDifference);
            throw new IllegalArgumentException("Thời gian trả dự kiến không được quá 1 tháng (30 ngày)!");
        }
    }

    @Transactional
    public PhieuMuon createPhieuMuon(int madocgia, int manv, List<String> maSachList, LocalDateTime ngayMuon, LocalDateTime ngayTraDuKien) {
        logger.info("Creating PhieuMuon: madocgia={}, manv={}, maSachList={}, ngayMuon={}, ngayTraDuKien={}",
                madocgia, manv, maSachList, ngayMuon, ngayTraDuKien);
        try {
            validateInput(madocgia, manv, maSachList, ngayMuon, ngayTraDuKien);

            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMadocgia(madocgia);
            phieuMuon.setManv(manv);
            phieuMuon.setNgaymuon(ngayMuon);
            phieuMuon.setNgaytradukien(ngayTraDuKien);
            phieuMuon.setTrangthai("Chưa trả");
            logger.debug("Saving PhieuMuon: {}", phieuMuon);
            phieuMuonRepository.save(phieuMuon);

            // Khóa bi quan cho các sách
            for (String masach : maSachList) {
                Sach sach = entityManager.find(Sach.class, masach, LockModeType.PESSIMISTIC_WRITE);
                if (sach.getSoluong() <= 0) {
                    throw new IllegalArgumentException("Sách " + masach + " đã hết!");
                }

                ChiTietPhieuMuon chiTiet = new ChiTietPhieuMuon();
                chiTiet.setPhieuMuon(phieuMuon);
                chiTiet.setMasach(masach);
                chiTiet.setSoluong(1);
                logger.debug("Saving ChiTietPhieuMuon: masach={}", masach);
                chiTietPhieuMuonRepository.save(chiTiet);

                sach.setSoluong(sach.getSoluong() - 1);
                logger.debug("Updating Sach: masach={}, soluong={}", masach, sach.getSoluong());
                sachRepository.save(sach);
            }

            logger.info("PhieuMuon created successfully: maphieu={}", phieuMuon.getMaphieu());
            return phieuMuon;
        } catch (Exception e) {
            logger.error("Error creating PhieuMuon: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public boolean updatePhieuMuon(int maphieu, int madocgia, int manv, List<String> maSachList, LocalDateTime ngayMuon, LocalDateTime ngayTraDuKien) {
        logger.info("Updating PhieuMuon: maphieu={}, madocgia={}, manv={}, maSachList={}, ngayMuon={}, ngayTraDuKien={}",
                maphieu, madocgia, manv, maSachList, ngayMuon, ngayTraDuKien);
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                PhieuMuon phieuMuon = phieuMuonRepository.findById(maphieu)
                        .orElseThrow(() -> new IllegalArgumentException("Phiếu mượn không tồn tại!"));
                if ("Đã trả".equals(phieuMuon.getTrangthai())) {
                    throw new IllegalArgumentException("Không thể sửa phiếu mượn đã trả!");
                }

                // Khôi phục số lượng sách cũ
                List<ChiTietPhieuMuon> oldChiTietList = chiTietPhieuMuonRepository.findByPhieuMuonMaphieu(maphieu);
                for (ChiTietPhieuMuon chiTiet : oldChiTietList) {
                    Sach sach = entityManager.find(Sach.class, chiTiet.getMasach(), LockModeType.PESSIMISTIC_WRITE);
                    sach.setSoluong(sach.getSoluong() + chiTiet.getSoluong());
                    logger.debug("Restoring Sach: masach={}, soluong={}", chiTiet.getMasach(), sach.getSoluong());
                    sachRepository.save(sach);
                }
                logger.debug("Deleting old ChiTietPhieuMuon: count={}", oldChiTietList.size());
                chiTietPhieuMuonRepository.deleteAll(oldChiTietList);
                phieuMuon.getChiTietPhieuMuonList().clear();

                // Cập nhật phiếu mượn
                validateInput(madocgia, manv, maSachList, ngayMuon, ngayTraDuKien);
                phieuMuon.setMadocgia(madocgia);
                phieuMuon.setManv(manv);
                phieuMuon.setNgaymuon(ngayMuon);
                phieuMuon.setNgaytradukien(ngayTraDuKien);
                logger.debug("Saving updated PhieuMuon: {}", phieuMuon);
                phieuMuonRepository.saveAndFlush(phieuMuon);

                // Thêm chi tiết mới
                List<String> uniqueMaSach = maSachList.stream().distinct().collect(Collectors.toList());
                for (String masach : uniqueMaSach) {
                    Sach sach = entityManager.find(Sach.class, masach, LockModeType.PESSIMISTIC_WRITE);
                    if (sach.getSoluong() <= 0) {
                        throw new IllegalArgumentException("Sách " + masach + " đã hết!");
                    }
                    ChiTietPhieuMuon chiTiet = new ChiTietPhieuMuon();
                    chiTiet.setPhieuMuon(phieuMuon);
                    chiTiet.setMasach(masach);
                    chiTiet.setSoluong(1);
                    logger.debug("Saving new ChiTietPhieuMuon: masach={}", masach);
                    chiTietPhieuMuonRepository.save(chiTiet);
                    sach.setSoluong(sach.getSoluong() - 1);
                    logger.debug("Updating Sach: masach={}, soluong={}", masach, sach.getSoluong());
                    sachRepository.save(sach);
                }

                logger.info("PhieuMuon updated successfully: maphieu={}", maphieu);
                return true;
            } catch (ObjectOptimisticLockingFailureException e) {
                logger.warn("Optimistic locking failure on attempt {}: {}", attempt, e.getMessage());
                if (attempt == maxAttempts) {
                    logger.error("Failed to update PhieuMuon after {} attempts: {}", maxAttempts, e.getMessage(), e);
                    throw new RuntimeException("Không thể cập nhật phiếu mượn sau " + maxAttempts + " lần thử: " + e.getMessage());
                }
                try {
                    Thread.sleep(100 * attempt);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception e) {
                logger.error("Error updating PhieuMuon: {}", e.getMessage(), e);
                throw e;
            }
        }
        return false;
    }

    @Transactional
    public void deletePhieuMuon(int maphieu) {
        PhieuMuon phieuMuon = phieuMuonRepository.findById(maphieu)
                .orElseThrow(() -> new IllegalArgumentException("Phiếu mượn không tồn tại!"));
        if ("Đã trả".equals(phieuMuon.getTrangthai())) {
            throw new IllegalArgumentException("Không thể xóa phiếu mượn đã trả!");
        }

        // Hoàn trả số lượng sách
        List<ChiTietPhieuMuon> chiTietList = chiTietPhieuMuonRepository.findByPhieuMuonMaphieu(maphieu);
        for (ChiTietPhieuMuon chiTiet : chiTietList) {
            Sach sach = sachRepository.findById(chiTiet.getMasach()).get();
            sach.setSoluong(sach.getSoluong() + chiTiet.getSoluong());
            sachRepository.save(sach);
        }

        // Xóa chi tiết và phiếu mượn
        chiTietPhieuMuonRepository.deleteAll(chiTietList);
        phieuMuonRepository.delete(phieuMuon);
    }
    
    public List<PhieuMuon> findAllPhieuMuon() {
        return phieuMuonRepository.findAll();
    }

    public PhieuMuon findPhieuMuonById(int maphieu) {
        return phieuMuonRepository.findById(maphieu)
                .orElseThrow(() -> new IllegalArgumentException("Phiếu mượn không tồn tại!"));
    }

    public PhieuMuonResponse convertToResponse(PhieuMuon phieuMuon) {
        PhieuMuonResponse response = new PhieuMuonResponse();
        response.setMaphieu(phieuMuon.getMaphieu());
        response.setMadocgia(phieuMuon.getMadocgia());
        response.setManv(phieuMuon.getManv());
        response.setNgaymuon(phieuMuon.getNgaymuon());
        response.setNgaytradukien(phieuMuon.getNgaytradukien());
        response.setTrangthai(phieuMuon.getTrangthai());

        // Lấy tendocgia từ DocGia
        if (phieuMuon.getDocGia() != null) {
            response.setTendocgia(phieuMuon.getDocGia().getTendocgia());
        } else {
            // Nếu quan hệ không tải được, truy vấn riêng
            DocGia docGia = docGiaRepository.findById(phieuMuon.getMadocgia()).orElse(null);
            response.setTendocgia(docGia != null ? docGia.getTendocgia() : null);
        }

        // Lấy tennv từ NhanVien
        if (phieuMuon.getNhanVien() != null) {
            response.setTennv(phieuMuon.getNhanVien().getTennv());
        } else {
            // Nếu quan hệ không tải được, truy vấn riêng
            NhanVien nhanVien = nhanVienRepository.findById(phieuMuon.getManv()).orElse(null);
            response.setTennv(nhanVien != null ? nhanVien.getTennv() : null);
        }

        List<String> maSachList = chiTietPhieuMuonRepository.findByPhieuMuonMaphieu(phieuMuon.getMaphieu())
                .stream()
                .sorted(Comparator.comparingInt(ChiTietPhieuMuon::getId)) // Sắp xếp theo id
                .map(ChiTietPhieuMuon::getMasach)
                .collect(Collectors.toList());
        response.setMaSachList(maSachList);
        return response;
    }

    public List<PhieuMuonResponse> findAllPhieuMuonResponse() {
        return phieuMuonRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public PhieuMuonResponse findPhieuMuonByIdResponse(int maphieu) {
        PhieuMuon phieuMuon = phieuMuonRepository.findById(maphieu)
                .orElseThrow(() -> new IllegalArgumentException("Phiếu mượn không tồn tại!"));
        return convertToResponse(phieuMuon);
    }

    public List<PhieuMuonResponse> searchPhieuMuon(String searchText, String criteria) {
        List<PhieuMuon> phieuMuonList;

        if (searchText == null || searchText.trim().isEmpty()) {
            phieuMuonList = phieuMuonRepository.findAll();
        } else {
            switch (criteria) {
                case "Mã phiếu" -> {
                    try {
                        int maphieu = Integer.parseInt(searchText);
                        PhieuMuon pm = phieuMuonRepository.findById(maphieu).orElse(null);
                        phieuMuonList = pm != null ? List.of(pm) : List.of();
                    } catch (NumberFormatException e) {
                        phieuMuonList = List.of();
                    }
                }
                case "Tên độc giả" -> {
                    phieuMuonList = phieuMuonRepository.findByDocGiaTendocgiaContainingIgnoreCase(searchText);
                }
                case "Tên nhân viên" -> {
                    phieuMuonList = phieuMuonRepository.findByNhanVienTennvContainingIgnoreCase(searchText);
                }
                default ->
                    phieuMuonList = List.of();
            }
        }

        return phieuMuonList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ThongKePhieuMuonQuaHanDTO> getPhieuMuonQuaHan() {
        return phieuMuonRepository.findPhieuMuonQuaHan("Chưa trả");
    }
}
