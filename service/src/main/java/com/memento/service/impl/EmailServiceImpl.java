package com.memento.service.impl;

import com.memento.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Primary
public class EmailServiceImpl implements EmailService {

    private static final String EMAIL_VERIFY_ENDPOINT = "/api/email/verify?token=";

    @Value("${spring.mail.username}")
    private String from;

    @Value("${app.url.instance}")
    private String mementoUrl;

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String emailTo, String token) {
        try {
            final String url = mementoUrl + EMAIL_VERIFY_ENDPOINT + token;
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(emailTo);
            message.setSubject("Memento Email Verification");
            message.setText(String.format("Please click on the following link to verify your email %s", url));
            javaMailSender.send(message);

        } catch (MailException e) {
            log.error(e.getMessage());
            throw new MailSendException(e.getMessage());
        }
    }
}
