package com.memento.service;

public interface EmailService {

    void sendMail(String emailTo, String verificationToken);
}
