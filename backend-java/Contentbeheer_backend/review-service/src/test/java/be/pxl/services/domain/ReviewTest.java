package be.pxl.services.domain;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewTest {

    @Test
    void testReviewBuilder() {
        // Arrange
        Long id = 1L;
        Long postId = 123L;
        String rejectReason = "Inappropriate content";

        // Act
        Review review = Review.builder()
                .id(id)
                .postId(postId)
                .rejectReason(rejectReason)
                .build();

        // Assert
        assertThat(review.getId()).isEqualTo(id);
        assertThat(review.getPostId()).isEqualTo(postId);
        assertThat(review.getRejectReason()).isEqualTo(rejectReason);
    }

    @Test
    void testReviewSetters() {
        // Arrange
        Review review = new Review();
        Long id = 2L;
        Long postId = 456L;
        String rejectReason = "Spam";

        // Act
        review.setId(id);
        review.setPostId(postId);
        review.setRejectReason(rejectReason);

        // Assert
        assertThat(review.getId()).isEqualTo(id);
        assertThat(review.getPostId()).isEqualTo(postId);
        assertThat(review.getRejectReason()).isEqualTo(rejectReason);
    }

    @Test
    void testReviewNoArgsConstructor() {
        // Act
        Review review = new Review();

        // Assert
        assertThat(review).isNotNull();
        assertThat(review.getId()).isNull();
        assertThat(review.getPostId()).isNull();
        assertThat(review.getRejectReason()).isNull();
    }

    @Test
    void testReviewAllArgsConstructor() {
        // Arrange
        Long id = 3L;
        Long postId = 789L;
        String rejectReason = "Plagiarism";

        // Act
        Review review = new Review(id, postId, rejectReason);

        // Assert
        assertThat(review.getId()).isEqualTo(id);
        assertThat(review.getPostId()).isEqualTo(postId);
        assertThat(review.getRejectReason()).isEqualTo(rejectReason);
    }
}

