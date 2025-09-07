package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class SachInputDTO {
    private String masach;

    @NotNull(message = "Tên sách không được để trống")
    private String tensach;

    @NotNull(message = "Mã tác giả không được để trống")
    private Integer matacgia;

    @NotNull(message = "Mã nhà xuất bản không được để trống")
    private Integer manxb;

    @NotNull(message = "Mã thể loại không được để trống")
    private Integer matheloai;

    private Integer namxb;
    private Integer sotrang;
    
    @NotNull(message = "Số lượng không được để trống")
    private Integer soluong;
}
