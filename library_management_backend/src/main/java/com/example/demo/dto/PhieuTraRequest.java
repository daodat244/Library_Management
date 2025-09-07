package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class PhieuTraRequest {
    private int maphieu;
    private LocalDateTime ngaytrathucte;
    private String ghichu;
    private List<ChiTietPhieuTraRequest> chiTietList;

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

    @JsonProperty("ghichu")
    public String getGhiChu() {
        return ghichu;
    }

    @JsonProperty("ghichu")
    public void setGhiChu(String ghichu) {
        this.ghichu = ghichu;
    }

    public List<ChiTietPhieuTraRequest> getChiTietList() {
        return chiTietList;
    }

    public void setChiTietList(List<ChiTietPhieuTraRequest> chiTietList) {
        this.chiTietList = chiTietList;
    }
}
