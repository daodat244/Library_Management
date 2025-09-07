package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public class PhieuMuonRequest {

    private int madocgia;
    private int manv;
    private List<String> maSachList;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngaymuon;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngaytradukien;

    public int getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(int madocgia) {
        this.madocgia = madocgia;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public List<String> getMaSachList() {
        return maSachList;
    }

    public void setMaSachList(List<String> maSachList) {
        this.maSachList = maSachList;
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


}
