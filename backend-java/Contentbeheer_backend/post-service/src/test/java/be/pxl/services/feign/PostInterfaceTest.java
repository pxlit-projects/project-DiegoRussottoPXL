package be.pxl.services.feign;

import be.pxl.services.domain.dto.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostInterfaceTest {
    @Mock
    private PostInterface postInterface;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentsById() {
        Long postId = 1L;
        List<CommentResponse> mockComments = List.of(
                new CommentResponse(1L, postId, "Author1", "Content1", LocalDate.now()),
                new CommentResponse(2L, postId, "Author2", "Content2", LocalDate.now())
        );
        when(postInterface.getCommentsById(postId)).thenReturn(ResponseEntity.ok(mockComments));

        ResponseEntity<List<CommentResponse>> response = postInterface.getCommentsById(postId);

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals("Author1", response.getBody().get(0).getAuthor());
        verify(postInterface, times(1)).getCommentsById(postId);
    }
}
