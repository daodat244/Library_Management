package com.example.demo.controller;

import com.example.demo.dto.SachDTO;
import com.example.demo.dto.SachInputDTO;
import com.example.demo.entity.Sach;
import com.example.demo.service.SachService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sach")
public class SachController {

    @Autowired
    private SachService sachService;

    @GetMapping
    public ResponseEntity<List<SachDTO>> getAllSach() {
        return ResponseEntity.ok(sachService.getAllSach());
    }

    @GetMapping("/{masach}")
    public ResponseEntity<?> getSachById(@PathVariable String masach) {
        try {
            return ResponseEntity.ok(sachService.getSachById(masach));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addSach(@Valid @RequestBody SachInputDTO sachInput) {
        try {
            Sach sach = sachService.convertToEntity(sachInput);
            return ResponseEntity.ok(sachService.addSach(sach));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{masach}")
    public ResponseEntity<?> updateSach(@PathVariable String masach, @Valid @RequestBody SachInputDTO sachInput) {
        try {
            return ResponseEntity.ok(sachService.updateSach(masach, sachInput));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{masach}")
    public ResponseEntity<?> deleteSach(@PathVariable String masach) {
        try {
            sachService.deleteSach(masach);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/giam-soluong/{masach}")
    public ResponseEntity<?> giamSoLuongSach(@PathVariable String masach) {
        try {
            return ResponseEntity.ok(sachService.giamSoLuongSach(masach));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/tang-soluong/{masach}")
    public ResponseEntity<?> tangSoLuongSach(@PathVariable String masach) {
        try {
            return ResponseEntity.ok(sachService.tangSoLuongSach(masach));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<SachDTO>> search(
            @RequestParam String searchText,
            @RequestParam String criteria) {
        return ResponseEntity.ok(sachService.search(searchText, criteria));
    }

    @GetMapping("/searchByIds")
    public ResponseEntity<List<SachDTO>> getSachByIds(@RequestParam("ids") String ids) {
        String[] idArray = ids.split(",");
        return ResponseEntity.ok(sachService.getSachByIds(idArray));
    }

    @GetMapping("/thongke/so-luong-dau-sach")
    public ResponseEntity<Long> getSoLuongDauSach() {
        return ResponseEntity.ok(sachService.getSoLuongDauSach());
    }

    @GetMapping("/thongke/tong-so-luong-sach")
    public ResponseEntity<Long> getTongSoLuongSach() {
        return ResponseEntity.ok(sachService.getTongSoLuongSach());
    }

    @GetMapping("/thongke/tong-so-luong-sach-dang-muon")
    public ResponseEntity<Long> getTongSoLuongSachDangMuon() {
        return ResponseEntity.ok(sachService.getTongSoLuongSachDangMuon());
    }

    @GetMapping("/thongke/tong-so-luong-sach-con-trong-kho")
    public ResponseEntity<Long> getTongSoLuongSachConTrongKho() {
        return ResponseEntity.ok(sachService.getTongSoLuongSachConTrongKho());
    }

    @GetMapping("/thongke/sach-duoc-muon-nhieu-nhat")
    public ResponseEntity<List<SachDTO>> getSachDuocMuonNhieuNhat() {
        return ResponseEntity.ok(sachService.getSachDuocMuonNhieuNhat());
    }
}