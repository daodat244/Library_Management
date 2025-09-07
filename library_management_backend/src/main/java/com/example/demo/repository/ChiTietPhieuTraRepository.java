package com.example.demo.repository;

import com.example.demo.entity.ChiTietPhieuTra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChiTietPhieuTraRepository extends JpaRepository<ChiTietPhieuTra, Integer> {
    List<ChiTietPhieuTra> findByPhieuTraMaphieutra(int maphieutra);
}
