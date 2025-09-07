package com.mycompany.demo_frontend.dto;

public class ThongKeDocGiaDTO {
    private Integer madocgia;
    private String tendocgia;
    private Long tongSoPhieuMuon;
    private Long soPhieuMuonDaTra;
    private Long soPhieuMuonChuaTra;
    private Double soPhiPhat;

    public ThongKeDocGiaDTO() {
    }

    public ThongKeDocGiaDTO(Integer madocgia, String tendocgia, Long tongSoPhieuMuon,
                            Long soPhieuMuonDaTra, Long soPhieuMuonChuaTra, Double soPhiPhat) {
        this.madocgia = madocgia;
        this.tendocgia = tendocgia;
        this.tongSoPhieuMuon = tongSoPhieuMuon;
        this.soPhieuMuonDaTra = soPhieuMuonDaTra;
        this.soPhieuMuonChuaTra = soPhieuMuonChuaTra;
        this.soPhiPhat = soPhiPhat;
    }

    public Integer getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(Integer madocgia) {
        this.madocgia = madocgia;
    }

    public String getTendocgia() {
        return tendocgia;
    }

    public void setTendocgia(String tendocgia) {
        this.tendocgia = tendocgia;
    }

    public Long getTongSoPhieuMuon() {
        return tongSoPhieuMuon;
    }

    public void setTongSoPhieuMuon(Long tongSoPhieuMuon) {
        this.tongSoPhieuMuon = tongSoPhieuMuon;
    }

    public Long getSoPhieuMuonDaTra() {
        return soPhieuMuonDaTra;
    }

    public void setSoPhieuMuonDaTra(Long soPhieuMuonDaTra) {
        this.soPhieuMuonDaTra = soPhieuMuonDaTra;
    }

    public Long getSoPhieuMuonChuaTra() {
        return soPhieuMuonChuaTra;
    }

    public void setSoPhieuMuonChuaTra(Long soPhieuMuonChuaTra) {
        this.soPhieuMuonChuaTra = soPhieuMuonChuaTra;
    }

    public Double getSoPhiPhat() {
        return soPhiPhat;
    }

    public void setSoPhiPhat(Double soPhiPhat) {
        this.soPhiPhat = soPhiPhat;
    }
}

