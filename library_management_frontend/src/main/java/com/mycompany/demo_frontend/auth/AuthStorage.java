package com.mycompany.demo_frontend.auth;

import com.mycompany.demo_frontend.dto.NhanVienDTO;

public class AuthStorage {

    private static String accessToken;
    private static String refreshToken;
    private static NhanVienDTO currentNhanVien;

    public static void saveTokens(String access, String refresh) {
        accessToken = access;
        refreshToken = refresh;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static boolean isLoggedIn() {
        return accessToken != null && !accessToken.isEmpty();
    }

    public static void clear() {
        accessToken = null;
        refreshToken = null;
    }

    public static void setCurrentNhanVien(NhanVienDTO nhanVien) {
        currentNhanVien = nhanVien;
    }

    public static NhanVienDTO getCurrentNhanVien() {
        return currentNhanVien;
    }
}
