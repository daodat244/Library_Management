package com.example.demo.repository;

import com.example.demo.entity.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, String> {
    boolean existsByToken(String token);
    void deleteByExpiredAtBefore(LocalDateTime time);
}
