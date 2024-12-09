package be.pxl.services.service;

import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.DraftedPost;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.feign.ReviewInterface;
import be.pxl.services.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final RabbitTemplate rabbitTemplate;
    private static final String QUEUE_NAME = "post-approval-queue";


    @Autowired
    ReviewInterface reviewInterface;

    public ResponseEntity<List<DraftedPost>> getDrafts() {
        System.out.println("Calling Feign client to get drafts...");
        List<PostResponse> drafts = reviewInterface.getDraftPosts().getBody();
        System.out.println("Received drafts: " + drafts);

        Review review = new Review();
        List<DraftedPost> draftedPosts = drafts.stream()
                .map(draft -> DraftedPost.builder()
                        .id(draft.getId())
                        .title(draft.getTitle())
                        .content(draft.getContent())
                        .author(draft.getAuthor())
                        .date(draft.getDate())
                        .status(draft.getStatus())
                        .build())
                .toList();
        System.out.println("Mapped drafted posts: " + draftedPosts);

        review.setDraftedPosts(draftedPosts);
        reviewRepository.save(review);

        // Return drafted posts as JSON
        return new ResponseEntity<>(draftedPosts, HttpStatus.CREATED);
    }
    public ResponseEntity<Void> publishPost(Long postId) {
        System.out.println("Calling Feign client to publish post...");
        ResponseEntity<Void> response = reviewInterface.publishPost(postId);
        System.out.println("Post with ID " + postId + " published successfully.");

        // Send a message to RabbitMQ
        String message = "Post " + postId + " is goedgekeurd.";
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);

        return response;
    }
    public ResponseEntity<Void> rejectPost(Long postId, String rejectReason) {
        System.out.println("Calling Feign client to reject post...");
        ResponseEntity<Void> response = reviewInterface.rejectPost(postId, rejectReason);
        System.out.println("Post with ID " + postId + " published successfully.");
        return response;
    }
    @Override
    public ResponseEntity<List<DraftedPost>> getRejectedPosts() {
        System.out.println("Calling Feign client to get rejected posts...");
        List<PostResponse> rejectedPosts = reviewInterface.getRejectedPosts().getBody();
        System.out.println("Received rejected posts: " + rejectedPosts);

        // Map naar DraftedPost
        List<DraftedPost> draftedPosts = rejectedPosts.stream()
                .map(post -> DraftedPost.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .date(post.getDate())
                        .status(post.getStatus())
                        .rejectReason(post.getRejectionReason()) // Zorg dat je dit veld toevoegt aan DraftedPost
                        .build())
                .toList();

        System.out.println("Mapped rejected posts: " + draftedPosts);

        // Optioneel: sla rejected posts op in een repository
        return new ResponseEntity<>(draftedPosts, HttpStatus.OK);
    }

}
