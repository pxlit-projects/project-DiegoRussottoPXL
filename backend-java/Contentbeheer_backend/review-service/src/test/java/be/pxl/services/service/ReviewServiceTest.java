package be.pxl.services.service;

import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPost;
import be.pxl.services.feign.ReviewInterface;
import be.pxl.services.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ReviewInterface reviewInterface;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;
    private PostResponse postResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        review = new Review();
        review.setPostId(1L);
        review.setRejectReason("Not suitable");

        postResponse = new PostResponse(1L, "Test Title", "Test Content", "Test Author", null, null);
    }

    @Test
    void testGetDrafts() {
        // Mock the Feign client to return drafts
        List<PostResponse> drafts = List.of(postResponse);
        ResponseEntity<List<PostResponse>> mockResponse = new ResponseEntity<>(drafts, HttpStatus.CREATED);
        when(reviewInterface.getDraftPosts()).thenReturn(mockResponse);

        ResponseEntity<List<PostResponse>> response = reviewService.getDrafts();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Title", response.getBody().get(0).getTitle());
    }

    @Test
    void testPublishPost() {
        // Mock the Feign client to publish post
        ResponseEntity<Void> mockResponse = new ResponseEntity<>(HttpStatus.OK);
        when(reviewInterface.publishPost(1L)).thenReturn(mockResponse);

        ResponseEntity<Void> response = reviewService.publishPost(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(rabbitTemplate).convertAndSend(eq("postApprovalQueue"), eq(1L));
    }

    @Test
    void testRejectPost() {
        // Mock the Feign client to reject post
        ResponseEntity<Void> mockResponse = new ResponseEntity<>(HttpStatus.OK);
        when(reviewInterface.rejectPost(1L)).thenReturn(mockResponse);

        // Mock the repository save method
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ResponseEntity<Void> response = reviewService.rejectPost(1L, "Not suitable");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reviewRepository).save(any(Review.class));
        verify(rabbitTemplate).convertAndSend(eq("postRejectionQueue"), eq(1L));
    }

    @Test
    void testGetRejectedPosts() {
        // Mock the repository to return rejected posts
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        // Mock the Feign client to return post details
        ResponseEntity<PostResponse> mockPostResponse = new ResponseEntity<>(postResponse, HttpStatus.OK);
        when(reviewInterface.getPostById(1L)).thenReturn(mockPostResponse);

        ResponseEntity<List<RejectedPost>> response = reviewService.getRejectedPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Title", response.getBody().get(0).getTitle());
    }

    @Test
    void testResubmitPost() {
        // Mock the Feign client to resubmit the post
        ResponseEntity<Void> mockResponse = new ResponseEntity<>(HttpStatus.OK);
        when(reviewInterface.resubmitPost(1L, new PostRequest())).thenReturn(mockResponse);

        ResponseEntity<Void> response = reviewService.resubmitPost(1L, new PostRequest());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reviewRepository).deleteByPostId(1L);
    }
}
