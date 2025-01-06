package be.pxl.services.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

public class PostTest {

    @Test
    void testPostGettersAndSetters() {
        // Arrange
        Long id = 1L;
        String title = "Test Title";
        String content = "Test Content";
        String author = "Test Author";
        LocalDate date = LocalDate.now();
        PostStatus status = PostStatus.DRAFT;

        // Act
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        post.setDate(date);
        post.setStatus(status);

        // Assert
        assertThat(post.getId()).isEqualTo(id);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getDate()).isEqualTo(date);
        assertThat(post.getStatus()).isEqualTo(status);
    }

    @Test
    void testPostBuilder() {
        // Arrange
        Long id = 1L;
        String title = "Builder Title";
        String content = "Builder Content";
        String author = "Builder Author";
        LocalDate date = LocalDate.of(2023, 1, 1);
        PostStatus status = PostStatus.PUBLISHED;

        // Act
        Post post = Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .date(date)
                .status(status)
                .build();

        // Assert
        assertThat(post.getId()).isEqualTo(id);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getDate()).isEqualTo(date);
        assertThat(post.getStatus()).isEqualTo(status);
    }

    @Test
    void testPostNoArgsConstructor() {
        // Act
        Post post = new Post();

        // Assert
        assertThat(post).isNotNull();
        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isNull();
        assertThat(post.getContent()).isNull();
        assertThat(post.getAuthor()).isNull();
        assertThat(post.getDate()).isNull();
        assertThat(post.getStatus()).isNull();
    }

    @Test
    void testPostAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String title = "Constructor Title";
        String content = "Constructor Content";
        String author = "Constructor Author";
        LocalDate date = LocalDate.of(2023, 1, 1);
        PostStatus status = PostStatus.PUBLISHED;

        // Act
        Post post = new Post(id, title, content, author, date, status);

        // Assert
        assertThat(post.getId()).isEqualTo(id);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
        assertThat(post.getDate()).isEqualTo(date);
        assertThat(post.getStatus()).isEqualTo(status);
    }
}
