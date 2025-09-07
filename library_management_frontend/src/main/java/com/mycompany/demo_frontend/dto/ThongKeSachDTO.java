package com.mycompany.demo_frontend.dto;

public class ThongKeSachDTO {
    private Long tongSoDauSach;
    private Long tongSoLuongSach;
    private Long soLuongSachDangDuocMuon;
    private Long soLuongSachConTrongKho;
    private String sachDuocMuonNhieuNhat;

    // Constructors, getters, setters
    public ThongKeSachDTO() {
    }

    public ThongKeSachDTO(Long tongSoDauSach, Long tongSoLuongSach, Long soLuongSachDangDuocMuon, Long soLuongSachConTrongKho, String sachDuocMuonNhieuNhat) {
        this.tongSoDauSach = tongSoDauSach;
        this.tongSoLuongSach = tongSoLuongSach;
        this.soLuongSachDangDuocMuon = soLuongSachDangDuocMuon;
        this.soLuongSachConTrongKho = soLuongSachConTrongKho;
        this.sachDuocMuonNhieuNhat = sachDuocMuonNhieuNhat;
    }

    public Long getTongSoDauSach() {
        return tongSoDauSach;
    }

    public void setTongSoDauSach(Long tongSoDauSach) {
        this.tongSoDauSach = tongSoDauSach;
    }

    public Long getTongSoLuongSach() {
        return tongSoLuongSach;
    }

    public void setTongSoLuongSach(Long tongSoLuongSach) {
        this.tongSoLuongSach = tongSoLuongSach;
    }

    public Long getSoLuongSachDangDuocMuon() {
        return soLuongSachDangDuocMuon;
    }

    public void setSoLuongSachDangDuocMuon(Long soLuongSachDangDuocMuon) {
        this.soLuongSachDangDuocMuon = soLuongSachDangDuocMuon;
    }

    public Long getSoLuongSachConTrongKho() {
        return soLuongSachConTrongKho;
    }

    public void setSoLuongSachConTrongKho(Long soLuongSachConTrongKho) {
        this.soLuongSachConTrongKho = soLuongSachConTrongKho;
    }

    public String getSachDuocMuonNhieuNhat() {
        return sachDuocMuonNhieuNhat;
    }

    public void setSachDuocMuonNhieuNhat(String sachDuocMuonNhieuNhat) {
        this.sachDuocMuonNhieuNhat = sachDuocMuonNhieuNhat;
    }

    
}
