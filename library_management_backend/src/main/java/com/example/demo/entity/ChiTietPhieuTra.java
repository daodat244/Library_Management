package com.example.demo.entity;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "chitietphieutra")
@Data
public class ChiTietPhieuTra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maphieutra")
    private PhieuTra phieuTra;

    private String masach;
    private String trangthai;
    private Double phiphat;  
}