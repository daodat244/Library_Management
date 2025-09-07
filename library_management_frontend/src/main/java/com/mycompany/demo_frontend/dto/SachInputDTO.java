package com.mycompany.demo_frontend.dto;

public class SachInputDTO {
    private String masach;
    private String tensach;
    private Integer matacgia;
    private Integer manxb;
    private Integer matheloai;
    private Integer namxb;
    private Integer sotrang;
    private Integer soluong;

    public SachInputDTO() {
    }

    public SachInputDTO(String masach, String tensach, Integer matacgia, Integer manxb, Integer matheloai, Integer namxb, Integer sotrang, Integer soluong) {
        this.masach = masach;
        this.tensach = tensach;
        this.matacgia = matacgia;
        this.manxb = manxb;
        this.matheloai = matheloai;
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

    public Integer getManxb() {
        return manxb;
    }

    public void setManxb(Integer manxb) {
        this.manxb = manxb;
    }

    public Integer getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(Integer matheloai) {
        this.matheloai = matheloai;
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
