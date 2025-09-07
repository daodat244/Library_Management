package com.mycompany.demo_frontend.dto;

public class ThongKeSachHongMatDTO {
    private String masach;
    private String tensach;
    private Long soLuong;

    public ThongKeSachHongMatDTO() {
    }

    public ThongKeSachHongMatDTO(String masach, String tensach, Long soLuong) {
        this.masach = masach;
        this.tensach = tensach;
        this.soLuong = soLuong;
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

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }
}

