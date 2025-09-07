package com.example.demo.repository;

import com.example.demo.dto.NhanVienThongKeDTO;
import com.example.demo.entity.NhanVien;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    List<NhanVien> findByTennvContainingIgnoreCase(String tennv);
    Optional<NhanVien> findByUsername(String username);
    
    @Query("SELECT new com.example.demo.dto.NhanVienThongKeDTO(" +
           "nv.manv, nv.tennv, COUNT(pm)) " +
           "FROM NhanVien nv LEFT JOIN PhieuMuon pm ON nv.manv = pm.nhanVien.manv " +
           "GROUP BY nv.manv, nv.tennv")
    List<NhanVienThongKeDTO> thongKeNhanVien();
}
