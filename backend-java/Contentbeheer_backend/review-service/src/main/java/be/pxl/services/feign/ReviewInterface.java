package be.pxl.services.feign;

import be.pxl.services.domain.dto.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "post-service")
public interface ReviewInterface {
    @GetMapping("/api/post/drafts")
    public ResponseEntity<List<PostResponse>> getDraftPosts();
}
