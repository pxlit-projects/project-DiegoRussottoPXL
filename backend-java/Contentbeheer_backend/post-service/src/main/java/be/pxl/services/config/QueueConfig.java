package be.pxl.services.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QueueConfig {
    @Bean
    public Queue myApprovedPostQueue() {
        return new Queue("postApprovalQueue", true);
    }

    @Bean
    public Queue myRejectedPostQueue() {
        return new Queue("postRejectionQueue", true);
    }
}

