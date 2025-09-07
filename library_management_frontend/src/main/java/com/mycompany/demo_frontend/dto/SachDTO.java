package com.mycompany.demo_frontend.dto;

public class SachDTO {
    private String masach;
    private String tensach;
    private Integer matacgia;
    private String tentacgia;
    private Integer manxb;
    private String tennxb;
    private Integer matheloai;
    private String tentheloai;
    private Integer namxb;
    private Integer sotrang;
    private Integer soluong;
    
    public SachDTO() {}

    public SachDTO(String masach, String tensach, Integer matacgia, String tentacgia, 
                   Integer manxb, String tennxb, Integer matheloai, String tentheloai, 
                   Integer namxb, Integer sotrang, Integer soluong) {
        this.masach = masach;
        this.tensach = tensach;
        this.matacgia = matacgia;
        this.tentacgia = tentacgia;
        this.manxb = manxb;
        this.tennxb = tennxb;
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
        this.namxb = namxb;
        this.sotrang = sotrang;
        this.soluong = soluong;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
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

    public Integer getNamxb() {
        return namxb;
    }

    public void setNamxb(Integer namxb) {
        this.namxb = namxb;
    }

    public Integer getSotrang() {
        return sotrang;
    }

    public void setSotrang(Integer sotrang) {
        this.sotrang = sotrang;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }
    
    
}
