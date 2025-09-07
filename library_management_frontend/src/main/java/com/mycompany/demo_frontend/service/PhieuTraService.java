package com.mycompany.demo_frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.DetailedPhieuTraResponse;
import com.mycompany.demo_frontend.dto.PhieuMuonResponse;
import com.mycompany.demo_frontend.dto.PhieuTraRequest;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PhieuTraService {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private HttpURLConnection setupConnection(String urlString, String method) throws IOException {
        if (!AuthStorage.isLoggedIn()) {
            throw new IOException("Chưa đăng nhập, không có token xác thực!");
        }
        System.out.println("Request URL: " + urlString);
        System.out.println("Request Method: " + method);
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        String token = AuthStorage.getAccessToken();
        System.out.println("Token: " + (token != null ? token : "NULL"));
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        } else {
            System.err.println("No token provided!");
        }
        return conn;
    }

    private void writeRequestBody(HttpURLConnection conn, String json) throws IOException {
        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void handleError(HttpURLConnection conn) throws IOException {
        int errorCode = conn.getResponseCode();
        String errorMsg = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
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

    public List<PhieuMuonResponse> getPhieuMuonChuaTra() {
        try {
            String encodedTrangThai = URLEncoder.encode("Chưa trả", StandardCharsets.UTF_8);
            String urlString = BASE_URL + "/phieumuon?trangthai=" + encodedTrangThai;
            HttpURLConnection conn = setupConnection(urlString, "GET");
            int responseCode = conn.getResponseCode();
            System.out.println("Yêu cầu GET " + urlString + " - HTTP " + responseCode);
            if (responseCode == 200) {
                List<PhieuMuonResponse> result = Arrays.asList(objectMapper.readValue(conn.getInputStream(), PhieuMuonResponse[].class));
                System.out.println("Số lượng phiếu mượn chưa trả: " + result.size());
                conn.disconnect();
                return result;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (IOException e) {
            showError("Lỗi khi lấy danh sách phiếu mượn chưa trả", e);
        }
        return new ArrayList<>();
    }

    public PhieuMuonResponse getPhieuMuonById(int maphieu) {
        try {
            String urlString = BASE_URL + "/phieumuon/" + maphieu;
            HttpURLConnection conn = setupConnection(urlString, "GET");
            int responseCode = conn.getResponseCode();
            System.out.println("Yêu cầu GET " + urlString + " - HTTP " + responseCode);
            if (responseCode == 200) {
                PhieuMuonResponse result = objectMapper.readValue(conn.getInputStream(), PhieuMuonResponse.class);
                conn.disconnect();
                return result;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (IOException e) {
            showError("Lỗi khi lấy chi tiết phiếu mượn", e);
        }
        return null;
    }
    
    public List<DetailedPhieuTraResponse> searchPhieuTra(String searchText, String criteria) {
        try {
            String urlString = BASE_URL + "/phieutra/search?searchText=" + URLEncoder.encode(searchText, StandardCharsets.UTF_8) + "&criteria=" + URLEncoder.encode(criteria, StandardCharsets.UTF_8);
            HttpURLConnection conn = setupConnection(urlString, "GET");
            if (conn.getResponseCode() == 200) {
                return Arrays.asList(objectMapper.readValue(conn.getInputStream(), DetailedPhieuTraResponse[].class));
            }
            conn.disconnect();
        } catch (IOException e) {
            showError("Lỗi khi tìm kiếm phiếu trả", e);
        }
        return new ArrayList<>();
    }

    public void traSach(PhieuTraRequest request) {
        try {
            String urlString = BASE_URL + "/phieutra";
            String jsonBody = objectMapper.writeValueAsString(request);
            System.out.println("Gửi yêu cầu trả sách: " + jsonBody);
            HttpURLConnection conn = setupConnection(urlString, "POST");
            writeRequestBody(conn, jsonBody);
            int responseCode = conn.getResponseCode();
            System.out.println("Yêu cầu POST " + urlString + " - HTTP " + responseCode);
            String responseBody = "";
            InputStream inputStream = (responseCode >= 400) ? conn.getErrorStream() : conn.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                responseBody = reader.lines().collect(Collectors.joining());
            }
            System.out.println("Response Body: " + responseBody);
            if (responseCode != 200 && responseCode != 201) {
                JOptionPane.showMessageDialog(null, "Lỗi server (Mã: " + responseCode + "): " + responseBody, "Lỗi", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Lỗi khi trả sách: " + responseBody);
            }
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            showError("Lỗi khi trả sách", e);
        }
    }

    public List<DetailedPhieuTraResponse> getAllPhieuTra() {
        try {
            String urlString = BASE_URL + "/phieutra/detailed";
            HttpURLConnection conn = setupConnection(urlString, "GET");
            int responseCode = conn.getResponseCode();
            System.out.println("Yêu cầu GET " + urlString + " - HTTP " + responseCode);
            if (responseCode == 200) {
                List<DetailedPhieuTraResponse> result = Arrays.asList(objectMapper.readValue(conn.getInputStream(), DetailedPhieuTraResponse[].class));
                System.out.println("Số lượng phiếu trả: " + result.size());
                conn.disconnect();
                return result;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (IOException e) {
            showError("Lỗi khi lấy danh sách phiếu trả", e);
        }
        return new ArrayList<>();
    }

    public DetailedPhieuTraResponse getPhieuTraById(int maphieutra) {
        try {
            String urlString = BASE_URL + "/phieutra/detailed/" + maphieutra;
            HttpURLConnection conn = setupConnection(urlString, "GET");
            int responseCode = conn.getResponseCode();
            System.out.println("Yêu cầu GET " + urlString + " - HTTP " + responseCode);
            if (responseCode == 200) {
                DetailedPhieuTraResponse result = objectMapper.readValue(conn.getInputStream(), DetailedPhieuTraResponse.class);
                conn.disconnect();
                return result;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (IOException e) {
            showError("Lỗi khi lấy chi tiết phiếu trả", e);
        }
        return null;
    }
}