package com.mycompany.demo_frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.TacGiaDTO;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TacGiaService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api/tacgia";
    
    public List<TacGiaDTO> getAllTacGia() throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, new TypeReference<List<TacGiaDTO>>() {});
    }
    
    public TacGiaDTO getTacGiaById(int id) throws Exception {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, TacGiaDTO.class);
    }
    
    public TacGiaDTO addTacGia(TacGiaDTO tacGia) throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        objectMapper.writeValue(os, tacGia);
        os.flush();
        os.close();

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, TacGiaDTO.class);
    }
    
    public boolean updateTacGia(int id, TacGiaDTO tacGia) throws Exception {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        // Gửi dữ liệu JSON
        OutputStream os = conn.getOutputStream();
        objectMapper.writeValue(os, tacGia);
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();

        // Trả về true nếu cập nhật thành công
        return responseCode == 200 || responseCode == 204;
    }
    
    public boolean deleteTacGia(int id) throws Exception {
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
    
    public List<TacGiaDTO> searchTacGia(String searchText, String criteria) throws Exception {
        String encodedText = URLEncoder.encode(searchText, StandardCharsets.UTF_8);
        String encodedCriteria = URLEncoder.encode(criteria, StandardCharsets.UTF_8);
        URL url = new URL(baseUrl + "/search?searchText=" + encodedText + "&criteria=" + encodedCriteria);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, new TypeReference<List<TacGiaDTO>>() {});
    }
}
