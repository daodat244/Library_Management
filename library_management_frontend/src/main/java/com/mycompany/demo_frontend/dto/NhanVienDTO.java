
package com.mycompany.demo_frontend.dto;

import java.time.LocalDate;

public class NhanVienDTO {
    private Integer manv;
    private String tennv;
    private String sdt;
    private LocalDate ngaysinh;
    private String quequan;
    private String gioitinh;
    private String role;  
    private String username;  
    private String password;

    public NhanVienDTO() {
    }

    public NhanVienDTO(Integer manv, String tennv, String sdt, LocalDate ngaysinh, String quequan, String gioitinh, String role, String username, String password) {
        this.manv = manv;
        this.tennv = tennv;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
        this.quequan = quequan;
        this.gioitinh = gioitinh;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public Integer getManv() {
        return manv;
    }

    public void setManv(Integer manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public LocalDate getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(LocalDate ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
