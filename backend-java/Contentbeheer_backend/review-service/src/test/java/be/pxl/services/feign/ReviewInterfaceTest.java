package be.pxl.services.feign;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewInterfaceTest {
    @Mock
    private ReviewInterface reviewInterface;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDraftPosts() {
        List<PostResponse> mockDrafts = List.of(new PostResponse(1L, "Draft 1", "Content", "Author", null, null));

        when(reviewInterface.getDraftPosts()).thenReturn(ResponseEntity.ok(mockDrafts));

        ResponseEntity<List<PostResponse>> response = reviewInterface.getDraftPosts();

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals("Draft 1", response.getBody().get(0).getTitle());
        verify(reviewInterface, times(1)).getDraftPosts();
    }

    @Test
    void testPublishPost() {
        Long postId = 1L;

        when(reviewInterface.publishPost(postId)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Void> response = reviewInterface.publishPost(postId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(reviewInterface, times(1)).publishPost(postId);
    }

    @Test
    void testResubmitPost() {
        Long postId = 1L;
        PostRequest postRequest = new PostRequest(postId, "Updated Title", "Updated Content", "AuthorG", null,null);

        when(reviewInterface.resubmitPost(postId, postRequest)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Void> response = reviewInterface.resubmitPost(postId, postRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(reviewInterface, times(1)).resubmitPost(postId, postRequest);
    }

    @Test
    void testGetPostById() {
        Long postId = 1L;
        PostResponse mockPost = new PostResponse(postId, "Post Title", "Post Content", "Author", null, null);

        when(reviewInterface.getPostById(postId)).thenReturn(ResponseEntity.ok(mockPost));

        ResponseEntity<PostResponse> response = reviewInterface.getPostById(postId);

        assertNotNull(response);
        assertEquals("Post Title", response.getBody().getTitle());
        verify(reviewInterface, times(1)).getPostById(postId);
    }

}
