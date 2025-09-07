package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PhieuMuonResponse {
    private int maphieu;
    private int madocgia;
    private String tendocgia;
    private int manv;
    private String tennv;
    private LocalDateTime ngaymuon;
    private LocalDateTime ngaytradukien;
    private String trangthai;
    private List<String> maSachList;

    // Getters và Setters

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

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
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

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public List<String> getMaSachList() {
        return maSachList;
    }

    public void setMaSachList(List<String> maSachList) {
        this.maSachList = maSachList;
    }

}
