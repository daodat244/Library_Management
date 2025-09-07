package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SachHuHongMatChiTietDTO {
    private Integer maphieutra;
    private Integer maphieu;
    private String tendocgia;
    private LocalDateTime ngaymuon;
    private LocalDateTime ngaytrathucte;
    private String masach;
    private String tensach;

    public SachHuHongMatChiTietDTO(Integer maphieutra, Integer maphieu, String tendocgia, LocalDateTime ngaymuon, LocalDateTime ngaytrathucte, String masach, String tensach) {
        this.maphieutra = maphieutra;
        this.maphieu = maphieu;
        this.tendocgia = tendocgia;
        this.ngaymuon = ngaymuon;
        this.ngaytrathucte = ngaytrathucte;
        this.masach = masach;
        this.tensach = tensach;
    }
}
