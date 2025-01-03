package be.pxl.services.service;

import be.pxl.services.controller.ReviewController;
import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPost;
import be.pxl.services.feign.ReviewInterface;
import be.pxl.services.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    @Autowired
    ReviewInterface reviewInterface;
    public ResponseEntity<List<PostResponse>> getDrafts() {
        log.info("Calling Feign client to get drafts...");
        List<PostResponse> drafts = reviewInterface.getDraftPosts().getBody();
        log.info("Received drafts: " + drafts);
        List<PostResponse> draftedPosts = drafts.stream()
                .map(draft -> PostResponse.builder()
                        .id(draft.getId())
                        .title(draft.getTitle())
                        .content(draft.getContent())
                        .author(draft.getAuthor())
                        .date(draft.getDate())
                        .status(draft.getStatus())
                        .build())
                .toList();
        return new ResponseEntity<>(draftedPosts, HttpStatus.CREATED);
    }
    public ResponseEntity<Void> publishPost(Long postId) {
        log.info("Calling Feign client to publish post with id: {}", postId);
        ResponseEntity<Void> response = reviewInterface.publishPost(postId);
        sendNotificationToQueue("postApprovalQueue", postId);

        return response;
    }
    public ResponseEntity<Void> rejectPost(Long postId, String rejectReason) {
        log.info("Calling Feign client to reject post with id: {}", postId);
        ResponseEntity<Void> response = reviewInterface.rejectPost(postId);
        Review reject = new Review();
        reject.setPostId(postId);
        reject.setRejectReason(rejectReason);
        reviewRepository.save(reject);
        sendNotificationToQueue("postRejectionQueue", postId);
        return response;
    }
    @Override
    public ResponseEntity<List<RejectedPost>> getRejectedPosts() {
        log.info("Fetching all rejected posts from the database...");
        List<Review> rejectedReviews = reviewRepository.findAll();

        if (rejectedReviews.isEmpty()) {
            log.info("No rejected posts found in the database.");
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }

        log.info("Found {} rejected reviews in the database. Mapping to RejectedPost objects...", rejectedReviews.size());
        List<RejectedPost> rejectedPosts = rejectedReviews.stream()
                .map(review -> {
                    log.info("Fetching post details for post ID: {}", review.getPostId());
                    PostResponse postResponse = reviewInterface.getPostById(review.getPostId()).getBody();

                    if (postResponse != null) {
                        log.info("Post details found for post ID: {}. Adding rejection reason.", review.getPostId());
                        return RejectedPost.builder()
                                .id(postResponse.getId())
                                .title(postResponse.getTitle())
                                .content(postResponse.getContent())
                                .author(postResponse.getAuthor())
                                .date(postResponse.getDate())
                                .status(postResponse.getStatus())
                                .rejectionReason(review.getRejectReason())
                                .build();
                    } else {
                        log.warn("Post details not found for post ID: {}. Adding rejection reason without post details.", review.getPostId());
                        return RejectedPost.builder()
                                .id(review.getPostId())
                                .rejectionReason(review.getRejectReason())
                                .build();
                    }
                })
                .toList();

        log.info("Successfully mapped rejected reviews to {} RejectedPost objects.", rejectedPosts.size());
        return new ResponseEntity<>(rejectedPosts, HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<Void> resubmitPost(Long postId, PostRequest postRequest) {
        log.info("Calling Feign client to resubmit post with id: {}", postId);
        ResponseEntity<Void> response = reviewInterface.resubmitPost(postId, postRequest);
        reviewRepository.deleteByPostId(postId);
        return response;
    }
    private void sendNotificationToQueue(String queueName, Long postId) {
        rabbitTemplate.convertAndSend(queueName, postId);
        log.info("Notification sent for post ID {} to queue {}.", postId, queueName);
    }
}
