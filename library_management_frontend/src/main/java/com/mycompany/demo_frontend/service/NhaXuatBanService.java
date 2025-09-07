package com.mycompany.demo_frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.NhaXuatBanDTO;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NhaXuatBanService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api/nhaxuatban";
    
    public List<NhaXuatBanDTO> getAllNXB() throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, new TypeReference<List<NhaXuatBanDTO>>() {});
    }
    
    public NhaXuatBanDTO getNXBById(int id) throws Exception {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, NhaXuatBanDTO.class);
    }
    
    public NhaXuatBanDTO addNXB(NhaXuatBanDTO nxb) throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        objectMapper.writeValue(os, nxb);
        os.flush();
        os.close();

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, NhaXuatBanDTO.class);
    }
    
    public boolean updateNXB(int id, NhaXuatBanDTO nxb) throws Exception {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        // Gửi dữ liệu JSON
        OutputStream os = conn.getOutputStream();
        objectMapper.writeValue(os, nxb);
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();

        // Trả về true nếu cập nhật thành công
        return responseCode == 200 || responseCode == 204;
    }
    
    public boolean deleteNXB(int id) throws Exception {
        try {
            URL url = new URL(baseUrl + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<NhaXuatBanDTO> searchNXB(String searchText, String criteria) throws Exception {
        String encodedText = URLEncoder.encode(searchText, StandardCharsets.UTF_8);
        String encodedCriteria = URLEncoder.encode(criteria, StandardCharsets.UTF_8);
        URL url = new URL(baseUrl + "/search?searchText=" + encodedText + "&criteria=" + encodedCriteria);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, new TypeReference<List<NhaXuatBanDTO>>() {});
    }
}
