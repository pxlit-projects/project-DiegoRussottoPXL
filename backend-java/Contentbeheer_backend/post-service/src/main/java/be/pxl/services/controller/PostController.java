package be.pxl.services.controller;

import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    private final IPostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String date) {
        log.info("Fetching posts with filters: author = {}, content = {}, date = {}", author, content, date);
        return new ResponseEntity<>(postService.getFilteredPosts(author, content, date), HttpStatus.OK);
    }


    @GetMapping("/drafts")
    public ResponseEntity<List<PostResponse>> getDraftPosts() {
        log.info("Fetching posts with status DRAFT");
        return new ResponseEntity<>(postService.getPostsByStatus(PostStatus.DRAFT), HttpStatus.OK);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<PostResponse>> getRejectedPosts() {
        log.info("Fetching posts with status REJECTED");
        return new ResponseEntity<>(postService.getPostsByStatus(PostStatus.PENDING), HttpStatus.OK);
    }
    @PutMapping("/{id}/resubmit")
    public void resubmitPost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        log.info("Resubmitting post with ID: {} and new title: {}, content: {}", id, postRequest.getTitle(), postRequest.getContent());
        postService.resubmitPost(id, postRequest);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPost(@RequestBody PostRequest postRequest) {
        log.info("Creating new post with title: {}", postRequest.getTitle());
        postService.addPost(postRequest);
    }
    @PutMapping("/{id}/publish")
    public void publishPost(@PathVariable Long id) {
        log.info("Publishing post with ID: {}", id);
        postService.publishPost(id);
    }
    @PutMapping("/{id}/reject")
    public void rejectPost(@PathVariable Long id, @RequestBody String rejectReason){
        log.info("Rejecting post with id" + id);
        postService.rejectPost(id, rejectReason);
    }
    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        log.info("Updating post with ID: {}", id);
        postService.updatePost(id, postRequest);
    }
}
