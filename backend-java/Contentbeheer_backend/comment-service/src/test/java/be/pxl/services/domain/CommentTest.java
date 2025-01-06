package be.pxl.services.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentTest {

    @Test
    void testCommentBuilder() {
        // Arrange
        Long id = 1L;
        Long postId = 100L;
        String user = "TestUser";
        String content = "This is a test comment";
        LocalDate createdDate = LocalDate.now();

        // Act
        Comment comment = Comment.builder()
                .id(id)
                .postId(postId)
                .user(user)
                .content(content)
                .createdDate(createdDate)
                .build();

        // Assert
        assertThat(comment.getId()).isEqualTo(id);
        assertThat(comment.getPostId()).isEqualTo(postId);
        assertThat(comment.getUser()).isEqualTo(user);
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getCreatedDate()).isEqualTo(createdDate);
    }

    @Test
    void testCommentSetters() {
        // Arrange
        Comment comment = new Comment();
        Long id = 2L;
        Long postId = 200L;
        String user = "AnotherUser";
        String content = "Updated comment content";
        LocalDate createdDate = LocalDate.now();

        // Act
        comment.setId(id);
        comment.setPostId(postId);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedDate(createdDate);

        // Assert
        assertThat(comment.getId()).isEqualTo(id);
        assertThat(comment.getPostId()).isEqualTo(postId);
        assertThat(comment.getUser()).isEqualTo(user);
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getCreatedDate()).isEqualTo(createdDate);
    }

    @Test
    void testCommentNoArgsConstructor() {
        // Act
        Comment comment = new Comment();

        // Assert
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isNull();
        assertThat(comment.getPostId()).isNull();
        assertThat(comment.getUser()).isNull();
        assertThat(comment.getContent()).isNull();
        assertThat(comment.getCreatedDate()).isNull();
    }

    @Test
    void testCommentAllArgsConstructor() {
        // Arrange
        Long id = 3L;
        Long postId = 300L;
        String user = "User3";
        String content = "Another comment";
        LocalDate createdDate = LocalDate.now();

        // Act
        Comment comment = new Comment(id, postId, user, content, createdDate);

        // Assert
        assertThat(comment.getId()).isEqualTo(id);
        assertThat(comment.getPostId()).isEqualTo(postId);
        assertThat(comment.getUser()).isEqualTo(user);
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getCreatedDate()).isEqualTo(createdDate);
    }
}
