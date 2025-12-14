package org.project.bestpractice.service.concretes;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.bestpractice.repository.TokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserScheduledTask {


    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanExpireToken(){
        log.info("Cleaning expired tokens");
        try{
            tokenRepository.deleteAllByExpiredTrueOrRevokedTrue();
        }catch (Exception e){
            log.error("Error while cleaning expired tokens : {}", e.getMessage());
        }

    }


}
