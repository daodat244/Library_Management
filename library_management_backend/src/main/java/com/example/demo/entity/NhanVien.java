package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "nhanvien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int manv;

    private String tennv;
    private String sdt;
    private LocalDate ngaysinh;
    private String quequan;
    private String gioitinh;
    private String role;
    private String username;
    private String password;
}
