package be.pxl.services.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = EmailConfig.class)
public class EmailConfigTest {
    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void testJavaMailSenderBean() {
        assertNotNull(javaMailSender, "JavaMailSender bean should not be null");
    }
}
