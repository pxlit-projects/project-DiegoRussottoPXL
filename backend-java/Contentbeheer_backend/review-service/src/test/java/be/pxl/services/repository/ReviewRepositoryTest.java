package be.pxl.services.repository;

import be.pxl.services.domain.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        review1 = Review.builder()
                .postId(1L)
                .rejectReason("Not suitable")
                .build();
        review2 = Review.builder()
                .postId(2L)
                .rejectReason("Content is spam")
                .build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);
    }

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll();
    }

    @Test
    void testSaveReview() {
        // Check if the reviews are saved
        assertNotNull(review1.getId());
        assertNotNull(review2.getId());

        List<Review> reviews = reviewRepository.findAll();
        assertEquals(2, reviews.size());
    }

    @Test
    void testDeleteByPostId() {
        // Delete review by postId
        reviewRepository.deleteByPostId(1L);

        List<Review> reviews = reviewRepository.findAll();
        assertEquals(1, reviews.size());

        // Ensure the review with postId 1L was deleted
        assertNull(reviewRepository.findById(review1.getId()).orElse(null));
    }

    @Test
    void testDeleteByPostId_notExist() {
        // Try deleting with a postId that doesn't exist
        reviewRepository.deleteByPostId(999L);

        List<Review> reviews = reviewRepository.findAll();
        assertEquals(2, reviews.size()); // No reviews should be deleted
    }
}
