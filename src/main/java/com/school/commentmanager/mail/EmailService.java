package com.school.commentmanager.mail;

import com.school.commentmanager.model.RegisterPerson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.receiver.email}")
    private String to;

    @PostMapping
    public void sendEmail(@RequestBody RegisterPerson person) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Запит " + person.getPhoneNumber());
        String body = "Повне імʼя: " + person.getFullName() + "\n" +
                "Пошта: " + person.getEmail() + "\n" +
                "Телефон: " + person.getPhoneNumber();
        message.setText(body);

        mailSender.send(message);
    }
}