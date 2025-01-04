package be.pxl.services.exception;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PostNotFoundExceptionTest {
    @Test
    void testExceptionWithMessage() {
        String message = "Post not found";
        PostNotFoundException exception = new PostNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionWithMessageAndCause() {
        String message = "Post not found";
        Throwable cause = new IllegalArgumentException("Invalid ID");
        PostNotFoundException exception = new PostNotFoundException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionWithNullMessage() {
        PostNotFoundException exception = new PostNotFoundException(null);

        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionWithNullMessageAndCause() {
        PostNotFoundException exception = new PostNotFoundException(null, null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}
