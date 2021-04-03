package com.anshishagua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * MyMailService.java
 *
 * @author lixiao
 * @date 2021-03-31
 */

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String subject, String email, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(subject);
        message.setTo(email);
        message.setText(content);
        mailSender.send(message);
    }
}
