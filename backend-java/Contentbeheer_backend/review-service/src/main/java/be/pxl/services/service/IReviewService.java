package be.pxl.services.service;

import be.pxl.services.domain.dto.DraftedPost;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReviewService {
    // Methode om de posts met status 'DRAFT' op te halen
    ResponseEntity<List<DraftedPost>> getDrafts();
    ResponseEntity<Void> publishPost(Long postId);
    ResponseEntity<Void> rejectPost(Long postId);

}
