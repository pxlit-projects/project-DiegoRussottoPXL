package be.pxl.services.services;


import be.pxl.services.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender emailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail_Success() {
        String subject = "Test Subject";
        String body = "Test Body";
        String senderEmail = "test@example.com";

        // Simuleer de @Value injectie
        emailService.senderEmail = senderEmail;

        // Capture the email message that gets sent
        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendEmail(subject, body);

        // Verifieer dat de emailSender de send() methode aanroept
        verify(emailSender, times(1)).send(emailCaptor.capture());

        SimpleMailMessage capturedEmail = emailCaptor.getValue();

        assertNotNull(capturedEmail);
        assertEquals("gamzeh8@gmail.com", capturedEmail.getTo()[0]);
        assertEquals(senderEmail, capturedEmail.getFrom());
        assertEquals(subject, capturedEmail.getSubject());
        assertEquals(body, capturedEmail.getText());
    }
}
