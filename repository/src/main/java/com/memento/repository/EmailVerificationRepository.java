package com.memento.repository;

import com.memento.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByUser_Email(String email);

    Optional<EmailVerificationToken> findByToken(String token);

    /**
     * Delete all expired email verification tokens for the current day
     */
    @Query(value = "DELETE FROM EmailVerificationToken e WHERE e.isEmailVerified = false AND CURRENT_TIMESTAMP > e.expiryTime")
    void deleteAllExpiredEVTForCurrentDay();
}
