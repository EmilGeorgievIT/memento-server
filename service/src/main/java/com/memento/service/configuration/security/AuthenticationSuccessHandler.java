package com.memento.service.configuration.security;

import com.memento.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.expires_in:10000}")
    private int expires_in;

    @Autowired
    public AuthenticationSuccessHandler(final JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        clearAuthenticationAttributes(request);
        final User user = (User) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(user, user.getRole().getAuthority());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(token);
    }

}
