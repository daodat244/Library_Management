package com.example.demo.dto;

import lombok.Data;

@Data
public class SachDTO {
    private String masach;
    private String tensach;
    private Integer matacgia;
    private String tentacgia;
    private Integer manxb;
    private String tennxb;
    private Integer matheloai;
    private String tentheloai;
    private Integer namxb;
    private Integer sotrang;
    private Integer soluong;
    
    public SachDTO() {}

    public SachDTO(String masach, String tensach, Integer matacgia, String tentacgia, 
                   Integer manxb, String tennxb, Integer matheloai, String tentheloai, 
                   Integer namxb, Integer sotrang, Integer soluong) {
        this.masach = masach;
        this.tensach = tensach;
        this.matacgia = matacgia;
        this.tentacgia = tentacgia;
        this.manxb = manxb;
        this.tennxb = tennxb;
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
        this.namxb = namxb;
        this.sotrang = sotrang;
        this.soluong = soluong;
    }
}
