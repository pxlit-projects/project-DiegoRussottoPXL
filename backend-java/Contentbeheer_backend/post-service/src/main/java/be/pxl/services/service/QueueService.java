package be.pxl.services.service;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.exception.PostNotFoundException;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {
    private final PostRepository postRepository;
    private final EmailService emailService;

    @RabbitListener(queues = "postApprovalQueue")
    public void handlePostApproval(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("No post found with ID: " + postId));
        post.setStatus(PostStatus.PUBLISHED);
        postRepository.save(post);
        emailService.sendEmail(
                "Blog Approved: " + post.getTitle(),
                "Congratulations! Your blog titled \"" + post.getTitle() + "\" has been approved and is ready for publication."
        );
    }

    @RabbitListener(queues = "postRejectionQueue")
    public void handlePostRejection(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("No post found with ID: " + postId));
        post.setStatus(PostStatus.PENDING);
        postRepository.save(post);
        emailService.sendEmail(
                "Blog Rejected: " + post.getTitle(),
                "Unfortunately, your blog titled \"" + post.getTitle() + "\" has been rejected. Please revise it before resubmitting."
        );
    }
}

