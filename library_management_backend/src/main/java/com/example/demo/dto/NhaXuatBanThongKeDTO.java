package com.example.demo.dto;

import lombok.Data;

@Data
public class NhaXuatBanThongKeDTO {
    private Integer manxb;
    private String tennxb;
    private Long soLuongDauSach; // Số lượng sách khác nhau
    private Long tongSoLuongSach; // Tổng số lượng sách (soluong)

    public NhaXuatBanThongKeDTO(Integer manxb, String tennxb, Long soLuongDauSach, Long tongSoLuongSach) {
        this.manxb = manxb;
        this.tennxb = tennxb;
        this.soLuongDauSach = soLuongDauSach;
        this.tongSoLuongSach = tongSoLuongSach;
    }
}
