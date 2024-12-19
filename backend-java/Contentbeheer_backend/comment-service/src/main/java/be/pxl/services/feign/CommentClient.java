package be.pxl.services.feign;

import be.pxl.services.domain.dto.CommentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "comment-service")
public interface CommentClient {
    @GetMapping("/post/{postId}")
    List<CommentResponse> getCommentsByPostId(@PathVariable Long postId);
}
