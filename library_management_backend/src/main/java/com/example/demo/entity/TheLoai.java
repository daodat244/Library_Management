package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "theloai")
@Data
public class TheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matheloai;
    
    @Column(name = "tentheloai", columnDefinition = "nvarchar(255)", nullable = false)
    @NotBlank(message = "Tên thể loại không được để trống")
    @Size(max = 255, message = "Tên thể loại không được vượt quá 255 ký tự")
    private String tentheloai;
    
    @Column(name = "mota", columnDefinition = "nvarchar(1000)")
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String mota;
    
    @OneToMany(mappedBy = "theLoai", fetch = FetchType.LAZY)
    private List<Sach> sachList;
    
    @Override
    public String toString() {
        return tentheloai;
    }
}
