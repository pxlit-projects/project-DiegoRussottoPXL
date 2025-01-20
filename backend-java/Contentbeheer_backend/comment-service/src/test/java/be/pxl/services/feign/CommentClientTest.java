package be.pxl.services.feign;

import be.pxl.services.domain.dto.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CommentClientTest {

    @Mock
    private CommentClient commentClient;

    @InjectMocks
    private CommentClientTest testClient;

    private List<CommentResponse> mockComments;

    @BeforeEach
    void setUp() {
        // Setup mock data
        mockComments = List.of(
                new CommentResponse(1L, 1L, "Test Author 1", "Test content 1", null),
                new CommentResponse(2L, 1L, "Test Author 2", "Test content 2", null)
        );
    }

//    @Test
//    void testGetCommentsByPostId() {
//        Long postId = 1L;
//
//        // Mock de service om een lijst van comments terug te geven
//        when(commentClient.getCommentsByPostId(postId)).thenReturn(mockComments);
//
//        // Test de methode
//        List<CommentResponse> comments = commentClient.getCommentsByPostId(postId);
//
//        // Controleer de size van de response
//        assert comments.size() == 2;
//        assert comments.get(0).getAuthor().equals("Test Author 1");
//        assert comments.get(1).getContent().equals("Test content 2");
//
//        verify(commentClient).getCommentsByPostId(postId);  // Controleer of de juiste methode is aangeroepen
//    }
}
