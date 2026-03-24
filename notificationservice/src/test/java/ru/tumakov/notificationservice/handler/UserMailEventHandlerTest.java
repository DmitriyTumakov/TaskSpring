package ru.tumakov.notificationservice.handler;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import ru.tumakov.servicedata.event.UserEvent;
import ru.tumakov.servicedata.type.Operation;

import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserMailEventHandlerTest {

    @Autowired
    private UserMailEventHandler userMailEventHandler;

    @Autowired
    private JavaMailSender mailSender;

    @RegisterExtension
    private static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig()
                    .withDisabledAuthentication());

    @Test
    public void testSendAccountCreateMail() throws MessagingException, IOException {
        UserEvent userEvent = new UserEvent("testUser@mail.ru", Operation.CREATE);

        userMailEventHandler.handle(userEvent);
        greenMail.waitForIncomingEmail(5000, 1);
        MimeMessage[] receivedMessage = greenMail.getReceivedMessages();
        MimeMessage message = receivedMessage[0];

        Assertions.assertNotNull(receivedMessage);
        Assertions.assertEquals(1, receivedMessage.length);
        Assertions.assertEquals("Аккаунт создан", message.getSubject());
        Assertions.assertEquals("Здравствуйте! Ваш аккаунт на сайте был успешно создан.", message.getContent().toString());
    }

    @Test
    public void testSendAccountDeleteMail() throws MessagingException, IOException {
        UserEvent userEvent = new UserEvent("testUser@mail.ru", Operation.DELETE);

        userMailEventHandler.handle(userEvent);
        greenMail.waitForIncomingEmail(5000, 1);
        MimeMessage[] receivedMessage = greenMail.getReceivedMessages();
        MimeMessage message = receivedMessage[0];

        Assertions.assertNotNull(receivedMessage);
        Assertions.assertEquals(1, receivedMessage.length);
        Assertions.assertEquals("Аккаунт удалён", message.getSubject());
        Assertions.assertEquals("Здравствуйте! Ваш аккаунт был удалён.", message.getContent().toString());
    }
}
