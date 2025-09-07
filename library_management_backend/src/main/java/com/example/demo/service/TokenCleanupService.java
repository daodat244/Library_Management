package com.example.demo.service;

import com.example.demo.repository.BlacklistTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TokenCleanupService {

    @Autowired
    private BlacklistTokenRepository repository;

    @Scheduled(cron = "0 0 00 * * ?") // Chạy lúc 00:00 hàng ngày
    public void cleanupExpiredTokens() {
        repository.deleteByExpiredAtBefore(LocalDateTime.now());
    }
}
