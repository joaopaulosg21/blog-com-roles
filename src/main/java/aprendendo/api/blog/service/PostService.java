package aprendendo.api.blog.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.auth.service.TokenService;
import aprendendo.api.blog.entities.Post;
import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.entities.DTO.PostDTO;
import aprendendo.api.blog.entities.DTO.UpdatePostDTO;
import aprendendo.api.blog.exceptions.post.PostException;
import aprendendo.api.blog.exceptions.user.UserException;
import aprendendo.api.blog.repository.PostRepository;
import aprendendo.api.blog.repository.UserRepository;
import aprendendo.api.blog.utils.UserTokenUtils;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public PostDTO addPost(Post post,HttpHeaders headers) {
        Optional<Post> optionalPost = postRepository.findByTitle(post.getTitle());
        if(optionalPost.isPresent()) {
            throw new PostException("Ja existe um post com esse title adicionado");
        }
        String token = UserTokenUtils.getTokenFromHeader(headers);
        long id = tokenService.getIdFromToken(token);
        User user = userRepository.findById(id).get();
        if(user.getRole().getAuthority().equals("editor") || user.getRole().getAuthority().equals("admin")) {
            post.setDate(LocalDate.now());
            post.setUser(user);
            return postRepository.save(post).toDTO();
        }
        
        throw new UserException("User não tem autoridade para adicionar posts");
    }

    public PostDTO updatePost(UpdatePostDTO updatedPost,long postId, HttpHeaders headers) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            String token = UserTokenUtils.getTokenFromHeader(headers);
            long id = tokenService.getIdFromToken(token);
            User user = userRepository.findById(id).get();
            if(user.getRole().getAuthority().equals("editor") || user.getRole().getAuthority().equals("admin")) {
                if(updatedPost.getTitle() != null || updatedPost.getContent() != null) {
                    if(updatedPost.getTitle() != null) {
                        post.setTitle(updatedPost.getTitle());
                    }
                    if(updatedPost.getContent() != null) {
                        post.setContent(updatedPost.getContent());
                    }
                    post.setUpdated(LocalDate.now());
                }
                return postRepository.save(post).toDTO();
            }
            throw new UserException("User não tem autoridade para editar posts");
        }
        throw new PostException("Post com essa id não existe");
    }
}
