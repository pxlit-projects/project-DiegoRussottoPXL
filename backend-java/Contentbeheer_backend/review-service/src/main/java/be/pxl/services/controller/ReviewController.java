package be.pxl.services.controller;

import be.pxl.services.domain.dto.DraftedPost;
import be.pxl.services.domain.dto.PostRequest;
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
    public ResponseEntity<List<DraftedPost>> getDraftPosts() {
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
}
