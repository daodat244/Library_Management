/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.repository;

import com.example.demo.entity.TacGia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface TacGiaRepository extends JpaRepository<TacGia, Integer> {
    boolean existsByTentacgia(String tentacgia);
    boolean existsByTentacgiaAndMatacgiaNot(String tentacgia, Integer matacgia);
    boolean existsByEmail(String email);
    boolean existsByEmailAndMatacgiaNot(String email, Integer matacgia);
    boolean existsBySdt(String sdt);
    boolean existsBySdtAndMatacgiaNot(String sdt, Integer matacgia);
    List<TacGia> findByTentacgiaContainingIgnoreCase(String tentacgia);
    
    @Query("SELECT new com.example.demo.dto.TacGiaThongKeDTO(" +
           "tg.matacgia, tg.tentacgia, COUNT(s), COALESCE(SUM(s.soluong), 0)) " +
           "FROM TacGia tg LEFT JOIN tg.sachList s " +
           "GROUP BY tg.matacgia, tg.tentacgia")
    List<com.example.demo.dto.TacGiaThongKeDTO> thongKeTacGia();
}
