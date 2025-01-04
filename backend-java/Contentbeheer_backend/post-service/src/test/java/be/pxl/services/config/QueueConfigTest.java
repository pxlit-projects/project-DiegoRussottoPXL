package be.pxl.services.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class QueueConfigTest {
    @Test
    void testMyApprovedPostQueue() {
        QueueConfig queueConfig = new QueueConfig();
        Queue queue = queueConfig.myApprovedPostQueue();

        assertNotNull(queue, "Queue should not be null");
        assertEquals("postApprovalQueue", queue.getName());
        assertTrue(queue.isDurable(), "Queue should be durable");
    }

    @Test
    void testMyRejectedPostQueue() {
        QueueConfig queueConfig = new QueueConfig();
        Queue queue = queueConfig.myRejectedPostQueue();

        assertNotNull(queue, "Queue should not be null");
        assertEquals("postRejectionQueue", queue.getName());
        assertTrue(queue.isDurable(), "Queue should be durable");
    }
}
