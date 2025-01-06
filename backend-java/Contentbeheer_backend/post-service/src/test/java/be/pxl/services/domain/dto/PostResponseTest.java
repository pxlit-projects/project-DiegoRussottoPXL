package be.pxl.services.domain.dto;

import be.pxl.services.domain.PostStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PostResponseTest {

    @Test
    void testPostResponseBuilder() {
        // Arrange
        Long id = 1L;
        String title = "Test Title";
        String content = "Test Content";
        String author = "Test Author";
        LocalDate date = LocalDate.now();
        PostStatus status = PostStatus.DRAFT;

        // Act
        PostResponse postResponse = PostResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .date(date)
                .status(status)
                .build();

        // Assert
        assertThat(postResponse.getId()).isEqualTo(id);
        assertThat(postResponse.getTitle()).isEqualTo(title);
        assertThat(postResponse.getContent()).isEqualTo(content);
        assertThat(postResponse.getAuthor()).isEqualTo(author);
        assertThat(postResponse.getDate()).isEqualTo(date);
        assertThat(postResponse.getStatus()).isEqualTo(status);
    }

    @Test
    void testPostResponseSetters() {
        // Arrange
        PostResponse postResponse = new PostResponse();
        Long id = 2L;
        String title = "Updated Title";
        String content = "Updated Content";
        String author = "Updated Author";
        LocalDate date = LocalDate.of(2023, 1, 1);
        PostStatus status = PostStatus.PUBLISHED;

        // Act
        postResponse.setId(id);
        postResponse.setTitle(title);
        postResponse.setContent(content);
        postResponse.setAuthor(author);
        postResponse.setDate(date);
        postResponse.setStatus(status);

        // Assert
        assertThat(postResponse.getId()).isEqualTo(id);
        assertThat(postResponse.getTitle()).isEqualTo(title);
        assertThat(postResponse.getContent()).isEqualTo(content);
        assertThat(postResponse.getAuthor()).isEqualTo(author);
        assertThat(postResponse.getDate()).isEqualTo(date);
        assertThat(postResponse.getStatus()).isEqualTo(status);
    }

    @Test
    void testPostResponseNoArgsConstructor() {
        // Act
        PostResponse postResponse = new PostResponse();

        // Assert
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getId()).isNull();
        assertThat(postResponse.getTitle()).isNull();
        assertThat(postResponse.getContent()).isNull();
        assertThat(postResponse.getAuthor()).isNull();
        assertThat(postResponse.getDate()).isNull();
        assertThat(postResponse.getStatus()).isNull();
    }

    @Test
    void testPostResponseAllArgsConstructor() {
        // Arrange
        Long id = 3L;
        String title = "All Args Title";
        String content = "All Args Content";
        String author = "All Args Author";
        LocalDate date = LocalDate.of(2022, 12, 31);
        PostStatus status = PostStatus.PENDING;

        // Act
        PostResponse postResponse = new PostResponse(id, title, content, author, date, status);

        // Assert
        assertThat(postResponse.getId()).isEqualTo(id);
        assertThat(postResponse.getTitle()).isEqualTo(title);
        assertThat(postResponse.getContent()).isEqualTo(content);
        assertThat(postResponse.getAuthor()).isEqualTo(author);
        assertThat(postResponse.getDate()).isEqualTo(date);
        assertThat(postResponse.getStatus()).isEqualTo(status);
    }
}
