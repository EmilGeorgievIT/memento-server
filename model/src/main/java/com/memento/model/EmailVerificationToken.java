package com.memento.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "email_verification_tokens", indexes = {@Index(name = "uidx_evt_token", unique = true, columnList = "token")})
public class EmailVerificationToken implements Serializable {

    private static final long serialVersionUID = -2920113489880098521L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private boolean isEmailVerified;

    @Column(name = "expiry_time", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant expiryTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @PrePersist
    private void updateCreatedAt() {
        expiryTime = Instant.now().plus(15, ChronoUnit.MINUTES);
    }

    public static EmailVerificationToken from(final User user) {
        return EmailVerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .isEmailVerified(false)
                .user(user)
                .build();
    }
}
