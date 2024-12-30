package be.pxl.services.services;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    @Override
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse addComment(Long postId, CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .postId(postId)
                .user(commentRequest.getAuthor())
                .content(commentRequest.getContent())
                .createdDate(LocalDate.now())
                .build();
        commentRepository.save(comment);
        log.info("Succesfully posted comment on post with id: {}", postId);
        return mapToCommentResponse(comment);
    }

    @Override
    public void updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.setContent(commentRequest.getContent());
        comment.setCreatedDate(LocalDate.now());
        commentRepository.save(comment);
        log.info("Succesfully updated comment with id: {}", commentId);
    }
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        log.info("Succesfully deleted comment with id: {}", commentId);
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .author(comment.getUser())
                .content(comment.getContent())
                .timestamp(comment.getCreatedDate())
                .build();
    }
}
