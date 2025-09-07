package com.mycompany.demo_frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.SachDTO;
import com.mycompany.demo_frontend.dto.SachInputDTO;
import com.mycompany.demo_frontend.dto.TacGiaDTO;
import com.mycompany.demo_frontend.dto.NhaXuatBanDTO;
import com.mycompany.demo_frontend.dto.TheLoaiDTO;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SachService {
    private static final String BASE_URL = "http://localhost:8080/api/sach";
    private static final String TACGIA_URL = "http://localhost:8080/api/tacgia";
    private static final String NHAXUATBAN_URL = "http://localhost:8080/api/nhaxuatban";
    private static final String THELOAI_URL = "http://localhost:8080/api/theloai";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<SachDTO> getAllSach() {
        try {
            HttpURLConnection conn = setupConnection(BASE_URL, "GET");
            System.out.println("Calling GET " + BASE_URL + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                List<SachDTO> result = Arrays.asList(objectMapper.readValue(conn.getInputStream(), SachDTO[].class));
                System.out.println("Retrieved " + result.size() + " books");
                return result;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tải danh sách sách: " + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public SachDTO getSachById(String id) {
        try {
            HttpURLConnection conn = setupConnection(BASE_URL + "/" + id, "GET");
            System.out.println("Calling GET " + BASE_URL + "/" + id + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                return objectMapper.readValue(conn.getInputStream(), SachDTO.class);
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi lấy sách theo mã: " + e.getMessage(), e);
        }
        return null;
    }

    public List<SachDTO> getSachByMaSachList(List<String> maSachList) {
        try {
            if (maSachList == null || maSachList.isEmpty()) {
                System.out.println("maSachList is null or empty");
                return new ArrayList<>();
            }
            String idsParam = String.join(",", maSachList);
            String urlString = BASE_URL + "/searchByIds?ids=" + URLEncoder.encode(idsParam, StandardCharsets.UTF_8);
            HttpURLConnection conn = setupConnection(urlString, "GET");
            System.out.println("Calling GET " + urlString + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                List<SachDTO> result = objectMapper.readValue(conn.getInputStream(), new TypeReference<List<SachDTO>>() {});
                System.out.println("Retrieved " + result.size() + " books by IDs");
                return result;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi lấy danh sách sách theo mã: " + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public SachDTO addSach(SachInputDTO sach) {
        try {
            HttpURLConnection conn = setupConnection(BASE_URL, "POST");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                objectMapper.writeValue(os, sach);
                os.flush();
            }
            System.out.println("Calling POST " + BASE_URL + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
                return objectMapper.readValue(conn.getInputStream(), SachDTO.class);
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi thêm sách: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean updateSach(String id, SachInputDTO sach) {
        try {
            HttpURLConnection conn = setupConnection(BASE_URL + "/" +id, "PUT");
            conn.setDoOutput(true);
            sach.setMasach(id);
            try (OutputStream os = conn.getOutputStream()) {
                objectMapper.writeValue(os, sach);
                os.flush();
            }
            System.out.println("Calling PUT " + BASE_URL + ", Response Code: " + conn.getResponseCode());
            boolean success = conn.getResponseCode() == 200 || conn.getResponseCode() == 204;
            if (!success) {
                handleError(conn);
            }
            conn.disconnect();
            return success;
        } catch (Exception e) {
            showError("Lỗi khi cập nhật sách: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteSach(String id) {
        try {
            HttpURLConnection conn = setupConnection(BASE_URL + "/" + id, "DELETE");
            System.out.println("Calling DELETE " + BASE_URL + "/" + id + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 204) {
                conn.disconnect();
                return true;
            } else {
                String errorMsg = "";
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                    errorMsg = reader.lines().collect(Collectors.joining());
                    Map<String, String> errorResponse = objectMapper.readValue(errorMsg, new TypeReference<Map<String, String>>() {});
                    String error = errorResponse.get("error");
                    showError("Lỗi khi xóa sách: " + error, null);
                } catch (Exception e) {
                    showError("Lỗi khi xóa sách: " + errorMsg, e);
                }
                conn.disconnect();
                return false;
            }
        } catch (Exception e) {
            showError("Lỗi khi xóa sách: " + e.getMessage(), e);
            return false;
        }
    }

    public List<SachDTO> searchSach(String searchText, String criteria) {
        try {
            System.out.println("Preparing search: searchText=" + searchText + ", criteria=" + criteria);
            String encodedText = URLEncoder.encode(searchText, StandardCharsets.UTF_8.name());
            String encodedCriteria = URLEncoder.encode(criteria, StandardCharsets.UTF_8.name());
            String urlString = BASE_URL + "/search?searchText=" + encodedText + "&criteria=" + encodedCriteria;
            HttpURLConnection conn = setupConnection(urlString, "GET");
            System.out.println("Calling GET " + urlString + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                List<SachDTO> result = objectMapper.readValue(conn.getInputStream(), new TypeReference<List<SachDTO>>() {});
                System.out.println("Search returned " + result.size() + " results");
                return result;
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tìm kiếm sách: " + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public List<TacGiaDTO> getAllTacGia() {
        try {
            HttpURLConnection conn = setupConnection(TACGIA_URL, "GET");
            System.out.println("Calling GET " + TACGIA_URL + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                return objectMapper.readValue(conn.getInputStream(), new TypeReference<List<TacGiaDTO>>() {});
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tải danh sách tác giả: " + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public List<NhaXuatBanDTO> getAllNhaXuatBan() {
        try {
            HttpURLConnection conn = setupConnection(NHAXUATBAN_URL, "GET");
            System.out.println("Calling GET " + NHAXUATBAN_URL + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                return objectMapper.readValue(conn.getInputStream(), new TypeReference<List<NhaXuatBanDTO>>() {});
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tải danh sách nhà xuất bản: " + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public List<TheLoaiDTO> getAllTheLoai() {
        try {
            HttpURLConnection conn = setupConnection(THELOAI_URL, "GET");
            System.out.println("Calling GET " + THELOAI_URL + ", Response Code: " + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                return objectMapper.readValue(conn.getInputStream(), new TypeReference<List<TheLoaiDTO>>() {});
            } else {
                handleError(conn);
            }
            conn.disconnect();
        } catch (Exception e) {
            showError("Lỗi khi tải danh sách thể loại: " + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    private HttpURLConnection setupConnection(String urlString, String method) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        String token = AuthStorage.getAccessToken();
        System.out.println("Token: " + (token != null ? "Present" : "Null"));
        if (token != null) conn.setRequestProperty("Authorization", "Bearer " + token);
        return conn;
    }

    private void handleError(HttpURLConnection conn) throws IOException {
        String errorMsg = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
            errorMsg = reader.lines().collect(Collectors.joining());
        }
        System.out.println("Error from server: " + errorMsg + ", Code: " + conn.getResponseCode());
        JOptionPane.showMessageDialog(null, "Lỗi từ server: " + errorMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showError(String message, Exception e) {
        System.out.println(message + ": " + (e != null ? e.getMessage() : "No details"));
        JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
