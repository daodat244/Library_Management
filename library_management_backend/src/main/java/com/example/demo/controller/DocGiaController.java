package com.example.demo.controller;

import com.example.demo.dto.DocGiaThongKeDTO;
import com.example.demo.entity.DocGia;
import com.example.demo.service.DocGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docgia")
public class DocGiaController {

    @Autowired
    private DocGiaService service;

    @GetMapping
    public List<DocGia> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocGia> getById(@PathVariable int id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DocGia add(@RequestBody DocGia dg) {
        return service.add(dg);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocGia> update(@PathVariable int id, @RequestBody DocGia dg) {
        return ResponseEntity.ok(service.update(id, dg));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<DocGia> search(@RequestParam String searchText, @RequestParam String criteria) {
        return service.search(searchText, criteria);
    }

    @GetMapping("/thongke")
    public ResponseEntity<List<DocGiaThongKeDTO>> getThongKeDocGia() {
        return ResponseEntity.ok(service.getThongKeDocGia());
    }
}
