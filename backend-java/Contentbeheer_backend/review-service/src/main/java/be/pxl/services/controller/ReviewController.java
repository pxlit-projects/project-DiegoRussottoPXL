package be.pxl.services.controller;

import be.pxl.services.domain.dto.DraftedPost;
import be.pxl.services.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping("/drafts")
    public ResponseEntity<List<DraftedPost>> getDraftPosts() {
        // Haal drafts op via ReviewService
        System.out.println("kanucdcdcdcsss");
        return reviewService.getDrafts();
    }
}
