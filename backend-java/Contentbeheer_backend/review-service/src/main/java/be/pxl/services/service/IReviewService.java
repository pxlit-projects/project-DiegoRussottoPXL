package be.pxl.services.service;

import be.pxl.services.domain.dto.DraftedPost;
import be.pxl.services.domain.dto.PostRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReviewService {
    // Methode om de posts met status 'DRAFT' op te halen
    ResponseEntity<List<DraftedPost>> getDrafts();
    ResponseEntity<Void> publishPost(Long postId);
    ResponseEntity<Void> rejectPost(Long postId, String rejectReason);
    ResponseEntity<List<DraftedPost>> getRejectedPosts();
    ResponseEntity<Void> resubmitPost(Long postId, PostRequest postRequest);



}
