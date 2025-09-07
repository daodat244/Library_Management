package com.mycompany.demo_frontend.dto;

public class ThongKeTheLoaiDTO {
    private Integer matheloai;
    private String tentheloai;
    private Long soDauSach;
    private Long tongSoSach;

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

    public Long getSoDauSach() {
        return soDauSach;
    }

    public void setSoDauSach(Long soDauSach) {
        this.soDauSach = soDauSach;
    }

    public Long getTongSoSach() {
        return tongSoSach;
    }

    public void setTongSoSach(Long tongSoSach) {
        this.tongSoSach = tongSoSach;
    }
}
