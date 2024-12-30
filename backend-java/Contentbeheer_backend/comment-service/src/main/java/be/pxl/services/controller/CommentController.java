package be.pxl.services.controller;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final ICommentService commentService;
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        log.info("Fetching comments for post with id: {}", postId);
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        log.info("Adding comment for post with id: {}", postId);
        return commentService.addComment(postId, commentRequest);
    }

    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        log.info("Updating comment with id: {}", commentId);
        commentService.updateComment(commentId, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        log.info("Deleting comment with id: {}", commentId);
        commentService.deleteComment(commentId);
    }
}
