package be.pxl.services.services;

import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
}
