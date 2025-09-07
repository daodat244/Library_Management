package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NhanVienDTO {
    private Integer manv;
    private String tennv;
    private String sdt;
    private LocalDate ngaysinh;
    private String quequan;
    private String gioitinh;
    private String role;
    private String username; 
    private String password; 
}