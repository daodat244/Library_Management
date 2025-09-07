package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "tacgia")
@Data
public class TacGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matacgia;
    
    @Column(name = "tentacgia", columnDefinition = "nvarchar(255)", nullable = false)
    @NotBlank(message = "Tên tác giả không được để trống")
    @Size(max = 255, message = "Tên tác giả không được vượt quá 255 ký tự")
    private String tentacgia;
    
    @Column(name = "namsinh")
    @Min(value = 1, message = "Năm sinh phải lớn hơn 0")
    @Max(value = 2025, message = "Năm sinh phải nhỏ hơn năm hiện tại")
    private Integer namsinh;
    
    @Column(name = "quequan", columnDefinition = "nvarchar(255)")
    @Size(max = 255, message = "Quê quán không được vượt quá 255 ký tự")
    private String quequan;
    
    @Column(name = "mota", columnDefinition = "nvarchar(1000)")
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String mota;
    
    @Column(name = "sdt", columnDefinition = "varchar(10)")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0")
    @Size(max = 10, message = "Số điện thoại không được vượt quá 10 ký tự")
    private String sdt;
    
    @Column(name = "email", columnDefinition = "nvarchar(255)")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email không hợp lệ")
    @Size(max = 255, message = "Email không được vượt quá 255 ký tự")
    private String email;
    
    @OneToMany(mappedBy = "tacGia", fetch = FetchType.LAZY)
    private List<Sach> sachList;
    
    @Override
    public String toString() {
        return tentacgia;
    }
}
