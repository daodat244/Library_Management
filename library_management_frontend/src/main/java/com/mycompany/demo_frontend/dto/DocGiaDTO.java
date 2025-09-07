package com.mycompany.demo_frontend.dto;

public class DocGiaDTO {
    private Integer madocgia;
    private String tendocgia;
    private String sdt;
    private String email;
    private String diachi;    

    public DocGiaDTO() {
    }

    public DocGiaDTO(Integer madocgia, String tendocgia, String sdt, String email, String diachi) {
        this.madocgia = madocgia;
        this.tendocgia = tendocgia;
        this.sdt = sdt;
        this.email = email;
        this.diachi = diachi;
    }

    public Integer getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(Integer madocgia) {
        this.madocgia = madocgia;
    }

    public String getTendocgia() {
        return tendocgia;
    }

    public void setTendocgia(String tendocgia) {
        this.tendocgia = tendocgia;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    
}
