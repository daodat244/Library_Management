package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "docgia")
@Data
public class DocGia {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int madocgia;
    
    @Column(name = "tendocgia", columnDefinition = "nvarchar(255)", nullable = false)
    @NotBlank(message = "Tên độc giả không được để trống")
    @Size(max = 255, message = "Tên độc giả không được vượt quá 255 ký tự")
    private String tendocgia;
    
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

    @Override
    public String toString() {
        return tendocgia;
    }
}