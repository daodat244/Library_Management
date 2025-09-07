package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "nhaxuatban")
@Data
public class NhaXuatBan {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int manxb;
    
    @Column(name = "tennxb", columnDefinition = "nvarchar(255)", nullable = false)
    @NotBlank(message = "Tên NXB không được để trống")
    @Size(max = 255, message = "Tên NXB không được vượt quá 255 ký tự")
    private String tennxb;
    
    @Column(name = "sdt", columnDefinition = "varchar(10)")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại phải là 10 chữ số và bắt đầu từ số 0")
    @Size(max = 10, message = "Số điện thoại không được vượt quá 10 ký tự")
    private String sdt;
    
    @Column(name = "email", columnDefinition = "nvarchar(255)", nullable = false)
    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email không hợp lệ")
    @Size(max = 255, message = "Email không được vượt quá 255 ký tự")
    private String email;
    
    @Column(name = "diachi", columnDefinition = "nvarchar(255)")
    private String diachi;
    
    @OneToMany(mappedBy = "nhaXuatBan", fetch = FetchType.LAZY)
    private List<Sach> sachList;

    @Override
    public String toString() {
        return tennxb;
    }
}
