package be.pxl.services.domain.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentResponseTest {

    @Test
    void testCommentResponseBuilder() {
        // Arrange
        Long id = 1L;
        Long postId = 2L;
        String author = "TestAuthor";
        String content = "This is a test comment response";
        LocalDate timestamp = LocalDate.now();

        // Act
        CommentResponse commentResponse = CommentResponse.builder()
                .id(id)
                .postId(postId)
                .author(author)
                .content(content)
                .timestamp(timestamp)
                .build();

        // Assert
        assertThat(commentResponse.getId()).isEqualTo(id);
        assertThat(commentResponse.getPostId()).isEqualTo(postId);
        assertThat(commentResponse.getAuthor()).isEqualTo(author);
        assertThat(commentResponse.getContent()).isEqualTo(content);
        assertThat(commentResponse.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void testCommentResponseSetters() {
        // Arrange
        CommentResponse commentResponse = new CommentResponse();
        Long id = 3L;
        Long postId = 4L;
        String author = "AnotherAuthor";
        String content = "Updated comment response content";
        LocalDate timestamp = LocalDate.now();

        // Act
        commentResponse.setId(id);
        commentResponse.setPostId(postId);
        commentResponse.setAuthor(author);
        commentResponse.setContent(content);
        commentResponse.setTimestamp(timestamp);

        // Assert
        assertThat(commentResponse.getId()).isEqualTo(id);
        assertThat(commentResponse.getPostId()).isEqualTo(postId);
        assertThat(commentResponse.getAuthor()).isEqualTo(author);
        assertThat(commentResponse.getContent()).isEqualTo(content);
        assertThat(commentResponse.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void testCommentResponseNoArgsConstructor() {
        // Act
        CommentResponse commentResponse = new CommentResponse();

        // Assert
        assertThat(commentResponse).isNotNull();
        assertThat(commentResponse.getId()).isNull();
        assertThat(commentResponse.getPostId()).isNull();
        assertThat(commentResponse.getAuthor()).isNull();
        assertThat(commentResponse.getContent()).isNull();
        assertThat(commentResponse.getTimestamp()).isNull();
    }

    @Test
    void testCommentResponseAllArgsConstructor() {
        // Arrange
        Long id = 5L;
        Long postId = 6L;
        String author = "Author5";
        String content = "Another comment response";
        LocalDate timestamp = LocalDate.now();

        // Act
        CommentResponse commentResponse = new CommentResponse(id, postId, author, content, timestamp);

        // Assert
        assertThat(commentResponse.getId()).isEqualTo(id);
        assertThat(commentResponse.getPostId()).isEqualTo(postId);
        assertThat(commentResponse.getAuthor()).isEqualTo(author);
        assertThat(commentResponse.getContent()).isEqualTo(content);
        assertThat(commentResponse.getTimestamp()).isEqualTo(timestamp);
    }
}
