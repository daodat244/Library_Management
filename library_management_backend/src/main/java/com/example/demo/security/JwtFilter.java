package com.example.demo.security;

import com.example.demo.repository.BlacklistTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String role = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (blacklistTokenRepository.existsByToken(token)) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Token đã bị vô hiệu hóa");
                return;
            }
            try {
                username = jwtUtil.getUsernameFromToken(token);
                role = jwtUtil.getRoleFromToken(token);
            } catch (Exception e) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ");
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token)) {
                if (request.getRequestURI().startsWith("/api/nhanvien") && 
                    !"QUAN_LY".equals(role) && 
                    (request.getMethod().equals("POST") || request.getMethod().equals("PUT") || request.getMethod().equals("DELETE"))) {
                    sendError(response, HttpServletResponse.SC_FORBIDDEN, "Không có quyền truy cập, yêu cầu role QUAN_LY");
                    return;
                }

                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Token đã hết hạn");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}