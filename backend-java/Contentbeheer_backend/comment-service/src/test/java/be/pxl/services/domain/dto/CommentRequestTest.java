package be.pxl.services.domain.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentRequestTest {

    @Test
    void testCommentRequestBuilder() {
        // Arrange
        Long postId = 1L;
        String author = "TestAuthor";
        String content = "This is a test comment request";
        LocalDate timestamp = LocalDate.now();

        // Act
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(postId)
                .author(author)
                .content(content)
                .timestamp(timestamp)
                .build();

        // Assert
        assertThat(commentRequest.getPostId()).isEqualTo(postId);
        assertThat(commentRequest.getAuthor()).isEqualTo(author);
        assertThat(commentRequest.getContent()).isEqualTo(content);
        assertThat(commentRequest.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void testCommentRequestSetters() {
        // Arrange
        CommentRequest commentRequest = new CommentRequest();
        Long postId = 2L;
        String author = "AnotherAuthor";
        String content = "Updated comment request content";
        LocalDate timestamp = LocalDate.now();

        // Act
        commentRequest.setPostId(postId);
        commentRequest.setAuthor(author);
        commentRequest.setContent(content);
        commentRequest.setTimestamp(timestamp);

        // Assert
        assertThat(commentRequest.getPostId()).isEqualTo(postId);
        assertThat(commentRequest.getAuthor()).isEqualTo(author);
        assertThat(commentRequest.getContent()).isEqualTo(content);
        assertThat(commentRequest.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void testCommentRequestNoArgsConstructor() {
        // Act
        CommentRequest commentRequest = new CommentRequest();

        // Assert
        assertThat(commentRequest).isNotNull();
        assertThat(commentRequest.getPostId()).isNull();
        assertThat(commentRequest.getAuthor()).isNull();
        assertThat(commentRequest.getContent()).isNull();
        assertThat(commentRequest.getTimestamp()).isNull();
    }

    @Test
    void testCommentRequestAllArgsConstructor() {
        // Arrange
        Long postId = 3L;
        String author = "Author3";
        String content = "Another comment request";
        LocalDate timestamp = LocalDate.now();

        // Act
        CommentRequest commentRequest = new CommentRequest(postId, author, content, timestamp);

        // Assert
        assertThat(commentRequest.getPostId()).isEqualTo(postId);
        assertThat(commentRequest.getAuthor()).isEqualTo(author);
        assertThat(commentRequest.getContent()).isEqualTo(content);
        assertThat(commentRequest.getTimestamp()).isEqualTo(timestamp);
    }
}
