package com.mycompany.demo_frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.DetailedPhieuTraResponse;
import com.mycompany.demo_frontend.dto.PhieuMuonResponse;
import com.mycompany.demo_frontend.dto.SachDTO;
import com.mycompany.demo_frontend.dto.ThongKeDocGiaDTO;
import com.mycompany.demo_frontend.dto.ThongKeNhaXuatBanDTO;
import com.mycompany.demo_frontend.dto.ThongKeNhanVienDTO;
import com.mycompany.demo_frontend.dto.ThongKePhieuMuonDTO;
import com.mycompany.demo_frontend.dto.ThongKeSachDTO;
import com.mycompany.demo_frontend.dto.ThongKeSachHongMatChiTietDTO;
import com.mycompany.demo_frontend.dto.ThongKeSachHongMatDTO;
import com.mycompany.demo_frontend.dto.ThongKeTacGiaDTO;
import com.mycompany.demo_frontend.dto.ThongKeTheLoaiDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThongKeService {

    private String getJson(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) content.append(line);

        in.close();
        conn.disconnect();
        return content.toString();
    }
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.registerModule(new JavaTimeModule());
    }
    
    //=========================================
    //           Thống kê Nhà Xuất Bản
    //=========================================
    public int getSoLuongNXB() {
        try {
            String json = getJson("http://localhost:8080/api/nhaxuatban/thongke");
            List<ThongKeNhaXuatBanDTO> list = new Gson().fromJson(json,
                    new TypeToken<List<ThongKeNhaXuatBanDTO>>() {}.getType());
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<Object[]> getThongKeSLSachTheoNXB() {
        List<Object[]> result = new ArrayList<>();

        try {
            String json = getJson("http://localhost:8080/api/nhaxuatban/thongke");
            List<ThongKeNhaXuatBanDTO> dtoList = new Gson().fromJson(json,
                    new TypeToken<List<ThongKeNhaXuatBanDTO>>() {}.getType());

            for (ThongKeNhaXuatBanDTO dto : dtoList) {
                Object[] row = {
                    dto.getTennxb(),
                    dto.getSoLuongDauSach(),
                    dto.getTongSoLuongSach()
                };
                result.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //=========================================
    //              Thống kê Thể Loại
    //=========================================
    public int getSoLuongTheLoai() {
        try {
            String json = getJson("http://localhost:8080/api/theloai/thongke");
            List<ThongKeTheLoaiDTO> list = new Gson().fromJson(json,
                    new TypeToken<List<ThongKeTheLoaiDTO>>() {}.getType());
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Object[]> getDanhSachSachTheoTheLoai() {
        List<Object[]> result = new ArrayList<>();
        try {
            String json = getJson("http://localhost:8080/api/theloai/thongke");
            List<ThongKeTheLoaiDTO> dtoList = new Gson().fromJson(json,
                    new TypeToken<List<ThongKeTheLoaiDTO>>() {}.getType());

            for (ThongKeTheLoaiDTO dto : dtoList) {
                Object[] row = {
                        dto.getTentheloai(),
                        "(chưa có tên sách)", // Có thể thay đổi nếu backend hỗ trợ tên sách
                        dto.getTongSoSach()
                };
                result.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 👉 Có thể bổ sung thêm phương thức thống kê chi tiết nếu backend mở rộng
    public List<ThongKeTheLoaiDTO> getDanhSachTheLoaiDTO() {
        try {
            String json = getJson("http://localhost:8080/api/theloai/thongke");
            return new Gson().fromJson(json, new TypeToken<List<ThongKeTheLoaiDTO>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public List<Object[]> getTongSoSachTheoTheLoai() {
        List<Object[]> result = new ArrayList<>();
        for (ThongKeTheLoaiDTO dto : getDanhSachTheLoaiDTO()) {
            result.add(new Object[]{dto.getTentheloai(), dto.getTongSoSach()});
        }
        return result;
    }
    
    //=========================================
    //              Thống kê Sách
    //=========================================
    public long getSoLuongDauSach() {
        try {
            String json = getJson("http://localhost:8080/api/sach/thongke/so-luong-dau-sach");
            return Long.parseLong(json.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long getTongSoLuongSach() {
        try {
            String json = getJson("http://localhost:8080/api/sach/thongke/tong-so-luong-sach");
            return Long.parseLong(json.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public long getTongSoLuongSachDangMuon() {
        try {
            String json = getJson("http://localhost:8080/api/sach/thongke/tong-so-luong-sach-dang-muon");
            return Long.parseLong(json.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public long getTongSoLuongSachConTrongKho() {
        try {
            String json = getJson("http://localhost:8080/api/sach/thongke/tong-so-luong-sach-con-trong-kho");
            return Long.parseLong(json.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<SachDTO> getSachDuocMuonNhieuNhat() {
        try {
            String json = getJson("http://localhost:8080/api/sach/thongke/sach-duoc-muon-nhieu-nhat");
            return new Gson().fromJson(json, new TypeToken<List<SachDTO>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    //=========================================
    //              Thống kê Tác giả
    //=========================================
    public int getSoLuongTacGia() {
        try {
            String json = getJson("http://localhost:8080/api/tacgia/thongke");
            List<ThongKeTacGiaDTO> list = new Gson().fromJson(json, new TypeToken<List<ThongKeTacGiaDTO>>(){}.getType());
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<ThongKeTacGiaDTO> getSachTheoTacGia() {
        try {
            String json = getJson("http://localhost:8080/api/tacgia/thongke");
            return new Gson().fromJson(json, new TypeToken<List<ThongKeTacGiaDTO>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    //=========================================
    //          Thống kê Độc giả
    //=========================================
    public int getSoLuongDocGia() {
        try {
            String json = getJson("http://localhost:8080/api/docgia/thongke");
            List<ThongKeDocGiaDTO> list = new Gson().fromJson(json, new TypeToken<List<ThongKeDocGiaDTO>>(){}.getType());
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<ThongKeDocGiaDTO> getThongKeDocGia() {
        try {
            String json = getJson("http://localhost:8080/api/docgia/thongke");
            return new Gson().fromJson(json, new TypeToken<List<ThongKeDocGiaDTO>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    //=========================================
    //          Thống kê Nhân Viên
    //=========================================
    public int getSoLuongNhanVien() {
        try {
            String json = getJson("http://localhost:8080/api/nhanvien/thongke");
            List<ThongKeNhanVienDTO> list = new Gson().fromJson(json, new TypeToken<List<ThongKeNhanVienDTO>>(){}.getType());
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<ThongKeNhanVienDTO> getPhieuMuonNhanVienDaTao() {
        try {
            String json = getJson("http://localhost:8080/api/nhanvien/thongke");
            return new Gson().fromJson(json, new TypeToken<List<ThongKeNhanVienDTO>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    //=========================================
    //          Thống kê Phiếu Mượn
    //=========================================
    public int getSoLuongPhieuMuon() {
        try {
            String json = getJson("http://localhost:8080/api/phieumuon");
            PhieuMuonResponse[] arr = objectMapper.readValue(json, PhieuMuonResponse[].class);
            return arr.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<ThongKePhieuMuonDTO> getSoPhieuMuonQuaHan() {
        try {
            String json = getJson("http://localhost:8080/api/phieumuon/thongke/quahan");
            ThongKePhieuMuonDTO[] arr = objectMapper.readValue(json, ThongKePhieuMuonDTO[].class);
            return Arrays.asList(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    //=========================================
    //          Thống kê Phiếu trả
    //=========================================
    public int getSoLuongPhieuTra() {
        try {
            String json = getJson("http://localhost:8080/api/phieutra");
            DetailedPhieuTraResponse[] arr = objectMapper.readValue(json, DetailedPhieuTraResponse[].class);
            return arr.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<ThongKeSachHongMatDTO> getSoSachHongMat() {
        try {
            String json = getJson("http://localhost:8080/api/phieutra/thongke/soluong");
            ThongKeSachHongMatDTO[] arr = objectMapper.readValue(json, ThongKeSachHongMatDTO[].class);
            return Arrays.asList(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<ThongKeSachHongMatChiTietDTO> getSoSachHongMatChiTiet() {
        try {
            String json = getJson("http://localhost:8080/api/phieutra/thongke/chitiet");
            ThongKeSachHongMatChiTietDTO[] arr = objectMapper.readValue(json, ThongKeSachHongMatChiTietDTO[].class);
            return Arrays.asList(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
