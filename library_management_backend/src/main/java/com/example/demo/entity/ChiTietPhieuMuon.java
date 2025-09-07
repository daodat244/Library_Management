package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chitietphieumuon")
@Data
public class ChiTietPhieuMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "maphieu", nullable = false)
    private PhieuMuon phieuMuon;

    @Column(name = "masach", nullable = false)
    private String masach;
    
    @Column(name = "soluong", nullable = false)
    private int soluong;
    
    @Version
    private Long version; 
}
