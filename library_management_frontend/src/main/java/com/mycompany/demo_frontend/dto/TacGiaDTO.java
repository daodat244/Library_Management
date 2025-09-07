package com.mycompany.demo_frontend.dto;

public class TacGiaDTO {
    private Integer matacgia;
    private String tentacgia;
    private Integer namsinh;
    private String quequan;
    private String mota;
    private String sdt;
    private String email;

    public TacGiaDTO() {
    }

    public TacGiaDTO(Integer matacgia, String tentacgia, Integer namsinh, String quequan, String mota, String sdt, String email) {
        this.matacgia = matacgia;
        this.tentacgia = tentacgia;
        this.namsinh = namsinh;
        this.quequan = quequan;
        this.mota = mota;
        this.sdt = sdt;
        this.email = email;
    }

    public Integer getMatacgia() {
        return matacgia;
    }

    public void setMatacgia(Integer matacgia) {
        this.matacgia = matacgia;
    }

    public String getTentacgia() {
        return tentacgia;
    }

    public void setTentacgia(String tentacgia) {
        this.tentacgia = tentacgia;
    }

    public Integer getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(Integer namsinh) {
        this.namsinh = namsinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
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
    
    
}
