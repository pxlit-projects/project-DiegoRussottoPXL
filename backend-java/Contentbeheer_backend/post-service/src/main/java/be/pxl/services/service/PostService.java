package be.pxl.services.service;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService{
    private final PostRepository postRepository;
    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToPostResponse(post)).toList();
    }

    private PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .date(post.getDate())
                .build();
    }

    @Override
    public void addPost(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .date(postRequest.getDate())
                .build();
        postRepository.save(post);
    }
}
