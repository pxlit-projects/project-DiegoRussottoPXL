package be.pxl.services.controller;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPost;
import be.pxl.services.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping("/drafts")
    public ResponseEntity<List<PostResponse>> getDraftPosts() {
        return reviewService.getDrafts();
    }
    @PutMapping("/post/{id}/publish")
    public ResponseEntity<Void> publishPost(@PathVariable Long id) {
        return reviewService.publishPost(id);
    }
    @PutMapping("/post/{id}/reject")
    public ResponseEntity<Void> rejectPost(@PathVariable Long id,  @RequestBody String rejectReason) {
        return reviewService.rejectPost(id, rejectReason);
    }
    @GetMapping("/rejected")
    public ResponseEntity<List<RejectedPost>> getRejectedPosts() {
        return reviewService.getRejectedPosts();
    }
    @PutMapping("/post/{id}/resubmit")
    public ResponseEntity<Void> resubmitPost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        return reviewService.resubmitPost(id, postRequest);
    }

}
