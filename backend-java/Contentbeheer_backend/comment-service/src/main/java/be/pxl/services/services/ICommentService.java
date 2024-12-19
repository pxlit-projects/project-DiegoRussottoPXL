package be.pxl.services.services;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;

import java.util.List;

public interface ICommentService {
    List<CommentResponse> getCommentsByPostId(Long postId);
    CommentResponse addComment(CommentRequest commentRequest);
    void updateComment(Long commentId, CommentRequest commentRequest);
    void deleteComment(Long commentId);
}
