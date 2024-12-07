package be.pxl.services.service;

import be.pxl.services.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void sendMessage(Notification notification) {
        log.info("Receiving notification...");
        log.info("Sending ... {}", notification.getMessage());
        log.info("TO {}", notification.getSender());
    }
    @RabbitListener(queues = "post-approval-queue")
    public void receiveMessage(String message) {
        System.out.println("Melding ontvangen: " + message);
        // Voeg hier je logica toe om de melding te sturen, bijvoorbeeld via e-mail of een andere notificatiemethode
    }
}
