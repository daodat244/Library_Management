package com.mycompany.demo_frontend.dto;

public class ThongKeNhaXuatBanDTO {
    private Integer manxb;
    private String tennxb;
    private Long soLuongDauSach;
    private Long tongSoLuongSach;

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

    public Long getSoLuongDauSach() {
        return soLuongDauSach;
    }

    public void setSoLuongDauSach(Long soLuongDauSach) {
        this.soLuongDauSach = soLuongDauSach;
    }

    public Long getTongSoLuongSach() {
        return tongSoLuongSach;
    }

    public void setTongSoLuongSach(Long tongSoLuongSach) {
        this.tongSoLuongSach = tongSoLuongSach;
    }
}
