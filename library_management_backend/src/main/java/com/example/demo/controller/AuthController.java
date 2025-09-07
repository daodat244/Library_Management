package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.BlacklistToken;
import com.example.demo.repository.BlacklistTokenRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String username = authentication.getName();
            String accessToken = jwtUtil.generateAccessToken(username);
            String refreshToken = jwtUtil.generateRefreshToken(username);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return ResponseEntity.ok(tokens);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Đăng nhập thất bại"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            BlacklistToken blacklistToken = new BlacklistToken();
            blacklistToken.setToken(token);
            blacklistToken.setExpiredAt(LocalDateTime.now().plusSeconds(
                    (jwtUtil.getExpirationFromToken(token) - System.currentTimeMillis()) / 1000));
            blacklistTokenRepository.save(blacklistToken);
            return ResponseEntity.ok(Map.of("message", "Đăng xuất thành công"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Token không hợp lệ"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || blacklistTokenRepository.existsByToken(refreshToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Refresh token không hợp lệ"));
        }
        if (jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(username);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Refresh token hết hạn"));
    }
}
