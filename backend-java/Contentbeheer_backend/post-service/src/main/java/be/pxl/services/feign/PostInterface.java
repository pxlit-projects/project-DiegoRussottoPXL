package be.pxl.services.feign;

import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "comment-service")
public interface PostInterface {
    @GetMapping("/api/comments/{postId}")
    ResponseEntity<List<CommentResponse>> getCommentsById(@PathVariable Long postId);
}
