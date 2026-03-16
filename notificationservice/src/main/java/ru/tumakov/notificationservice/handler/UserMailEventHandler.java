package ru.tumakov.notificationservice.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.tumakov.event.UserEvent;
import ru.tumakov.notificationservice.service.MailService;

@Component
@KafkaListener(topics = "mail-sent-events-topic")
public class UserMailEventHandler {

    @Autowired
    private MailService mailService;

    @KafkaHandler()
    public void handle(UserEvent userEvent) {
        mailService.sendMail(userEvent);
    }
}
