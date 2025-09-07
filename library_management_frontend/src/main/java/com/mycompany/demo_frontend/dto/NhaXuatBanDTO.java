package com.mycompany.demo_frontend.dto;

public class NhaXuatBanDTO {
    private Integer manxb;
    private String tennxb;
    private String sdt;
    private String email;
    private String diachi;

    public NhaXuatBanDTO() {}

    public NhaXuatBanDTO(Integer manxb, String tennxb, String sdt, String email, String diachi) {
        this.manxb = manxb;
        this.tennxb = tennxb;
        this.sdt = sdt;
        this.email = email;
        this.diachi = diachi;
    }

    public Integer getManxb() {
        return manxb;
    }

    public void setManxb(Integer manxb) {
        this.manxb = manxb;
    }

    public String getTennxb() {
        return tennxb;
    }

    public void setTennxb(String tennxb) {
        this.tennxb = tennxb;
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
