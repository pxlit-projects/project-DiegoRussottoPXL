package be.pxl.services.services;


import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.feign.PostInterface;
import be.pxl.services.repository.PostRepository;
import be.pxl.services.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostInterface postInterface;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPosts() {
        List<Post> posts = Arrays.asList(
                Post.builder().id(1L).title("Title 1").content("Content 1").author("Author 1").date(LocalDate.now()).status(PostStatus.PUBLISHED).build(),
                Post.builder().id(2L).title("Title 2").content("Content 2").author("Author 2").date(LocalDate.now()).status(PostStatus.PUBLISHED).build()
        );

        when(postRepository.findAll()).thenReturn(posts);

        List<PostResponse> result = postService.getAllPosts();

        assertEquals(2, result.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testGetPostById() {
        Long postId = 1L;
        Post post = Post.builder().id(postId).title("Test Title").content("Test Content").author("Author").date(LocalDate.now()).status(PostStatus.PUBLISHED).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        PostResponse result = postService.getPostById(postId);

        assertEquals("Test Title", result.getTitle());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    public void testGetPostById_NotFound() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> postService.getPostById(postId));
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    public void testAddPost() {
        PostRequest postRequest = new PostRequest(null, "New Post", "Content", "Author", LocalDate.now(), PostStatus.DRAFT);
        Post savedPost = Post.builder().id(1L).title("New Post").content("Content").author("Author").date(LocalDate.now()).status(PostStatus.DRAFT).build();

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        postService.addPost(postRequest);

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testPublishPost() {
        Long postId = 1L;
        Post post = Post.builder().id(postId).status(PostStatus.DRAFT).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.publishPost(postId);

        assertEquals(PostStatus.PUBLISHED, post.getStatus());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void testGetCommentsById() {
        Long postId = 1L;
        List<CommentResponse> comments = Arrays.asList(
                new CommentResponse(1L, postId, "Author 1", "Comment 1", LocalDate.now())
        );

        // Mock de interactie met postInterface
        when(postInterface.getCommentsById(postId)).thenReturn(ResponseEntity.ok(comments));

        // Roep de methode aan die getest moet worden
        List<CommentResponse> result = postService.getCommentsById(postId);

        // Controleer de resultaten
        assertEquals(1, result.size());
        assertEquals("Comment 1", result.get(0).getContent());

        // Verifieer dat de mock werd aangeroepen
        verify(postInterface, times(1)).getCommentsById(postId);
    }



    @Test
    public void testGetFilteredPosts() {
        List<Post> posts = Arrays.asList(
                Post.builder().id(1L).title("Title 1").content("Content 1").author("Author 1").date(LocalDate.now()).build(),
                Post.builder().id(2L).title("Title 2").content("Content 2").author("Author 2").date(LocalDate.now()).build()
        );

        when(postRepository.findAll()).thenReturn(posts);

        List<PostResponse> result = postService.getFilteredPosts("Author 1", null, null);

        assertEquals(1, result.size());
        assertEquals("Author 1", result.get(0).getAuthor());
        verify(postRepository, times(1)).findAll();
    }
}
