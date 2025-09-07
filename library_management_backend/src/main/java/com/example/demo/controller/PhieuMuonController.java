package com.example.demo.controller;

import com.example.demo.dto.PhieuMuonRequest;
import com.example.demo.dto.PhieuMuonResponse;
import com.example.demo.dto.ThongKePhieuMuonQuaHanDTO;
import com.example.demo.entity.PhieuMuon;
import com.example.demo.service.PhieuMuonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@RestController
@RequestMapping("/api/phieumuon")
public class PhieuMuonController {

    @Autowired
    private PhieuMuonService phieuMuonService;

    @PostMapping
    public ResponseEntity<?> createPhieuMuon(@RequestBody PhieuMuonRequest request) {
        try {
            PhieuMuon phieuMuon = phieuMuonService.createPhieuMuon(
                    request.getMadocgia(),
                    request.getManv(),
                    request.getMaSachList(),
                    request.getNgaymuon(),
                    request.getNgaytradukien()
            );
            PhieuMuonResponse response = phieuMuonService.convertToResponse(phieuMuon);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{maphieu}")
    public ResponseEntity<?> updatePhieuMuon(@PathVariable int maphieu, @RequestBody PhieuMuonRequest request) {
        try {
            boolean success = phieuMuonService.updatePhieuMuon(
                    maphieu, request.getMadocgia(), request.getManv(), request.getMaSachList(),
                    request.getNgaymuon(), request.getNgaytradukien());
            if (success) {
                PhieuMuon phieuMuon = phieuMuonService.findPhieuMuonById(maphieu);
                PhieuMuonResponse response = phieuMuonService.convertToResponse(phieuMuon);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body("Cập nhật phiếu mượn thất bại sau nhiều lần thử.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Xung đột khi cập nhật, vui lòng thử lại.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + e.getMessage());
        }
    }

    @DeleteMapping("/{maphieu}")
    public ResponseEntity<?> deletePhieuMuon(@PathVariable int maphieu) {
        try {
            phieuMuonService.deletePhieuMuon(maphieu);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllPhieuMuon() {
        try {
            List<PhieuMuonResponse> phieuMuonList = phieuMuonService.findAllPhieuMuonResponse();
            return ResponseEntity.ok(phieuMuonList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{maphieu}")
    public ResponseEntity<?> findPhieuMuonById(@PathVariable int maphieu) {
        try {
            PhieuMuonResponse phieuMuon = phieuMuonService.findPhieuMuonByIdResponse(maphieu);
            return ResponseEntity.ok(phieuMuon);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPhieuMuon(
            @RequestParam String searchText,
            @RequestParam String criteria) {
        try {
            List<PhieuMuonResponse> result = phieuMuonService.searchPhieuMuon(searchText, criteria);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/thongke/quahan")
    public ResponseEntity<List<ThongKePhieuMuonQuaHanDTO>> getPhieuMuonQuaHan() {
        return ResponseEntity.ok(phieuMuonService.getPhieuMuonQuaHan());
    }
}
