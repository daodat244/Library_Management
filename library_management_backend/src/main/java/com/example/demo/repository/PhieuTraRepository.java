package com.example.demo.repository;

import com.example.demo.dto.SachHuHongMatChiTietDTO;
import com.example.demo.dto.SachHuHongMatSoLuongDTO;
import com.example.demo.entity.PhieuTra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhieuTraRepository extends JpaRepository<PhieuTra, Integer> {

    List<PhieuTra> findByMaphieu(int maphieu);

    @Query("SELECT pt FROM PhieuTra pt JOIN PhieuMuon pm ON pt.maphieu = pm.maphieu WHERE pm.madocgia = :madocgia")
    List<PhieuTra> findByMadocgia(int madocgia);
    
    @Query("SELECT pt FROM PhieuTra pt JOIN PhieuMuon pm ON pt.maphieu = pm.maphieu JOIN DocGia dg ON pm.madocgia = dg.madocgia WHERE LOWER(dg.tendocgia) LIKE LOWER(CONCAT('%', :tendocgia, '%'))")
    List<PhieuTra> findByTenDocGia(@Param("tendocgia") String tendocgia);

    @Query("SELECT pt FROM PhieuTra pt JOIN PhieuMuon pm ON pt.maphieu = pm.maphieu JOIN NhanVien nv ON pm.manv = nv.manv WHERE LOWER(nv.tennv) LIKE LOWER(CONCAT('%', :tennv, '%'))")
    List<PhieuTra> findByTenNhanVien(@Param("tennv") String tennv);
    
    @Query("SELECT new com.example.demo.dto.SachHuHongMatChiTietDTO(" +
           "pt.maphieutra, pt.maphieu, dg.tendocgia, pm.ngaymuon, pt.ngaytrathucte, ctpt.masach, s.tensach) " +
           "FROM PhieuTra pt " +
           "JOIN PhieuMuon pm ON pt.maphieu = pm.maphieu " +
           "JOIN DocGia dg ON pm.madocgia = dg.madocgia " +
           "JOIN ChiTietPhieuTra ctpt ON pt.maphieutra = ctpt.phieuTra.maphieutra " +
           "JOIN Sach s ON ctpt.masach = s.masach " +
           "WHERE ctpt.trangthai = :trangthai")
    List<SachHuHongMatChiTietDTO> thongKeSachHuHongMatChiTiet(@Param("trangthai") String trangthai);

    @Query("SELECT new com.example.demo.dto.SachHuHongMatSoLuongDTO(" +
           "ctpt.masach, s.tensach, COUNT(ctpt)) " +
           "FROM ChiTietPhieuTra ctpt " +
           "JOIN Sach s ON ctpt.masach = s.masach " +
           "WHERE ctpt.trangthai = :trangthai " +
           "GROUP BY ctpt.masach, s.tensach")
    List<SachHuHongMatSoLuongDTO> thongKeSachHuHongMatSoLuong(@Param("trangthai") String trangthai);
    
    @Query("SELECT COALESCE(SUM(pt.tongphiphat), 0.0) FROM PhieuTra pt")
    Double tinhTongPhiPhat();
}
