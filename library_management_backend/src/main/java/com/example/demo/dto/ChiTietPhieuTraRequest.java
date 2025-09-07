package com.example.demo.dto;

public class ChiTietPhieuTraRequest {
    private String masach;
    private String trangthai; // "Nguyên vẹn" hoặc "Hư hỏng/Mất"

    // Getters, setters
    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
