package com.mycompany.demo_frontend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DetailedPhieuTraResponse {

    private int maphieutra;
    private int maphieu;
    private int madocgia;
    private LocalDateTime ngaytrathucte;
    private double tongphiphat;
    private String ghichu;
    private String tendocgia;
    private String tennv;
    private LocalDateTime ngaymuon;
    private LocalDateTime ngaytradukien;
    private String trangthai;
    private List<ChiTietTraSach> chiTietTraSachList;

    public static class ChiTietTraSach {

        private String masach;
        private String trangthai;
        private double phiphat;

        public String getMasach() {
            return masach;
        }

        public void setMasach(String masach) {
            this.masach = masach;
        }

        public String getTrangthai() {
            return trangthai;
        }

        public void setTrangthai(String trangthai) {
            this.trangthai = trangthai;
        }

        public double getPhiphat() {
            return phiphat;
        }

        public void setPhiphat(double phiphat) {
            this.phiphat = phiphat;
        }
    }

    public int getMaphieutra() {
        return maphieutra;
    }

    public void setMaphieutra(int maphieutra) {
        this.maphieutra = maphieutra;
    }

    public int getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }

    public LocalDateTime getNgaytrathucte() {
        return ngaytrathucte;
    }

    public void setNgaytrathucte(LocalDateTime ngaytrathucte) {
        this.ngaytrathucte = ngaytrathucte;
    }

    public double getTongphiphat() {
        return tongphiphat;
    }

    public void setTongphiphat(double tongphiphat) {
        this.tongphiphat = tongphiphat;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTendocgia() {
        return tendocgia;
    }

    public void setTendocgia(String tendocgia) {
        this.tendocgia = tendocgia;
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

    public List<ChiTietTraSach> getChiTietTraSachList() {
        return chiTietTraSachList;
    }

    public void setChiTietTraSachList(List<ChiTietTraSach> chiTietTraSachList) {
        this.chiTietTraSachList = chiTietTraSachList;
    }

    public int getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(int madocgia) {
        this.madocgia = madocgia;
    }

}
