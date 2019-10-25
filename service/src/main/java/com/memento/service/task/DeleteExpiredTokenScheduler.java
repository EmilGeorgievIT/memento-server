package com.memento.service.task;

import com.memento.repository.EmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredTokenScheduler {

    private final EmailVerificationRepository emailVerificationRepository;

    @Autowired
    public DeleteExpiredTokenScheduler(final EmailVerificationRepository emailVerificationRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Scheduled(cron = "0 * 0/6 * * *")
    public void deleteExpiredTokens() {
        emailVerificationRepository.deleteAllExpiredEVTForCurrentDay();
    }
}
