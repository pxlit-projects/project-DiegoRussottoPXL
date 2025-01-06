package be.pxl.services.service;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.feign.PostInterface;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    //private final NotificationClient notificationClient;

    @Autowired
    PostInterface postInterface;

    public List<CommentResponse> getCommentsById(@PathVariable Long postId){
        List<CommentResponse> comments = postInterface.getCommentsById(postId).getBody();
        log.info("Retrieved {} comments for post ID: {}", comments.size(), postId);
        return comments;
    }

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        log.info("Retrieved {} posts", posts.size());
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
        log.info("Filtered posts count: {}", posts.size());
        return posts.stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostsByStatus(PostStatus status) {
        return postRepository.findByStatus(status)
                .stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .date(post.getDate())
                        .status(post.getStatus())
                        //.rejectionReason(post.getRejectionReason())
                        .build())
                .toList();
    }
    @Override
    public void resubmitPost(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with ID " + id + " not found"));
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setDate(LocalDate.now());
        post.setStatus(PostStatus.DRAFT);
        postRepository.save(post);
        log.info("Post with ID: {} resubmitted successfully", id);
    }
    private PostResponse mapToPostResponse(Post post) {
        log.debug("Mapping post ID: {} to PostResponse", post.getId());
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .date(post.getDate())
                .status(post.getStatus())
                //.rejectionReason(post.getRejectionReason())
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
        log.info("Post added successfully with title: {}", post.getTitle());


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
        post.setStatus(PostStatus.PUBLISHED);
        postRepository.save(post);
        log.info("Post with ID: {} published successfully", postId);
    }
    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        log.info("Post with ID: {} retrieved successfully", id);
        return mapToPostResponse(post);
    }


    public void rejectPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        post.setStatus(PostStatus.PENDING);
        postRepository.save(post);
        log.info("Post with ID: {} rejected successfully", postId);
    }

    public void updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());
        post.setStatus(PostStatus.DRAFT);
        postRepository.save(post);
        log.info("Post with ID: {} updated successfully", postId);
    }
}
