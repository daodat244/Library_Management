package com.example.demo.dto;

import lombok.Data;

@Data
public class TacGiaThongKeDTO {
    private Integer matacgia;
    private String tentacgia;
    private Long soLuongDauSach; // Số lượng sách khác nhau
    private Long tongSoLuongSach; // Tổng số lượng sách (soluong)

    public TacGiaThongKeDTO(Integer matacgia, String tentacgia, Long soLuongDauSach, Long tongSoLuongSach) {
        this.matacgia = matacgia;
        this.tentacgia = tentacgia;
        this.soLuongDauSach = soLuongDauSach;
        this.tongSoLuongSach = tongSoLuongSach;
    }
    
}
