package com.memento.service.configuration.security;

import com.google.common.base.Strings;
import com.memento.model.User;
import com.memento.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Component
public class MementoAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MementoAuthenticationProvider(final UserService userService,
                                         final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (Strings.isNullOrEmpty(authentication.getName()) || Objects.isNull(authentication.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(null, null, null);
        }

        final String email = authentication.getName();
        final String password = authentication.getCredentials().toString();

        final User user = userService.findByEmail(email);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password doesn't match");
        }

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), Set.of(user.getRole()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
