package ru.tumakov.notificationservice.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.tumakov.event.UserEvent;
import ru.tumakov.type.Operation;

@Component
@KafkaListener(topics = "mail-sent-events-topic")
public class UserMailEventHandler {
    @Value("${notification.service.mail}")
    String serviceEmail;

    @Autowired
    private JavaMailSender mailSender;

    @KafkaHandler()
    public void handle(UserEvent userEvent) {
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
