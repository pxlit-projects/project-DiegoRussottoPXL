package be.pxl.services.repository;

import be.pxl.services.PostServiceApplication;
import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = PostServiceApplication.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setup() {
        // Voeg enkele voorbeeldposts toe aan de in-memory database
        postRepository.save(Post.builder()
                .title("Post 1")
                .content("Content 1")
                .author("Author 1")
                .date(LocalDate.now())
                .status(PostStatus.DRAFT)
                .build());

        postRepository.save(Post.builder()
                .title("Post 2")
                .content("Content 2")
                .author("Author 2")
                .date(LocalDate.now())
                .status(PostStatus.PUBLISHED)
                .build());
    }

    @Test
    public void testFindById() {
        // Arrange
        Post post = postRepository.save(Post.builder()
                .title("Post 3")
                .content("Content 3")
                .author("Author 3")
                .date(LocalDate.now())
                .status(PostStatus.DRAFT)
                .build());

        // Act
        Optional<Post> result = postRepository.findById(post.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Post 3", result.get().getTitle());
    }

    @Test
    public void testFindByStatus() {
        // Act
        List<Post> drafts = postRepository.findByStatus(PostStatus.DRAFT);
        List<Post> published = postRepository.findByStatus(PostStatus.PUBLISHED);

        // Assert
        assertEquals(2, drafts.size());
        assertEquals(1, published.size());
    }

    @Test
    public void testSave() {
        // Arrange
        Post post = Post.builder()
                .title("New Post")
                .content("New Content")
                .author("New Author")
                .date(LocalDate.now())
                .status(PostStatus.DRAFT)
                .build();

        // Act
        Post savedPost = postRepository.save(post);

        // Assert
        assertEquals("New Post", savedPost.getTitle());
        assertEquals(PostStatus.DRAFT, savedPost.getStatus());
    }

    @Test
    public void testFindAll() {
        // Act
        List<Post> allPosts = postRepository.findAll();

        // Assert
        assertEquals(2, allPosts.size());
    }
}
