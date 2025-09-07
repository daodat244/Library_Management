package com.mycompany.demo_frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.NhanVienDTO;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NhanVienService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api";

    public NhanVienService() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public NhanVienDTO getCurrentNhanVien(String accessToken) {
        try {
            HttpURLConnection conn = setupConnection(baseUrl + "/user/info", "GET", accessToken);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                NhanVienDTO nhanVien = objectMapper.readValue(conn.getInputStream(), NhanVienDTO.class);
                conn.disconnect();
                return nhanVien;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (IOException e) {
            showError("Lỗi khi lấy thông tin nhân viên hiện tại", e);
            System.err.println("IOException: " + e.getMessage());
        } catch (Exception e) {
            showError("Lỗi không xác định khi lấy thông tin nhân viên", e);
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public List<NhanVienDTO> getAllNhanViens() {
        try {
            HttpURLConnection conn = setupConnection(baseUrl + "/nhanvien", "GET", AuthStorage.getAccessToken());
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return objectMapper.readValue(conn.getInputStream(), new TypeReference<List<NhanVienDTO>>() {});
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tải danh sách nhân viên", e);
        }
        return new ArrayList<>();
    }

    public NhanVienDTO getNhanVienById(int id) {
        try {
            HttpURLConnection conn = setupConnection(baseUrl + "/nhanvien/" + id, "GET", AuthStorage.getAccessToken());
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return objectMapper.readValue(conn.getInputStream(), NhanVienDTO.class);
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (IOException e) {
            showError("Lỗi khi lấy thông tin nhân viên", e);
        }
        return null;
    }

    public NhanVienDTO addNhanVien(NhanVienDTO nhanVien) {
        try {
            HttpURLConnection conn = setupConnection(baseUrl + "/nhanvien", "POST", AuthStorage.getAccessToken());
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            objectMapper.writeValue(os, nhanVien);
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                return objectMapper.readValue(conn.getInputStream(), NhanVienDTO.class);
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi thêm nhân viên", e);
        }
        return null;
    }

    public boolean updateNhanVien(int id, NhanVienDTO nhanVien) {
        try {
            HttpURLConnection conn = setupConnection(baseUrl + "/nhanvien/" + id, "PUT", AuthStorage.getAccessToken());
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            objectMapper.writeValue(os, nhanVien);
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                conn.disconnect();
                return true;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi cập nhật nhân viên", e);
        }
        return false;
    }

    public boolean deleteNhanVien(int id) {
        try {
            HttpURLConnection conn = setupConnection(baseUrl + "/nhanvien/" + id, "DELETE", AuthStorage.getAccessToken());
            int responseCode = conn.getResponseCode();
            conn.disconnect();
            return responseCode == 200 || responseCode == 204;
        } catch (Exception e) {
            showError("Lỗi khi xóa nhân viên", e);
            return false;
        }
    }

    public List<NhanVienDTO> searchNhanVien(String searchText, String criteria) {
        try {
            String encodedText = URLEncoder.encode(searchText, StandardCharsets.UTF_8);
            String encodedCriteria = URLEncoder.encode(criteria, StandardCharsets.UTF_8);
            String urlString = baseUrl + "/nhanvien/search?searchText=" + encodedText + "&searchCriteria=" + encodedCriteria;
            HttpURLConnection conn = setupConnection(urlString, "GET", AuthStorage.getAccessToken());
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return objectMapper.readValue(conn.getInputStream(), new TypeReference<List<NhanVienDTO>>() {});
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tìm kiếm nhân viên", e);
        }
        return new ArrayList<>();
    }

    private HttpURLConnection setupConnection(String urlString, String method, String token) throws IOException {
        System.out.println("Request URL: " + urlString);
        System.out.println("Request Method: " + method);
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        return conn;
    }

    private void handleError(HttpURLConnection conn) throws IOException {
        int errorCode = conn.getResponseCode();
        String errorMsg = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
            errorMsg = reader.lines().collect(Collectors.joining());
        } catch (Exception e) {
            errorMsg = "Không thể đọc lỗi từ server.";
        }
        System.out.println("HTTP Error Code: " + errorCode);
        System.out.println("Error Message from server: " + errorMsg);
        JOptionPane.showMessageDialog(null, "Lỗi từ server (Mã lỗi: " + errorCode + "): " + errorMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showError(String message, Exception e) {
        JOptionPane.showMessageDialog(null, message + ": " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
