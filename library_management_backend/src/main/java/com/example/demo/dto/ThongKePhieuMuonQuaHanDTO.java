package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ThongKePhieuMuonQuaHanDTO {
    @JsonProperty("maphieu")
    private int maphieu;

    @JsonProperty("madocgia")
    private int madocgia;

    @JsonProperty("tendocgia")
    private String tendocgia;

    @JsonProperty("ngaymuon")
    private LocalDateTime ngaymuon;

    @JsonProperty("ngaytradukien")
    private LocalDateTime ngaytradukien;

    @JsonProperty("soNgayQuaHan")
    private long soNgayQuaHan;

    public ThongKePhieuMuonQuaHanDTO() {}

    public ThongKePhieuMuonQuaHanDTO(int maphieu, int madocgia, String tendocgia, LocalDateTime ngaymuon, 
                                     LocalDateTime ngaytradukien, long soNgayQuaHan) {
        this.maphieu = maphieu;
        this.madocgia = madocgia;
        this.tendocgia = tendocgia;
        this.ngaymuon = ngaymuon;
        this.ngaytradukien = ngaytradukien;
        this.soNgayQuaHan = soNgayQuaHan;
    } 
}
