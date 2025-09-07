package com.example.demo.controller;

import com.example.demo.dto.TheLoaiDTO;
import com.example.demo.dto.ThongKeTheLoaiDTO;
import com.example.demo.service.TheLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/theloai")
public class TheLoaiController {
    @Autowired
    private TheLoaiService service;

    @GetMapping
    public ResponseEntity<List<TheLoaiDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<TheLoaiDTO> add(@RequestBody TheLoaiDTO theLoaiDTO) {
        return ResponseEntity.ok(service.add(theLoaiDTO));
    }

    @PutMapping("/{matheloai}")
    public ResponseEntity<TheLoaiDTO> update(@PathVariable int matheloai, @RequestBody TheLoaiDTO theLoaiDTO) {
        return ResponseEntity.ok(service.update(matheloai, theLoaiDTO));
    }

    @DeleteMapping("/{matheloai}")
    public ResponseEntity<?> delete(@PathVariable int matheloai) {
        try {
            service.delete(matheloai);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TheLoaiDTO>> search(@RequestParam String searchText, @RequestParam String criteria) {
        return ResponseEntity.ok(service.search(searchText, criteria));
    }

    @GetMapping("/thongke")
    public ResponseEntity<List<ThongKeTheLoaiDTO>> thongKeTheLoai() {
        return ResponseEntity.ok(service.thongKeTheLoai());
    }
}
