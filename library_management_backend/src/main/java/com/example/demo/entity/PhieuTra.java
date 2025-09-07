package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "phieutra")
@Data
public class PhieuTra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maphieutra;

    @Column(name = "maphieu", nullable = false)
    private int maphieu;

    @Column(name = "ngaytrathucte", nullable = false)
    private LocalDateTime ngaytrathucte;
    
    @Column(name = "tongphiphat", nullable = false)
    private double tongphiphat;
    
    @Column(name = "ghichu")
    private String ghichu;
    
}
