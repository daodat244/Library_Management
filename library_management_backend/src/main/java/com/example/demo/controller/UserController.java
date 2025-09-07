package com.example.demo.controller;

import com.example.demo.dto.ChangePasswordRequest;
import com.example.demo.entity.BlacklistToken;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.BlacklistTokenRepository;
import com.example.demo.repository.NhanVienRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;


    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        // Lấy token từ header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Thiếu token hoặc định dạng sai");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Token không hợp lệ");
        }

        String username = jwtUtil.getUsernameFromToken(token);
        var nv = nhanVienRepository.findByUsername(username);
        if (nv.isPresent()) {
            return ResponseEntity.ok(nv.get());
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy nhân viên");
        }
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ChangePasswordRequest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Thiếu token hoặc định dạng sai"));
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("error", "Token không hợp lệ"));
        }

        String username = jwtUtil.getUsernameFromToken(token);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, request.getOldPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Mật khẩu cũ không đúng"));
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Mật khẩu mới và xác nhận không khớp"));
        }

        NhanVien nv = nhanVienRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));
        nv.setPassword(passwordEncoder.encode(request.getNewPassword()));
        nhanVienRepository.save(nv);

        BlacklistToken blacklistToken = new BlacklistToken();
        blacklistToken.setToken(token);
        blacklistToken.setExpiredAt(LocalDateTime.now().plusSeconds(
                (jwtUtil.getExpirationFromToken(token) - System.currentTimeMillis()) / 1000));
        blacklistTokenRepository.save(blacklistToken);

        return ResponseEntity.ok(Map.of("message", "Đổi mật khẩu thành công"));
    }
}
