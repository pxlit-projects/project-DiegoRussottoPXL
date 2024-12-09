package be.pxl.services.service;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;

import java.util.List;

public interface IPostService {
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByStatus(PostStatus status); // Nieuwe methode

    void addPost(PostRequest postRequest);
    void publishPost(Long id);
    void rejectPost(Long id, String reason);
    void updatePost(Long id, PostRequest postRequest);

    List<PostResponse> getFilteredPosts(String author, String content, String date);
}
