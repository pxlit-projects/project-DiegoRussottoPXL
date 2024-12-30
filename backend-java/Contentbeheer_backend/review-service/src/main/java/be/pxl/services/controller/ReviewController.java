package be.pxl.services.controller;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPost;
import be.pxl.services.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    @GetMapping("/drafts")
    public ResponseEntity<List<PostResponse>> getDraftPosts() {
        log.info("Fetching all drafts");
        return reviewService.getDrafts();
    }
    @PutMapping("/post/{id}/publish")
    public ResponseEntity<Void> publishPost(@PathVariable Long id) {
        log.info("Publishing post with id: {}", id);
        return reviewService.publishPost(id);
    }
    @PutMapping("/post/{id}/reject")
    public ResponseEntity<Void> rejectPost(@PathVariable Long id,  @RequestBody String rejectReason) {
        log.info("Rejecting post with id: {}", id);
        return reviewService.rejectPost(id, rejectReason);
    }
    @GetMapping("/rejected")
    public ResponseEntity<List<RejectedPost>> getRejectedPosts() {
        log.info("Fetching all rejected posts");
        return reviewService.getRejectedPosts();
    }
    @PutMapping("/post/{id}/resubmit")
    public ResponseEntity<Void> resubmitPost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        log.info("Resubmitting post with id: {}", id);
        return reviewService.resubmitPost(id, postRequest);
    }

}
