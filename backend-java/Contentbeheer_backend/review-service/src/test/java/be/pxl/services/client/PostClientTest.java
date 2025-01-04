package be.pxl.services.client;

import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.PostStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostClientTest {
    @Mock
    private PostClient postClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPostById() {
        Long postId = 1L;
        PostResponse mockPost = PostResponse.builder()
                .id(postId)
                .title("Test Post")
                .content("This is a test content")
                .author("John Doe")
                .date(LocalDate.now())
                .status(PostStatus.PUBLISHED)
                .build();

        when(postClient.getPostById(postId)).thenReturn(mockPost);

        PostResponse postResponse = postClient.getPostById(postId);

        assertNotNull(postResponse);
        assertEquals("Test Post", postResponse.getTitle());
        assertEquals("John Doe", postResponse.getAuthor());
        assertEquals(PostStatus.PUBLISHED, postResponse.getStatus());
        verify(postClient, times(1)).getPostById(postId);
    }
}
