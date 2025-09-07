package com.example.demo.dto;

import lombok.Data;

@Data
public class NhanVienThongKeDTO {
    private Integer manv;
    private String tennv;
    private Long soPhieuMuonDaTao;

    public NhanVienThongKeDTO() {
    }

    public NhanVienThongKeDTO(Integer manv, String tennv, Long soPhieuMuonDaTao) {
        this.manv = manv;
        this.tennv = tennv;
        this.soPhieuMuonDaTao = soPhieuMuonDaTao;
    }
    
}
