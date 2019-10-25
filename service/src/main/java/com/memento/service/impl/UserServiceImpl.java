package com.memento.service.impl;

import com.memento.model.EmailVerificationToken;
import com.memento.model.User;
import com.memento.repository.UserRepository;
import com.memento.service.EmailService;
import com.memento.service.EmailVerificationService;
import com.memento.service.RoleService;
import com.memento.service.UserService;
import com.memento.shared.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
@Primary
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EmailService emailService;
    private final EmailVerificationService verificationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final RoleService roleService,
                           final EmailService emailService,
                           final EmailVerificationService verificationService,
                           final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.emailService = emailService;
        this.verificationService = verificationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Set<User> getAll() {
        return Set.copyOf(userRepository.findAll());
    }

    @Override
    @Transactional
    public void register(final User user) {
        Objects.requireNonNull(user, "User cannot be null.");

        if (!userRepository.findByEmail(user.getEmail()).isEmpty()) {
            throw new DuplicateKeyException("email exists.");
        }

        final User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(roleService.findRoleByRoleName(user.getRole().getRoleName()))
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .build();

        final User savedUser = userRepository.save(newUser);
        final EmailVerificationToken emailVerificationToken = EmailVerificationToken.from(savedUser);
        emailService.sendMail(newUser.getEmail(), emailVerificationToken.getToken());
        verificationService.save(emailVerificationToken);
    }

    @Override
    public User update(final User user) {
        Objects.requireNonNull(user, "User cannot be null.");
        final User oldUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Cannot find user with id: " + user.getId()));
        final User newUser = User.builder()
                .id(oldUser.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public User findByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Cannot find user with email: " + email));
    }

    @Override
    public User findById(final Long id) {
        Objects.requireNonNull(id, "id cannot be null.");
        log.info("Search for user with id: " + id);
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find user with id: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        Objects.requireNonNull(email, "Email cannot be null.");
        final User user =  userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Cannot find Email with email: " + email));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Set.of(user.getRole()))
                .build();
    }
}
