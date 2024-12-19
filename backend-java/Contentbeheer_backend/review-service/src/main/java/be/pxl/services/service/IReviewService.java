package be.pxl.services.service;

import be.pxl.services.domain.dto.DraftedPost;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReviewService {
    ResponseEntity<List<PostResponse>> getDrafts();
    ResponseEntity<Void> publishPost(Long postId);
    ResponseEntity<Void> rejectPost(Long postId, String rejectReason);
    ResponseEntity<List<PostRequest>> getRejectedPosts();
    ResponseEntity<Void> resubmitPost(Long postId, PostRequest postRequest);



}
