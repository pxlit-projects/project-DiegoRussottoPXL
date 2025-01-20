package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.exception.PostNotFoundException;
import be.pxl.services.repository.PostRepository;
import be.pxl.services.service.EmailService;
import be.pxl.services.service.QueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Optional;

class QueueServiceTest {

    @InjectMocks
    private QueueService queueService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandlePostApproval_Success() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setTitle("Test Post");
        post.setStatus(PostStatus.PENDING);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        queueService.handlePostApproval(postId);

        verify(postRepository).save(post);
        verify(emailService).sendEmail(
                "Blog Approved: Test Post",
                "Congratulations! Your blog titled \"Test Post\" has been approved and is ready for publication."
        );
        assert(post.getStatus() == PostStatus.PUBLISHED);
    }

    @Test
    void testHandlePostApproval_PostNotFound() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        try {
            queueService.handlePostApproval(postId);
        } catch (PostNotFoundException ex) {
            assert(ex.getMessage().equals("No post found with ID: " + postId));
        }

        verify(postRepository, never()).save(any(Post.class));
        verify(emailService, never()).sendEmail(anyString(), anyString());
    }

    @Test
    void testHandlePostRejection_Success() {
        Long postId = 2L;
        Post post = new Post();
        post.setId(postId);
        post.setTitle("Test Post");
        post.setStatus(PostStatus.PENDING);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        queueService.handlePostRejection(postId);

        verify(postRepository).save(post);
        verify(emailService).sendEmail(
                "Blog Rejected: Test Post",
                "Unfortunately, your blog titled \"Test Post\" has been rejected. Please revise it before resubmitting."
        );
        assert(post.getStatus() == PostStatus.PENDING);
    }

    @Test
    void testHandlePostRejection_PostNotFound() {
        Long postId = 2L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        try {
            queueService.handlePostRejection(postId);
        } catch (PostNotFoundException ex) {
            assert(ex.getMessage().equals("No post found with ID: " + postId));
        }

        verify(postRepository, never()).save(any(Post.class));
        verify(emailService, never()).sendEmail(anyString(), anyString());
    }
}
