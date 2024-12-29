package be.pxl.services.service;

import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPost;
import be.pxl.services.feign.ReviewInterface;
import be.pxl.services.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
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


    @Autowired
    ReviewInterface reviewInterface;

    public ResponseEntity<List<PostResponse>> getDrafts() {
        System.out.println("Calling Feign client to get drafts...");
        List<PostResponse> drafts = reviewInterface.getDraftPosts().getBody();
        System.out.println("Received drafts: " + drafts);

        Review review = new Review();
        List<PostResponse> draftedPosts = drafts.stream()
                .map(draft -> PostResponse.builder()
                        .id(draft.getId())
                        .title(draft.getTitle())
                        .content(draft.getContent())
                        .author(draft.getAuthor())
                        .date(draft.getDate())
                        .status(draft.getStatus())
                        //.rejectionReason(draft.getRejectionReason())
                        .build())
                .toList();
        System.out.println("Mapped drafted posts: " + draftedPosts);

        //review.setDraftedPosts(draftedPosts);
        //reviewRepository.save(review);

        // Return drafted posts as JSON
        return new ResponseEntity<>(draftedPosts, HttpStatus.CREATED);
    }
    public ResponseEntity<Void> publishPost(Long postId) {
        System.out.println("Calling Feign client to publish post...");
        ResponseEntity<Void> response = reviewInterface.publishPost(postId);
        System.out.println("Post with ID " + postId + " published successfully.");


        return response;
    }
    public ResponseEntity<Void> rejectPost(Long postId, String rejectReason) {
        System.out.println("Calling Feign client to reject post...");
        ResponseEntity<Void> response = reviewInterface.rejectPost(postId);//, rejectReason);
        Review reject = new Review();
        reject.setPostId(postId);
        reject.setRejectReason(rejectReason);
        reviewRepository.save(reject);
        System.out.println("Post with ID " + postId + " published successfully.");
        return response;
    }
    @Override
    public ResponseEntity<List<RejectedPost>> getRejectedPosts() {
        // Haal alle afgewezen reviews uit de database
        List<Review> rejectedReviews = reviewRepository.findAll();

        if (rejectedReviews.isEmpty()) {
            return new ResponseEntity<>(List.of(), HttpStatus.OK); // Geen afgewezen berichten gevonden
        }
        PostResponse postResponsee = reviewInterface.getPostById(252L).getBody();

        // Zoek bijbehorende berichten aan de hand van postId
        List<RejectedPost> rejectedPosts = rejectedReviews.stream()
                .map(review -> {
                    // Haal de post op via de Feign client
                    PostResponse postResponse = reviewInterface.getPostById(review.getPostId()).getBody();

                    if (postResponse != null) {
                        // Bouw een RejectedPost DTO
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
                        // Als de post niet gevonden is, retourner een lege DTO met alleen de rejectReason
                        return RejectedPost.builder()
                                .id(review.getPostId())
                                .rejectionReason(review.getRejectReason())
                                .build();
                    }
                })
                .toList();

        // Retourneer de lijst van afgewezen posts
        return new ResponseEntity<>(rejectedPosts, HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<Void> resubmitPost(Long postId, PostRequest postRequest) {
        System.out.println("Calling Feign client to resubmit post...");
        ResponseEntity<Void> response = reviewInterface.resubmitPost(postId, postRequest);
        reviewRepository.deleteByPostId(postId);
        System.out.println("Post with ID " + postId + " resubmitted successfully.");

        return response;
    }

}
