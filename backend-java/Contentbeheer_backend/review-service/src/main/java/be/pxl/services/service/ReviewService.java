package be.pxl.services.service;

import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.DraftedPost;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.feign.ReviewInterface;
import be.pxl.services.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    ReviewInterface reviewInterface;

    public ResponseEntity<String> getDrafts() {
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

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
