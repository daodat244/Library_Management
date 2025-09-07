package com.example.demo.controller;

import com.example.demo.dto.NhaXuatBanDTO;
import com.example.demo.dto.NhaXuatBanThongKeDTO;
import com.example.demo.service.NhaXuatBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nhaxuatban")
public class NhaXuatBanController {
    @Autowired
    private NhaXuatBanService service;

    @GetMapping
    public ResponseEntity<List<NhaXuatBanDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<NhaXuatBanDTO> add(@RequestBody NhaXuatBanDTO nhaXuatBanDTO) {
        return ResponseEntity.ok(service.add(nhaXuatBanDTO));
    }

    @PutMapping("/{manxb}")
    public ResponseEntity<NhaXuatBanDTO> update(@PathVariable int manxb, @RequestBody NhaXuatBanDTO nhaXuatBanDTO) {
        return ResponseEntity.ok(service.update(manxb, nhaXuatBanDTO));
    }

    @DeleteMapping("/{manxb}")
    public ResponseEntity<?> delete(@PathVariable int manxb) {
        try {
            service.delete(manxb);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<NhaXuatBanDTO>> search(@RequestParam String searchText, @RequestParam String criteria) {
        return ResponseEntity.ok(service.search(searchText, criteria));
    }

    @GetMapping("/thongke")
    public ResponseEntity<List<NhaXuatBanThongKeDTO>> getThongKe() {
        return ResponseEntity.ok(service.getThongKeNXB());
    }
}