package ru.tumakov.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.tumakov.servicedata.event.UserEvent;
import ru.tumakov.servicedata.type.Operation;

@Service
public class MailService {
    @Value("${notification.service.mail}")
    String serviceEmail;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(UserEvent userEvent) {
        String email = userEvent.getEmail();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        if (userEvent.getOperation().equals(Operation.CREATE)) {
            mailMessage.setSubject("Аккаунт создан");
            mailMessage.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
        } else {
            mailMessage.setSubject("Аккаунт удалён");
            mailMessage.setText("Здравствуйте! Ваш аккаунт был удалён.");
        }
        mailMessage.setFrom(serviceEmail);
        mailSender.send(mailMessage);
    }
}
