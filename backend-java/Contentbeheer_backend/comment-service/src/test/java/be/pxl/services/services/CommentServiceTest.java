package be.pxl.services.services;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCommentsByPostId_shouldReturnListOfCommentResponses() {
        // Arrange
        Long postId = 1L;
        List<Comment> comments = Arrays.asList(
                new Comment(1L, postId, "User1", "Content1", LocalDate.now()),
                new Comment(2L, postId, "User2", "Content2", LocalDate.now())
        );
        when(commentRepository.findByPostId(postId)).thenReturn(comments);

        // Act
        List<CommentResponse> result = commentService.getCommentsByPostId(postId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("User1", result.get(0).getAuthor());
        assertEquals("Content1", result.get(0).getContent());
        assertEquals("User2", result.get(1).getAuthor());
        assertEquals("Content2", result.get(1).getContent());
        verify(commentRepository, times(1)).findByPostId(postId);
    }

//    @Test
//    void addComment_shouldSaveCommentAndReturnCommentResponse() {
//        // Arrange
//        Long postId = 1L;
//        CommentRequest commentRequest = new CommentRequest(postId, "User1", "New Comment", null);
//        Comment savedComment = new Comment(1L, postId, "User1", "New Comment", LocalDate.now());
//        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);
//
//        // Act
//        CommentResponse result = commentService.addComment(postId, commentRequest);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals(postId, result.getPostId());
//        assertEquals("User1", result.getAuthor());
//        assertEquals("New Comment", result.getContent());
//        assertNotNull(result.getTimestamp());
//        verify(commentRepository, times(1)).save(any(Comment.class));
//    }

    @Test
    void updateComment_shouldUpdateExistingComment() {
        // Arrange
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest(commentId, "User1", "New Comment", null);
        Comment existingComment = new Comment(commentId, 1L, "User1", "Original Comment", LocalDate.now());
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));

        // Act
        commentService.updateComment(commentId, commentRequest);

        // Assert
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(existingComment);
        assertEquals("New Comment", existingComment.getContent());
        assertEquals(LocalDate.now(), existingComment.getCreatedDate());
    }

    @Test
    void updateComment_shouldThrowExceptionWhenCommentNotFound() {
        // Arrange
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest(commentId, "User1", "New Comment", null);
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(commentId, commentRequest));
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void deleteComment_shouldDeleteExistingComment() {
        // Arrange
        Long commentId = 1L;

        // Act
        commentService.deleteComment(commentId);

        // Assert
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}

