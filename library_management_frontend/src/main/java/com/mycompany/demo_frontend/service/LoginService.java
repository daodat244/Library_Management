package com.mycompany.demo_frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.LoginDTO;
import com.mycompany.demo_frontend.dto.LoginResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginService {
    public static LoginResponse login(String username, String password) {
        try {
            URL url = new URL("http://localhost:8080/api/auth/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Gửi thông tin đăng nhập
            LoginDTO loginDTO = new LoginDTO(username, password);
            ObjectMapper mapper = new ObjectMapper();
            String jsonInput = mapper.writeValueAsString(loginDTO);

            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();

            if (conn.getResponseCode() == 200) {
                // Parse response
                LoginResponse response = mapper.readValue(conn.getInputStream(), LoginResponse.class);

                // ✅ Lưu và in token
                String token = response.getAccessToken();
                AuthStorage.setAccessToken(token);
                System.out.println("✅ TOKEN SAU KHI LOGIN: " + token);

                return response;
            } else {
                System.out.println("❌ Đăng nhập thất bại. HTTP code: " + conn.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
