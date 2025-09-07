package com.example.demo.dto;

import lombok.Data;

@Data
public class SachHuHongMatSoLuongDTO {

    private String masach;
    private String tensach;
    private Long soLuong;

    public SachHuHongMatSoLuongDTO(String masach, String tensach, Long soLuong) {
        this.masach = masach;
        this.tensach = tensach;
        this.soLuong = soLuong;
    }
}
