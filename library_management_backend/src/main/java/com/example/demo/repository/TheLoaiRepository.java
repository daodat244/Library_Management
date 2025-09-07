package com.example.demo.repository;

import com.example.demo.entity.TheLoai;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer> {
    boolean existsByTentheloai(String tentheloai);
    boolean existsByTentheloaiAndMatheloaiNot(String tentheloai, Integer matheloai);
    List<TheLoai> findByTentheloaiContainingIgnoreCase(String tentheloai);

    @Query("SELECT new com.example.demo.dto.ThongKeTheLoaiDTO(" +
           "t.matheloai, t.tentheloai, COUNT(s), COALESCE(SUM(s.soluong), 0)) " +
           "FROM TheLoai t LEFT JOIN t.sachList s " +
           "GROUP BY t.matheloai, t.tentheloai " +
           "ORDER BY t.tentheloai")
    List<com.example.demo.dto.ThongKeTheLoaiDTO> thongKeTheLoai();
}
