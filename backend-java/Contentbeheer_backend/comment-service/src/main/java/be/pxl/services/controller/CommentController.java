package be.pxl.services.controller;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(@RequestBody CommentRequest commentRequest) {
        return commentService.addComment(commentRequest);
    }

    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(commentId, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
