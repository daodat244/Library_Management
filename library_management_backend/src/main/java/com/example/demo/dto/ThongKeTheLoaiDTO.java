package com.example.demo.dto;

import lombok.Data;

@Data
public class ThongKeTheLoaiDTO {
    private int matheloai;
    private String tentheloai;
    private long soDauSach;
    private long tongSoSach;
    
    public ThongKeTheLoaiDTO(int matheloai, String tentheloai, long soDauSach, long tongSoSach) {
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
        this.soDauSach = soDauSach;
        this.tongSoSach = tongSoSach;
    }
}
