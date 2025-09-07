package com.mycompany.demo_frontend.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.DocGiaDTO;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DocGiaService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api/docgia";

    public List<DocGiaDTO> getAllDocGia() throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, new TypeReference<List<DocGiaDTO>>() {});
    }

    public DocGiaDTO getDocGiaById(int id) throws Exception {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, DocGiaDTO.class);
    }

    public DocGiaDTO addDocGia(DocGiaDTO docGia) throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        objectMapper.writeValue(os, docGia);
        os.flush();
        os.close();

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, DocGiaDTO.class);
    }

    public boolean updateDocGia(int id, DocGiaDTO docGia) throws Exception {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");

        // Gửi dữ liệu JSON
        OutputStream os = conn.getOutputStream();
        objectMapper.writeValue(os, docGia);
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();

        // Trả về true nếu cập nhật thành công
        return responseCode == 200 || responseCode == 204;
    }


    public boolean deleteDocGia(int id) throws Exception {
        try {
            URL url = new URL(baseUrl + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

            // KHÔNG cần getOutputStream
            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ✅ Tìm kiếm theo mã độc giả và tên độc giả
    public List<DocGiaDTO> searchDocGia(String searchText, String criteria) throws Exception {
        String encodedText = URLEncoder.encode(searchText, StandardCharsets.UTF_8);
        String encodedCriteria = URLEncoder.encode(criteria, StandardCharsets.UTF_8);
        URL url = new URL(baseUrl + "/search?searchText=" + encodedText + "&criteria=" + encodedCriteria);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + AuthStorage.getAccessToken());

        InputStream is = conn.getInputStream();
        return objectMapper.readValue(is, new TypeReference<List<DocGiaDTO>>() {});
    }

}

