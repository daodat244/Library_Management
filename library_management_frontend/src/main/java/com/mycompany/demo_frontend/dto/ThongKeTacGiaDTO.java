package com.mycompany.demo_frontend.dto;

public class ThongKeTacGiaDTO {
    private int matacgia;
    private String tentacgia;
    private int soLuongDauSach;
    private int tongSoLuongSach;

    public ThongKeTacGiaDTO() {
    }

    public ThongKeTacGiaDTO(int matacgia, String tentacgia, int soLuongDauSach, int tongSoLuongSach) {
        this.matacgia = matacgia;
        this.tentacgia = tentacgia;
        this.soLuongDauSach = soLuongDauSach;
        this.tongSoLuongSach = tongSoLuongSach;
    }

    public int getMatacgia() {
        return matacgia;
    }

    public void setMatacgia(int matacgia) {
        this.matacgia = matacgia;
    }

    public String getTentacgia() {
        return tentacgia;
    }

    public void setTentacgia(String tentacgia) {
        this.tentacgia = tentacgia;
    }

    public int getSoLuongDauSach() {
        return soLuongDauSach;
    }

    public void setSoLuongDauSach(int soLuongDauSach) {
        this.soLuongDauSach = soLuongDauSach;
    }

    public int getTongSoLuongSach() {
        return tongSoLuongSach;
    }

    public void setTongSoLuongSach(int tongSoLuongSach) {
        this.tongSoLuongSach = tongSoLuongSach;
    }
}
