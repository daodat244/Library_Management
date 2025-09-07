package com.example.demo.controller;

import com.example.demo.dto.NhanVienDTO;
import com.example.demo.dto.NhanVienThongKeDTO;
import com.example.demo.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping
    public ResponseEntity<List<NhanVienDTO>> getAll() {
        List<NhanVienDTO> nvList = nhanVienService.getAll();
        return ResponseEntity.ok(nvList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        NhanVienDTO nv = nhanVienService.getById(id);
        if (nv != null) {
            return ResponseEntity.ok(nv);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy nhân viên");
        }
    }

    @PreAuthorize("hasRole('QUAN_LY')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody NhanVienDTO nhanVienDTO) {
        NhanVienDTO createdNv = nhanVienService.create(nhanVienDTO);
        if (createdNv != null) {
            return ResponseEntity.ok(createdNv);
        } else {
            return ResponseEntity.status(400).body("Lỗi khi tạo nhân viên");
        }
    }

    @PreAuthorize("hasRole('QUAN_LY')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody NhanVienDTO nvUpdate) {
        NhanVienDTO updatedNv = nhanVienService.update(id, nvUpdate);
        if (updatedNv != null) {
            return ResponseEntity.ok(updatedNv);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy nhân viên");
        }
    }

    @PreAuthorize("hasRole('QUAN_LY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        nhanVienService.delete(id);
        return ResponseEntity.ok("Đã xoá nhân viên có ID = " + id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String keyword) {
        List<NhanVienDTO> nvList = nhanVienService.searchByKeyword(keyword);
        return ResponseEntity.ok(nvList);
    }
    
    @GetMapping("/thongke")
    public ResponseEntity<List<NhanVienThongKeDTO>> thongKeNhanVien() {
        return ResponseEntity.ok(nhanVienService.thongKeNhanVien());
    }
}