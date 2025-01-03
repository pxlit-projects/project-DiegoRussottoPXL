package be.pxl.services.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    private JavaMailSender emailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void sendEmail(String subject, String body) {
        logger.info("Preparing email with subject: '{}' and body: '{}'", subject, body);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("gamzeh8@gmail.com");
        email.setFrom(senderEmail);
        email.setSubject(subject);
        email.setText(body);

        emailSender.send(email);

        logger.info("Email sent successfully to {}", "gamzeh8@gmail.com");
    }
}

