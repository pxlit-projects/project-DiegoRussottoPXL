package be.pxl.services.domain.dto;

import be.pxl.services.domain.PostStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRequestTest {

    @Test
    void testPostRequestBuilder() {
        // Arrange
        Long id = 1L;
        String title = "Test Title";
        String content = "Test Content";
        String author = "Test Author";
        LocalDate date = LocalDate.now();
        PostStatus status = PostStatus.DRAFT;

        // Act
        PostRequest postRequest = PostRequest.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .date(date)
                .status(status)
                .build();

        // Assert
        assertThat(postRequest.getId()).isEqualTo(id);
        assertThat(postRequest.getTitle()).isEqualTo(title);
        assertThat(postRequest.getContent()).isEqualTo(content);
        assertThat(postRequest.getAuthor()).isEqualTo(author);
        assertThat(postRequest.getDate()).isEqualTo(date);
        assertThat(postRequest.getStatus()).isEqualTo(status);
    }

    @Test
    void testPostRequestSetters() {
        // Arrange
        PostRequest postRequest = new PostRequest();
        Long id = 2L;
        String title = "Updated Title";
        String content = "Updated Content";
        String author = "Updated Author";
        LocalDate date = LocalDate.of(2023, 1, 1);
        PostStatus status = PostStatus.PUBLISHED;

        // Act
        postRequest.setId(id);
        postRequest.setTitle(title);
        postRequest.setContent(content);
        postRequest.setAuthor(author);
        postRequest.setDate(date);
        postRequest.setStatus(status);

        // Assert
        assertThat(postRequest.getId()).isEqualTo(id);
        assertThat(postRequest.getTitle()).isEqualTo(title);
        assertThat(postRequest.getContent()).isEqualTo(content);
        assertThat(postRequest.getAuthor()).isEqualTo(author);
        assertThat(postRequest.getDate()).isEqualTo(date);
        assertThat(postRequest.getStatus()).isEqualTo(status);
    }

    @Test
    void testPostRequestNoArgsConstructor() {
        // Act
        PostRequest postRequest = new PostRequest();

        // Assert
        assertThat(postRequest).isNotNull();
        assertThat(postRequest.getId()).isNull();
        assertThat(postRequest.getTitle()).isNull();
        assertThat(postRequest.getContent()).isNull();
        assertThat(postRequest.getAuthor()).isNull();
        assertThat(postRequest.getDate()).isNull();
        assertThat(postRequest.getStatus()).isNull();
    }

    @Test
    void testPostRequestAllArgsConstructor() {
        // Arrange
        Long id = 3L;
        String title = "All Args Title";
        String content = "All Args Content";
        String author = "All Args Author";
        LocalDate date = LocalDate.of(2022, 12, 31);
        PostStatus status = PostStatus.PENDING;

        // Act
        PostRequest postRequest = new PostRequest(id, title, content, author, date, status);

        // Assert
        assertThat(postRequest.getId()).isEqualTo(id);
        assertThat(postRequest.getTitle()).isEqualTo(title);
        assertThat(postRequest.getContent()).isEqualTo(content);
        assertThat(postRequest.getAuthor()).isEqualTo(author);
        assertThat(postRequest.getDate()).isEqualTo(date);
        assertThat(postRequest.getStatus()).isEqualTo(status);
    }
}
