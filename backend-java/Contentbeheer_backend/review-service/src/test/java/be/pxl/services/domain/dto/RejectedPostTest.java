package be.pxl.services.domain.dto;

import be.pxl.services.domain.PostStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class RejectedPostTest {

    @Test
    void testRejectedPostBuilder() {
        // Arrange
        Long id = 1L;
        String title = "Test Post";
        String content = "This is a test post";
        String author = "Author Name";
        LocalDate date = LocalDate.now();
        PostStatus status = PostStatus.PENDING;
        String rejectionReason = "Duplicate content";

        // Act
        RejectedPost rejectedPost = RejectedPost.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .date(date)
                .status(status)
                .rejectionReason(rejectionReason)
                .build();

        // Assert
        assertThat(rejectedPost.getId()).isEqualTo(id);
        assertThat(rejectedPost.getTitle()).isEqualTo(title);
        assertThat(rejectedPost.getContent()).isEqualTo(content);
        assertThat(rejectedPost.getAuthor()).isEqualTo(author);
        assertThat(rejectedPost.getDate()).isEqualTo(date);
        assertThat(rejectedPost.getStatus()).isEqualTo(status);
        assertThat(rejectedPost.getRejectionReason()).isEqualTo(rejectionReason);
    }

    @Test
    void testRejectedPostSetters() {
        // Arrange
        RejectedPost rejectedPost = new RejectedPost();
        Long id = 2L;
        String title = "New Post";
        String content = "Updated content";
        String author = "New Author";
        LocalDate date = LocalDate.now();
        PostStatus status = PostStatus.DRAFT;
        String rejectionReason = "Invalid content";

        // Act
        rejectedPost.setId(id);
        rejectedPost.setTitle(title);
        rejectedPost.setContent(content);
        rejectedPost.setAuthor(author);
        rejectedPost.setDate(date);
        rejectedPost.setStatus(status);
        rejectedPost.setRejectionReason(rejectionReason);

        // Assert
        assertThat(rejectedPost.getId()).isEqualTo(id);
        assertThat(rejectedPost.getTitle()).isEqualTo(title);
        assertThat(rejectedPost.getContent()).isEqualTo(content);
        assertThat(rejectedPost.getAuthor()).isEqualTo(author);
        assertThat(rejectedPost.getDate()).isEqualTo(date);
        assertThat(rejectedPost.getStatus()).isEqualTo(status);
        assertThat(rejectedPost.getRejectionReason()).isEqualTo(rejectionReason);
    }

    @Test
    void testRejectedPostNoArgsConstructor() {
        // Act
        RejectedPost rejectedPost = new RejectedPost();

        // Assert
        assertThat(rejectedPost).isNotNull();
        assertThat(rejectedPost.getId()).isNull();
        assertThat(rejectedPost.getTitle()).isNull();
        assertThat(rejectedPost.getContent()).isNull();
        assertThat(rejectedPost.getAuthor()).isNull();
        assertThat(rejectedPost.getDate()).isNull();
        assertThat(rejectedPost.getStatus()).isNull();
        assertThat(rejectedPost.getRejectionReason()).isNull();
    }

    @Test
    void testRejectedPostAllArgsConstructor() {
        // Arrange
        Long id = 3L;
        String title = "Another Test Post";
        String content = "Content of another post";
        String author = "Author 3";
        LocalDate date = LocalDate.now();
        PostStatus status = PostStatus.PUBLISHED;
        String rejectionReason = "Plagiarism detected";

        // Act
        RejectedPost rejectedPost = new RejectedPost(id, title, content, author, date, status, rejectionReason);

        // Assert
        assertThat(rejectedPost.getId()).isEqualTo(id);
        assertThat(rejectedPost.getTitle()).isEqualTo(title);
        assertThat(rejectedPost.getContent()).isEqualTo(content);
        assertThat(rejectedPost.getAuthor()).isEqualTo(author);
        assertThat(rejectedPost.getDate()).isEqualTo(date);
        assertThat(rejectedPost.getStatus()).isEqualTo(status);
        assertThat(rejectedPost.getRejectionReason()).isEqualTo(rejectionReason);
    }
}
