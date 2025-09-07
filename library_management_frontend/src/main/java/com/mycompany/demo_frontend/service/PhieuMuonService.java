package com.mycompany.demo_frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.PhieuMuonRequest;
import com.mycompany.demo_frontend.dto.PhieuMuonResponse;

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

public class PhieuMuonService {

    private static final String BASE_URL = "http://localhost:8080/api/phieumuon";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<PhieuMuonResponse> getAllPhieuMuon() {
        try {
            HttpURLConnection conn = setupConnection(BASE_URL, "GET");
            if (conn.getResponseCode() == 200) {
                return Arrays.asList(objectMapper.readValue(conn.getInputStream(), PhieuMuonResponse[].class));
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tải danh sách phiếu mượn", e);
        }
        return new ArrayList<>();
    }

    public PhieuMuonResponse getPhieuMuonById(int maphieu) {
        try {
            HttpURLConnection conn = setupConnection(BASE_URL + "/" + maphieu, "GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return objectMapper.readValue(conn.getInputStream(), PhieuMuonResponse.class);
            } else {
                handleError(conn);
                System.err.println("Lỗi API getPhieuMuonById - Mã: " + responseCode);
            }
            conn.disconnect();
        } catch (IOException e) {
            showError("Lỗi khi lấy chi tiết phiếu mượn", e);
            System.err.println("IOException: " + e.getMessage());
        } catch (Exception e) {
            showError("Lỗi không xác định khi lấy chi tiết phiếu mượn", e);
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public boolean createPhieuMuon(PhieuMuonRequest requestDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(requestDTO);
            System.out.println("Request Body: " + requestBody);
            HttpURLConnection conn = setupConnection(BASE_URL, "POST");
            conn.setDoOutput(true);
            writeRequestBody(conn, requestBody);

            int code = conn.getResponseCode();
            System.out.println("Response Code: " + code);
            String responseBody = "";
            InputStream inputStream = (code >= 400) ? conn.getErrorStream() : conn.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                responseBody = reader.lines().collect(Collectors.joining());
            }
            System.out.println("Response Body: " + responseBody);

            if (code != 200 && code != 201) {
                JOptionPane.showMessageDialog(null, "Lỗi server (Mã: " + code + "): " + responseBody, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            conn.disconnect();
            return true;
        } catch (Exception e) {
            System.err.println("Error in createPhieuMuon: " + e.getMessage());
            showError("Lỗi khi thêm phiếu mượn", e);
            return false;
        }
    }

    public boolean updatePhieuMuon(int maphieu, PhieuMuonRequest requestDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(requestDTO);
            System.out.println("Request Body: " + requestBody);
            HttpURLConnection conn = setupConnection(BASE_URL + "/" + maphieu, "PUT");
            conn.setDoOutput(true);
            writeRequestBody(conn, requestBody);

            int code = conn.getResponseCode();
            System.out.println("Response Code: " + code);
            String responseBody = "";
            InputStream inputStream = (code >= 400) ? conn.getErrorStream() : conn.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                responseBody = reader.lines().collect(Collectors.joining());
            }
            System.out.println("Response Body: " + responseBody);

            if (code != 200) {
                JOptionPane.showMessageDialog(null, "Lỗi server (Mã: " + code + "): " + responseBody, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            conn.disconnect();
            return true;
        } catch (Exception e) {
            System.err.println("Error in updatePhieuMuon: " + e.getMessage());
            showError("Lỗi khi cập nhật phiếu mượn", e);
            return false;
        }
    }

    public boolean deletePhieuMuon(int id) {
        try {
            HttpURLConnection conn = setupConnection(BASE_URL + "/" + id, "DELETE");
            int code = conn.getResponseCode();
            conn.disconnect();
            return code == 200;
        } catch (Exception e) {
            showError("Lỗi khi xóa phiếu mượn", e);
            return false;
        }
    }

    public List<PhieuMuonResponse> searchPhieuMuon(String searchText, String criteria) {
        try {
            String urlString = BASE_URL + "/search?searchText=" + URLEncoder.encode(searchText, StandardCharsets.UTF_8) + "&criteria=" + URLEncoder.encode(criteria, StandardCharsets.UTF_8);
            HttpURLConnection conn = setupConnection(urlString, "GET");
            if (conn.getResponseCode() == 200) {
                return Arrays.asList(objectMapper.readValue(conn.getInputStream(), PhieuMuonResponse[].class));
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tìm kiếm phiếu mượn", e);
        }
        return new ArrayList<>();
    }

    private HttpURLConnection setupConnection(String urlString, String method) throws IOException {
        if (!AuthStorage.isLoggedIn()) {
            throw new IOException("Chưa đăng nhập, không có token xác thực!");
        }
        System.out.println("Request URL: " + urlString);
        System.out.println("Request Method: " + method);
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod(method);
        conn.setInstanceFollowRedirects(false);
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
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void handleError(HttpURLConnection conn) throws IOException {
        int errorCode = conn.getResponseCode(); // Lấy mã lỗi HTTP
        String errorMsg = "";

        // Đọc phản hồi lỗi từ server
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
            errorMsg = reader.lines().collect(Collectors.joining());
        } catch (Exception e) {
            errorMsg = "Không thể đọc lỗi từ server.";
        }

        // Log chi tiết về mã lỗi và thông điệp lỗi từ server
        System.out.println("HTTP Error Code: " + errorCode);
        System.out.println("Error Message from server: " + errorMsg);

        // In ra thông tin chi tiết về yêu cầu bị lỗi (header và body nếu cần)
        System.out.println("Request URL: " + conn.getURL());
        System.out.println("Request Method: " + conn.getRequestMethod());
        System.out.println("Request Headers: " + conn.getRequestProperties());

        // Hiển thị thông báo lỗi chi tiết cho người dùng
        JOptionPane.showMessageDialog(null, "Lỗi từ server (Mã lỗi: " + errorCode + "): " + errorMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showError(String message, Exception e) {
        JOptionPane.showMessageDialog(null, message + ": " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
