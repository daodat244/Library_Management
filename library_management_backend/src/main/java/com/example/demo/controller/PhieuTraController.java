package com.example.demo.controller;

import com.example.demo.dto.DetailedPhieuTraResponse;
import com.example.demo.dto.PhieuTraRequest;
import com.example.demo.dto.SachHuHongMatChiTietDTO;
import com.example.demo.dto.SachHuHongMatSoLuongDTO;
import com.example.demo.entity.PhieuTra;
import com.example.demo.service.PhieuTraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/phieutra")
public class PhieuTraController {

    @Autowired
    private PhieuTraService phieuTraService;

    @PostMapping
    public ResponseEntity<?> traSach(@RequestBody PhieuTraRequest request) {
        try {
            PhieuTra phieuTra = phieuTraService.traSach(
                    request.getMaphieu(),
                    request.getNgaytrathucte(),
                    request.getGhiChu(),
                    request.getChiTietList()
            );
            return ResponseEntity.ok(phieuTra);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllPhieuTra() {
        try {
            List<PhieuTra> phieuTraList = phieuTraService.findAllPhieuTra();
            return ResponseEntity.ok(phieuTraList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{maphieutra}")
    public ResponseEntity<?> findPhieuTraById(@PathVariable int maphieutra) {
        try {
            PhieuTra phieuTra = phieuTraService.findPhieuTraById(maphieutra);
            return ResponseEntity.ok(phieuTra);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchPhieuTra(
            @RequestParam String searchText,
            @RequestParam String criteria) {
        try {
            List<DetailedPhieuTraResponse> result = phieuTraService.searchPhieuTra(searchText, criteria);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/detailed/{maphieutra}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> findDetailedPhieuTraById(@PathVariable int maphieutra) {
        try {
            DetailedPhieuTraResponse phieuTra = phieuTraService.findDetailedPhieuTraById(maphieutra);
            return ResponseEntity.ok(phieuTra);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/detailed")
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllDetailedPhieuTra() {
        try {
            List<DetailedPhieuTraResponse> phieuTraList = phieuTraService.findAllDetailedPhieuTra();
            return ResponseEntity.ok(phieuTraList);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @GetMapping("/thongke/huhongmat/chitiet")
    @Transactional(readOnly = true)
    public ResponseEntity<?> thongKeSachHuHongMatChiTiet() {
        try {
            List<SachHuHongMatChiTietDTO> result = phieuTraService.thongKeSachHuHongMatChiTiet();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/thongke/huhongmat/soluong")
    @Transactional(readOnly = true)
    public ResponseEntity<?> thongKeSachHuHongMatSoLuong() {
        try {
            List<SachHuHongMatSoLuongDTO> result = phieuTraService.thongKeSachHuHongMatSoLuong();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @GetMapping("/thongke/tongphiphat")
    @Transactional(readOnly = true)
    public ResponseEntity<?> tinhTongPhiPhat() {
        try {
            double tongPhiPhat = phieuTraService.tinhTongPhiPhat();
            return ResponseEntity.ok(tongPhiPhat);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
