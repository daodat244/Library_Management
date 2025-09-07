package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phieumuon")
@Data
public class PhieuMuon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maphieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madocgia", nullable = false, insertable = false, updatable = false)
    private DocGia docGia;

    @Column(name = "madocgia", nullable = false)
    private int madocgia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manv", nullable = false, insertable = false, updatable = false)
    private NhanVien nhanVien;

    @Column(name = "manv", nullable = false)
    private int manv;
    
    @Column(name = "ngaymuon", nullable = false)
    private LocalDateTime ngaymuon;

    @Column(name = "ngaytradukien", nullable = false)
    private LocalDateTime ngaytradukien;

    @Column(name = "trangthai", nullable = false)
    private String trangthai;

    @OneToMany(mappedBy = "phieuMuon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietPhieuMuon> chiTietPhieuMuonList = new ArrayList<>();

    @Version
    private Long version;
}
