package com.mycompany.demo_frontend.dto;

import java.time.LocalDateTime;

public class ThongKePhieuMuonDTO {
    private int maphieu;
    private int madocgia;
    private String tendocgia;
    private LocalDateTime ngaymuon;
    private LocalDateTime ngaytradukien;
    private long soNgayQuaHan;

    public ThongKePhieuMuonDTO() {
    }

    public ThongKePhieuMuonDTO(int maphieu, int madocgia, String tendocgia,
                               LocalDateTime ngaymuon, LocalDateTime ngaytradukien, long soNgayQuaHan) {
        this.maphieu = maphieu;
        this.madocgia = madocgia;
        this.tendocgia = tendocgia;
        this.ngaymuon = ngaymuon;
        this.ngaytradukien = ngaytradukien;
        this.soNgayQuaHan = soNgayQuaHan;
    }

    public int getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }

    public int getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(int madocgia) {
        this.madocgia = madocgia;
    }

    public String getTendocgia() {
        return tendocgia;
    }

    public void setTendocgia(String tendocgia) {
        this.tendocgia = tendocgia;
    }

    public LocalDateTime getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(LocalDateTime ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public LocalDateTime getNgaytradukien() {
        return ngaytradukien;
    }

    public void setNgaytradukien(LocalDateTime ngaytradukien) {
        this.ngaytradukien = ngaytradukien;
    }

    public long getSoNgayQuaHan() {
        return soNgayQuaHan;
    }

    public void setSoNgayQuaHan(long soNgayQuaHan) {
        this.soNgayQuaHan = soNgayQuaHan;
    }
}

