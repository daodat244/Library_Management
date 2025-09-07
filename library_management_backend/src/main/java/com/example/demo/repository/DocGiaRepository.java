package com.example.demo.repository;

import com.example.demo.entity.DocGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface DocGiaRepository extends JpaRepository<DocGia, Integer> {

    boolean existsByTendocgia(String tendocgia);

    boolean existsByTendocgiaAndMadocgiaNot(String tendocgia, Integer madocgia);

    List<DocGia> findByTendocgiaContainingIgnoreCase(String tendocgia);

    @Query("SELECT new com.example.demo.dto.DocGiaThongKeDTO("
            + "dg.madocgia, dg.tendocgia, "
            + "COUNT(pm.maphieu), "
            + "SUM(CASE WHEN pm.trangthai = :daTra THEN 1 ELSE 0 END), "
            + "SUM(CASE WHEN pm.trangthai = :chuaTra THEN 1 ELSE 0 END), "
            + "COALESCE(SUM(pt.tongphiphat), 0.0)) "
            + "FROM DocGia dg "
            + "LEFT JOIN PhieuMuon pm ON dg.madocgia = pm.madocgia "
            + "LEFT JOIN PhieuTra pt ON pm.maphieu = pt.maphieu "
            + "GROUP BY dg.madocgia, dg.tendocgia "
            + "ORDER BY COUNT(pm.maphieu) DESC")
    List<com.example.demo.dto.DocGiaThongKeDTO> thongKeDocGia(
            @Param("daTra") String daTra,
            @Param("chuaTra") String chuaTra);
}
