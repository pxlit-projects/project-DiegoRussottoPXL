package be.pxl.services.service;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.NotificationRequest;
import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private final NotificationClient notificationClient;

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getFilteredPosts(String author, String content, String date) {
        List<Post> posts = postRepository.findAll();

        if (author != null) {
            posts = posts.stream()
                    .filter(post -> post.getAuthor() != null && post.getAuthor().contains(author)) // Check for null
                    .collect(Collectors.toList());
        }

        if (content != null) {
            posts = posts.stream()
                    .filter(post -> post.getContent() != null && post.getContent().contains(content)) // Check for null
                    .collect(Collectors.toList());
        }

        if (date != null) {
            LocalDate filterDate = LocalDate.parse(date);
            posts = posts.stream()
                    .filter(post -> post.getDate() != null && post.getDate().equals(filterDate)) // Check for null
                    .collect(Collectors.toList());
        }

        return posts.stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }




    private PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .date(post.getDate())
                .status(post.getStatus())
                .build();
    }

    @Override
    public void addPost(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .date(LocalDate.now())
                .status(postRequest.getStatus() != null ? postRequest.getStatus() : PostStatus.DRAFT) // Standaard naar concept
                .build();
        postRepository.save(post);

        // Uncomment als notificaties nodig zijn
        /*
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("Post created")
                .sender(post.getAuthor())
                .build();
        notificationClient.sendNotification(notificationRequest);
        */
    }
    public void publishPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        post.setStatus(PostStatus.PUBLISHED);  // Verander de status naar PUBLISHED
        postRepository.save(post);
    }
    public void updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());
        post.setStatus(postRequest.getStatus() != null ? postRequest.getStatus() : post.getStatus());

        postRepository.save(post);
    }

}
