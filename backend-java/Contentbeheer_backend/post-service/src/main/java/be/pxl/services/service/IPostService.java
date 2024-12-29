package be.pxl.services.service;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;

import java.util.List;

public interface IPostService {
    List<CommentResponse> getCommentsById(Long id);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByStatus(PostStatus status);

    void addPost(PostRequest postRequest);
    void publishPost(Long id);
    void rejectPost(Long id);
    void updatePost(Long id, PostRequest postRequest);
    void resubmitPost(Long id, PostRequest postRequest);
    PostResponse getPostById(Long id);

    List<PostResponse> getFilteredPosts(String author, String content, String date);
}
