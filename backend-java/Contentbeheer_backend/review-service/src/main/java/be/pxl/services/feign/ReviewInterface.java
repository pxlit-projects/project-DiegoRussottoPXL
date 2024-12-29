package be.pxl.services.feign;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "post-service")
public interface ReviewInterface {
    @GetMapping("/api/post/drafts")
    ResponseEntity<List<PostResponse>> getDraftPosts();
    @PutMapping("/api/post/{id}/publish")
    ResponseEntity<Void> publishPost(@PathVariable Long id);
    @PutMapping("/api/post/{id}/reject")
    ResponseEntity<Void> rejectPost(@PathVariable Long id);//, String rejectReason);
    @GetMapping("/api/post/rejected")
    ResponseEntity<List<PostResponse>> getRejectedPosts();

    @PutMapping("/api/post/{id}/resubmit")
    ResponseEntity<Void> resubmitPost(@PathVariable Long id, @RequestBody PostRequest postRequest);
    @GetMapping("/api/post/{id}")
    ResponseEntity<PostResponse> getPostById(@PathVariable Long id);



}
