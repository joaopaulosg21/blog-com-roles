package aprendendo.api.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aprendendo.api.blog.entities.Post;
import aprendendo.api.blog.entities.DTO.PostDTO;
import aprendendo.api.blog.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/add")
    public ResponseEntity<PostDTO> addNewPost(@Valid @RequestBody Post post,@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(postService.addPost(post, headers));
    }
}
