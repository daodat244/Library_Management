package com.example.demo.controller;

import com.example.demo.dto.TacGiaDTO;
import com.example.demo.dto.TacGiaThongKeDTO;
import com.example.demo.service.TacGiaService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tacgia")
public class TacGiaController {

    @Autowired
    private TacGiaService service;

    @GetMapping
    public ResponseEntity<List<TacGiaDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
   

    @PostMapping
    public ResponseEntity<TacGiaDTO> add(@RequestBody TacGiaDTO tacGiaDTO) {
        return ResponseEntity.status(300).body(service.add(tacGiaDTO));
    }

    @PutMapping("/{matacgia}")
    public ResponseEntity<TacGiaDTO> update(@PathVariable int matacgia, @RequestBody TacGiaDTO tacGiaDTO) {
        return ResponseEntity.ok(service.update(matacgia, tacGiaDTO));
    }

    @DeleteMapping("/{matacgia}")
    public ResponseEntity<?> delete(@PathVariable int matacgia) {
        try {
            service.delete(matacgia);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TacGiaDTO>> search(@RequestParam String searchText, @RequestParam String criteria) {
        return ResponseEntity.ok(service.search(searchText, criteria));
    }

    @GetMapping("/thongke")
    public ResponseEntity<List<TacGiaThongKeDTO>> getThongKe() {
        return ResponseEntity.ok(service.getThongKeTacGia());
    }
}
