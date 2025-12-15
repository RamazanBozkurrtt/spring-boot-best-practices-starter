package org.project.bestpractice.service.concretes;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.bestpractice.repository.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserScheduledTask {

    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredTokens() {
        log.info("Token temizliği başladı: {}", Instant.now());

        try {
            refreshTokenRepository.deleteByExpiryDateBefore((Instant.now()));
            log.info("Süresi dolmuş tokenlar veritabanından başarıyla temizlendi.");
        } catch (Exception e) {
            log.error("Token temizliği sırasında hata oluştu!", e);
        }
    }

}
