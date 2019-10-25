package com.memento.service.impl;

import com.memento.model.EmailVerificationToken;
import com.memento.repository.EmailVerificationRepository;
import com.memento.service.EmailVerificationService;
import com.memento.shared.exception.EmailVerificationTimeExpiryException;
import com.memento.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;

    @Autowired
    public EmailVerificationServiceImpl(final EmailVerificationRepository emailVerificationRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Override
    public boolean isEmailVerified(String email) {
        final Optional<EmailVerificationToken> token = emailVerificationRepository.findByUser_Email(email);
        return token.isPresent() && token.get().isEmailVerified();
    }

    @Override
    @Transactional
    public void verifyEmail(String verificationToken) {
        final EmailVerificationToken emailVerificationToken = getEmailVerificationToken(verificationToken);
        validateVerificationActiveToken(emailVerificationToken);
        final EmailVerificationToken verifiedToken =
                EmailVerificationToken.builder()
                        .id(emailVerificationToken.getId())
                        .expiryTime(emailVerificationToken.getExpiryTime())
                        .user(emailVerificationToken.getUser())
                        .isEmailVerified(true)
                        .build();

        emailVerificationRepository.save(verifiedToken);
    }

    @Override
    public void save(EmailVerificationToken emailVerificationToken) {
        emailVerificationRepository.save(emailVerificationToken);
    }

    private EmailVerificationToken getEmailVerificationToken(String verificationToken) {
        return emailVerificationRepository.findByToken(verificationToken)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Invalid verification token %s", verificationToken)));
    }

    private void validateVerificationActiveToken(EmailVerificationToken emailVerificationToken) {
        if (emailVerificationToken.getExpiryTime().toEpochMilli() - Instant.now().toEpochMilli() <= 0) {
            throw new EmailVerificationTimeExpiryException("Verification token has expired.");
        }
    }
}
