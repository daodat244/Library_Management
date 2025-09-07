package com.mycompany.demo_frontend.dto;

public class ThongKeNhanVienDTO {
    private Integer manv;
    private String tennv;
    private Long soPhieuMuonDaTao;

    public ThongKeNhanVienDTO() {
    }

    public ThongKeNhanVienDTO(Integer manv, String tennv, Long soPhieuMuonDaTao) {
        this.manv = manv;
        this.tennv = tennv;
        this.soPhieuMuonDaTao = soPhieuMuonDaTao;
    }

    public Integer getManv() {
        return manv;
    }

    public void setManv(Integer manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public Long getSoPhieuMuonDaTao() {
        return soPhieuMuonDaTao;
    }

    public void setSoPhieuMuonDaTao(Long soPhieuMuonDaTao) {
        this.soPhieuMuonDaTao = soPhieuMuonDaTao;
    }
}

