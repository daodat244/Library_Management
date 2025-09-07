package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "sach", uniqueConstraints = {@UniqueConstraint(columnNames = "masach")})
@Data
public class Sach {
    @Id
    @Column(name = "masach", length = 50, nullable = false)
    @NotNull(message = "Mã sách không được để trống")
    private String masach;

    @Column(name = "tensach", columnDefinition = "nvarchar(255)", nullable = false)
    @NotNull(message = "Tên sách không được để trống")
    private String tensach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matacgia", referencedColumnName = "matacgia", nullable = false)
    @NotNull(message = "Mã tác giả không được để trống")
    private TacGia tacGia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manxb", referencedColumnName = "manxb", nullable = false)
    @NotNull(message = "Mã nhà xuất bản không được để trống")
    private NhaXuatBan nhaXuatBan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matheloai", referencedColumnName = "matheloai", nullable = false)
    @NotNull(message = "Mã thể loại không được để trống")
    private TheLoai theLoai;

    @Column(name = "namxb")
    private Integer namxb;

    @Column(name = "sotrang")
    private Integer sotrang;

    @Column(name = "soluong", nullable = false)
    @NotNull(message = "Số lượng không được để trống")
    private Integer soluong;

    // Constructors
    public Sach() {}

    public Sach(String masach, String tensach, TacGia tacGia, NhaXuatBan nhaXuatBan, TheLoai theLoai, Integer namxb, Integer sotrang, Integer soluong) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacGia = tacGia;
        this.nhaXuatBan = nhaXuatBan;
        this.theLoai = theLoai;
        this.namxb = namxb;
        this.sotrang = sotrang;
        this.soluong = soluong;
    }
}