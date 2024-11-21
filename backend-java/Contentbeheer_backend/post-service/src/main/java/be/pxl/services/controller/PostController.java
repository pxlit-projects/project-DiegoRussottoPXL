package be.pxl.services.controller;

import be.pxl.services.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;

    @GetMapping
    public ResponseEntity getAllPosts(){
        return new ResponseEntity(postService.getAllPosts(), HttpStatus.OK);
    }
}
