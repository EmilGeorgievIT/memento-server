package com.memento.web.endpoint;

import com.memento.service.EmailVerificationService;
import com.memento.web.endpoint.api.EmailVerificationApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/email", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EmailVerificationApiController implements EmailVerificationApi {

    private final EmailVerificationService emailVerificationService;

    @Autowired
    public EmailVerificationApiController(final EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<HttpStatus> confirmRegistration(@RequestParam String token) {
        emailVerificationService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }
}
