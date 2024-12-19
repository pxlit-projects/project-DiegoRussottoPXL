package be.pxl.services.services;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse addComment(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .postId(commentRequest.getPostId())
                .user(commentRequest.getAuthor())
                .content(commentRequest.getContent())
                .createdDate(LocalDate.now())
                .build();

        commentRepository.save(comment);
        return mapToCommentResponse(comment);
    }

    @Override
    public void updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.setContent(commentRequest.getContent());
        comment.setCreatedDate(LocalDate.now()); //misschien geupdate tijd idk
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
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
