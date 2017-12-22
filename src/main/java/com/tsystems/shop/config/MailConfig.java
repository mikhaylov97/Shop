package com.tsystems.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    private static final String HOST = "smtp.gmail.com";

    public static final String USERNAME = "blacklionru.store@gmail.com";

    private static final String PASSWORD = "blacklion123";

    private static final int PORT = 587;

    @Value("${email.subject}")
    private String subject;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(HOST);
        javaMailSender.setUsername(USERNAME);
        javaMailSender.setPassword(PASSWORD);
        javaMailSender.setPort(PORT);

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
}
