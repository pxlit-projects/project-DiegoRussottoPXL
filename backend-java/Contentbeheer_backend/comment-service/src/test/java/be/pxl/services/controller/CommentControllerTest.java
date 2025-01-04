package be.pxl.services.controller;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.services.ICommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ICommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private CommentRequest commentRequest;
    private CommentResponse commentResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        // Initialiseer de testdata
        commentRequest = new CommentRequest(1L, "Test Author", "Test comment", null);
        commentResponse = new CommentResponse(1L, 1L, "Test Author", "Test comment", null);
    }

    @Test
    void testGetComments() throws Exception {
        Long postId = 1L;
        List<CommentResponse> comments = List.of(commentResponse);

        // Mock de service om een lijst van comments te retourneren
        when(commentService.getCommentsByPostId(postId)).thenReturn(comments);

        // Voer de GET-aanroep uit en controleer de response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(commentResponse.getId()))
                .andExpect(jsonPath("$[0].author").value(commentResponse.getAuthor()))
                .andExpect(jsonPath("$[0].content").value(commentResponse.getContent()));

        verify(commentService).getCommentsByPostId(postId);
    }

    @Test
    void testAddComment() throws Exception {
        Long postId = 1L;

        // Mock de service om een comment toe te voegen
        when(commentService.addComment(eq(postId), any(CommentRequest.class))).thenReturn(commentResponse);

        // Voer de POST-aanroep uit en controleer de response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comments/{postId}", postId)
                        .contentType("application/json")
                        .content("{ \"author\": \"Test Author\", \"content\": \"Test comment\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(commentResponse.getId()))
                .andExpect(jsonPath("$.author").value(commentResponse.getAuthor()))
                .andExpect(jsonPath("$.content").value(commentResponse.getContent()));

        verify(commentService).addComment(eq(postId), any(CommentRequest.class));
    }

    @Test
    void testUpdateComment() throws Exception {
        Long commentId = 1L;

        // Voer de PUT-aanroep uit om een comment bij te werken
        mockMvc.perform(MockMvcRequestBuilders.put("/api/comments/{commentId}", commentId)
                        .contentType("application/json")
                        .content("{ \"author\": \"Updated Author\", \"content\": \"Updated comment\" }"))
                .andExpect(status().isOk());

        verify(commentService).updateComment(eq(commentId), any(CommentRequest.class));
    }

    @Test
    void testDeleteComment() throws Exception {
        Long commentId = 1L;

        // Voer de DELETE-aanroep uit om een comment te verwijderen
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comments/{commentId}", commentId))
                .andExpect(status().isNoContent());

        verify(commentService).deleteComment(commentId);
    }
}
