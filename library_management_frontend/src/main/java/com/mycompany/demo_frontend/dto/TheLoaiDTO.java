package com.mycompany.demo_frontend.dto;

public class TheLoaiDTO {
    private Integer matheloai;
    private String tentheloai;
    private String mota;

    public TheLoaiDTO() {
    }

    public TheLoaiDTO(Integer matheloai, String tentheloai, String mota) {
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
        this.mota = mota;
    }

    public Integer getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(Integer matheloai) {
        this.matheloai = matheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
    
    
}
