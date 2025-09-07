package com.example.demo.repository;

import com.example.demo.dto.ThongKePhieuMuonQuaHanDTO;
import com.example.demo.entity.PhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhieuMuonRepository extends JpaRepository<PhieuMuon, Integer> {

    List<PhieuMuon> findByMadocgia(int madocgia);

    @EntityGraph(attributePaths = {"docGia", "nhanVien", "chiTietPhieuMuonList"})
    @Override
    Optional<PhieuMuon> findById(Integer maphieu);

    @EntityGraph(attributePaths = {"docGia", "nhanVien", "chiTietPhieuMuonList"})
    @Override
    List<PhieuMuon> findAll();

    List<PhieuMuon> findByTrangthai(String trangthai);

    @Query("SELECT new com.example.demo.dto.ThongKePhieuMuonQuaHanDTO("
            + "pm.maphieu, pm.madocgia, dg.tendocgia, pm.ngaymuon, pm.ngaytradukien, "
            + "timestampdiff(DAY, pm.ngaytradukien, CURRENT_TIMESTAMP)) "
            + "FROM PhieuMuon pm JOIN pm.docGia dg "
            + "WHERE pm.trangthai = :trangthai AND pm.ngaytradukien < CURRENT_TIMESTAMP")
    List<ThongKePhieuMuonQuaHanDTO> findPhieuMuonQuaHan(@Param("trangthai") String trangthai);

    @EntityGraph(attributePaths = {"docGia", "nhanVien", "chiTietPhieuMuonList"})
    @Query("SELECT pm FROM PhieuMuon pm JOIN pm.docGia dg WHERE lower(dg.tendocgia) LIKE lower(concat('%', :tendocgia, '%'))")
    List<PhieuMuon> findByDocGiaTendocgiaContainingIgnoreCase(@Param("tendocgia") String tendocgia);

    @EntityGraph(attributePaths = {"docGia", "nhanVien", "chiTietPhieuMuonList"})
    @Query("SELECT pm FROM PhieuMuon pm JOIN pm.nhanVien nv WHERE lower(nv.tennv) LIKE lower(concat('%', :tennv, '%'))")
    List<PhieuMuon> findByNhanVienTennvContainingIgnoreCase(@Param("tennv") String tennv);
    
}
