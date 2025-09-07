package com.mycompany.demo_frontend.dto;

import java.time.LocalDateTime;

public class ThongKeSachHongMatChiTietDTO {
    private Integer maphieutra;
    private Integer maphieu;
    private String tendocgia;
    private LocalDateTime ngaymuon;
    private LocalDateTime ngaytrathucte;
    private String masach;
    private String tensach;

    public ThongKeSachHongMatChiTietDTO() {
    }

    public ThongKeSachHongMatChiTietDTO(Integer maphieutra, Integer maphieu, String tendocgia,
                                        LocalDateTime ngaymuon, LocalDateTime ngaytrathucte,
                                        String masach, String tensach) {
        this.maphieutra = maphieutra;
        this.maphieu = maphieu;
        this.tendocgia = tendocgia;
        this.ngaymuon = ngaymuon;
        this.ngaytrathucte = ngaytrathucte;
        this.masach = masach;
        this.tensach = tensach;
    }

    public Integer getMaphieutra() {
        return maphieutra;
    }

    public void setMaphieutra(Integer maphieutra) {
        this.maphieutra = maphieutra;
    }

    public Integer getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(Integer maphieu) {
        this.maphieu = maphieu;
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

    public LocalDateTime getNgaytrathucte() {
        return ngaytrathucte;
    }

    public void setNgaytrathucte(LocalDateTime ngaytrathucte) {
        this.ngaytrathucte = ngaytrathucte;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }
}

