package com.glowin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${SPRING_MAIL_USERNAME}")
    private String mailUsername;

    @Value("${SPRING_MAIL_HOST}")
    private String mailHost;

    @Value("${SPRING_MAIL_PORT}")
    private String mailPort;

    @Value("${SPRING_MAIL_PASSWORD}")
    private String mailPassword;

    @Value("${SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH}")
    private String mailSmtpAuth;

    @Value("${SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE}")
    private String mailStarttlsEnable;

    @Value("${SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST}")
    private String mailSslTrust;

    @Value("${SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST}")
    private String mailLocalhost;

    public void logMailProperties() {
        System.out.println("SPRING_MAIL_USERNAME: " + mailUsername);
        System.out.println("SPRING_MAIL_HOST: " + mailHost);
        System.out.println("SPRING_MAIL_PORT: " + mailPort);
        System.out.println("SPRING_MAIL_PASSWORD: " + mailPassword);
        System.out.println("SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH: " + mailSmtpAuth);
        System.out.println("SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE: " + mailStarttlsEnable);
        System.out.println("SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST: " + mailSslTrust);
        System.out.println("SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST: " + mailLocalhost);
    }

    public void sendConfirmationEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(mailUsername);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}