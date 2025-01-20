package be.pxl.services.controller;

import be.pxl.services.PostServiceApplication;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPost;
import be.pxl.services.service.IReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@ContextConfiguration(classes = PostServiceApplication.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetDraftPosts() throws Exception {
        List<PostResponse> drafts = Arrays.asList(
                new PostResponse(1L, "Draft Title", "Draft Content", "Author", LocalDate.now(), PostStatus.DRAFT)
        );

        when(reviewService.getDrafts()).thenReturn(ResponseEntity.ok(drafts));

        mockMvc.perform(get("/api/review/drafts"))
                .andExpect(status().isOk());

        verify(reviewService).getDrafts();
    }

    @Test
    public void testPublishPost() throws Exception {
        Long postId = 1L;

        when(reviewService.publishPost(postId)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/api/review/post/{id}/publish", postId))
                .andExpect(status().isOk());

        verify(reviewService).publishPost(postId);
    }

//    @Test
//    public void testRejectPost() throws Exception {
//        Long postId = 1L;
//        String rejectReason = "Inappropriate content";
//
//        when(reviewService.rejectPost(postId, rejectReason)).thenReturn(ResponseEntity.ok().build());
//
//        mockMvc.perform(put("/api/review/post/{id}/reject", postId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(rejectReason))) // Zorg dat rejectReason correct wordt geserialiseerd
//                .andExpect(status().isOk());
//
//        verify(reviewService).rejectPost(postId, rejectReason);
//    }


    @Test
    public void testGetRejectedPosts() throws Exception {
        List<RejectedPost> rejectedPosts = Arrays.asList(
                new RejectedPost(1L, "Rejected Title", "Rejected Content", "Author", LocalDate.now(), PostStatus.PENDING, "Invalid content")
        );

        when(reviewService.getRejectedPosts()).thenReturn(ResponseEntity.ok(rejectedPosts));

        mockMvc.perform(get("/api/review/rejected"))
                .andExpect(status().isOk());

        verify(reviewService).getRejectedPosts();
    }

    @Test
    public void testResubmitPost() throws Exception {
        Long postId = 1L;
        PostRequest postRequest = new PostRequest(postId, "Updated Title", "Updated Content", "Author", LocalDate.now(), PostStatus.DRAFT);

        when(reviewService.resubmitPost(postId, postRequest)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/api/review/post/{id}/resubmit", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk());

        verify(reviewService).resubmitPost(postId, postRequest);
    }
}