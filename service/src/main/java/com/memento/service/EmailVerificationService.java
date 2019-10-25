package com.memento.service;

import com.memento.model.EmailVerificationToken;

public interface EmailVerificationService {

    boolean isEmailVerified(String email);

    void verifyEmail(String verificationToken);

    void save(EmailVerificationToken emailVerificationToken);
}
