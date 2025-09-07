package com.example.demo.repository;

import com.example.demo.entity.ChiTietPhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChiTietPhieuMuonRepository extends JpaRepository<ChiTietPhieuMuon, Integer> {
    List<ChiTietPhieuMuon> findByPhieuMuonMaphieu(int maphieu);
    
    @Query("SELECT SUM(ct.soluong) FROM ChiTietPhieuMuon ct JOIN ct.phieuMuon pm WHERE pm.trangthai = :trangthai")
    Long sumSoLuongSachDangMuon(@Param("trangthai") String trangthai);
}
