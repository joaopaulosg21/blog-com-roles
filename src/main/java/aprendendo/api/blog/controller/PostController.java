package aprendendo.api.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aprendendo.api.blog.entities.Post;
import aprendendo.api.blog.entities.DTO.PostDTO;
import aprendendo.api.blog.entities.DTO.UpdatePostDTO;
import aprendendo.api.blog.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/add")
    public ResponseEntity<PostDTO> addNewPost(@Valid @RequestBody Post post,@AuthenticationPrincipal String username) {
        return ResponseEntity.ok(postService.addPost(post, username));
    }

    @PutMapping("/update")
    public ResponseEntity<PostDTO> updatePost(@RequestBody UpdatePostDTO updatePost,@RequestParam(required = true,name = "id") long id) {
        return ResponseEntity.ok(postService.updatePost(updatePost, id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PostDTO> deletePost(@RequestParam long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
}
