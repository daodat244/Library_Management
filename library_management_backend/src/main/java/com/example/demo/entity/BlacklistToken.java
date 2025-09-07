package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "blacklist_token")
public class BlacklistToken {
    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
}
