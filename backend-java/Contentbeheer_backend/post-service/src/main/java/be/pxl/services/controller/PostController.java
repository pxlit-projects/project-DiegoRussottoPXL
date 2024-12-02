package be.pxl.services.controller;

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
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        log.info("Fetching all posts");
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
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
    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        log.info("Updating post with ID: {}", id);
        postService.updatePost(id, postRequest);
    }
}
