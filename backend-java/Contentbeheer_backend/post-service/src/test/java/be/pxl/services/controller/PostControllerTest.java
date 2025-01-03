package be.pxl.services.controller;

import be.pxl.services.PostServiceApplication;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@ContextConfiguration(classes = PostServiceApplication.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetPostById() throws Exception {
        Long postId = 1L;
        PostResponse postResponse = new PostResponse(postId, "Test Title", "Test Content", "Author", LocalDate.now(), PostStatus.PUBLISHED);

        when(postService.getPostById(postId)).thenReturn(postResponse);

        mockMvc.perform(get("/api/post/{id}", postId))
                .andExpect(status().isOk());

        verify(postService).getPostById(postId);
    }

    @Test
    public void testGetAllPosts() throws Exception {
        List<PostResponse> posts = Arrays.asList(
                new PostResponse(1L, "Title 1", "Content 1", "Author 1", LocalDate.now(), PostStatus.PUBLISHED),
                new PostResponse(2L, "Title 2", "Content 2", "Author 2", LocalDate.now(), PostStatus.PUBLISHED)
        );

        when(postService.getFilteredPosts(null, null, null)).thenReturn(posts);

        mockMvc.perform(get("/api/post"))
                .andExpect(status().isOk());

        verify(postService).getFilteredPosts(null, null, null);
    }

    @Test
    public void testGetCommentsById() throws Exception {
        Long postId = 1L;
        List<CommentResponse> comments = Arrays.asList(
                new CommentResponse(1L, postId, "Author 1", "Comment 1", LocalDate.now())
        );

        when(postService.getCommentsById(postId)).thenReturn(comments);

        mockMvc.perform(get("/api/post/{id}/comments", postId))
                .andExpect(status().isOk());

        verify(postService).getCommentsById(postId);
    }

    @Test
    public void testGetDraftPosts() throws Exception {
        List<PostResponse> drafts = Arrays.asList(
                new PostResponse(1L, "Draft Title", "Draft Content", "Author", LocalDate.now(), PostStatus.DRAFT)
        );

        when(postService.getPostsByStatus(PostStatus.DRAFT)).thenReturn(drafts);

        mockMvc.perform(get("/api/post/drafts"))
                .andExpect(status().isOk());

        verify(postService).getPostsByStatus(PostStatus.DRAFT);
    }

    @Test
    public void testAddPost() throws Exception {
        PostRequest postRequest = new PostRequest(null, "New Post", "Content", "Author", LocalDate.now(), PostStatus.DRAFT);

        mockMvc.perform(post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated());

        verify(postService).addPost(postRequest);
    }

    @Test
    public void testPublishPost() throws Exception {
        Long postId = 1L;

        mockMvc.perform(put("/api/post/{id}/publish", postId))
                .andExpect(status().isOk());

        verify(postService).publishPost(postId);
    }

    @Test
    public void testRejectPost() throws Exception {
        Long postId = 1L;

        mockMvc.perform(put("/api/post/{id}/reject", postId))
                .andExpect(status().isOk());

        verify(postService).rejectPost(postId);
    }

    @Test
    public void testResubmitPost() throws Exception {
        Long postId = 1L;
        PostRequest postRequest = new PostRequest(postId, "Updated Title", "Updated Content", "Author", LocalDate.now(), PostStatus.DRAFT);

        mockMvc.perform(put("/api/post/{id}/resubmit", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk());

        verify(postService).resubmitPost(postId, postRequest);
    }

    @Test
    public void testUpdatePost() throws Exception {
        Long postId = 1L;
        PostRequest postRequest = new PostRequest(postId, "Updated Title", "Updated Content", "Author", LocalDate.now(), PostStatus.PUBLISHED);

        mockMvc.perform(put("/api/post/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk());

        verify(postService).updatePost(postId, postRequest);
    }
}
