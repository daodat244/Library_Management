package com.example.demo.dto;

import lombok.Data;

@Data
public class DocGiaThongKeDTO {
    private Integer madocgia;
    private String tendocgia;
    private Long tongSoPhieuMuon;
    private Long soPhieuMuonDaTra;
    private Long soPhieuMuonChuaTra;
    private Double soPhiPhat;

    public DocGiaThongKeDTO(Integer madocgia, String tendocgia, Long tongSoPhieuMuon, 
                           Long soPhieuMuonDaTra, Long soPhieuMuonChuaTra, Double soPhiPhat) {
        this.madocgia = madocgia;
        this.tendocgia = tendocgia;
        this.tongSoPhieuMuon = tongSoPhieuMuon;
        this.soPhieuMuonDaTra = soPhieuMuonDaTra;
        this.soPhieuMuonChuaTra = soPhieuMuonChuaTra;
        this.soPhiPhat = soPhiPhat;
    }
}
