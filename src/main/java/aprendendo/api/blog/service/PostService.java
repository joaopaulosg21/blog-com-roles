package aprendendo.api.blog.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.entities.Post;
import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.entities.DTO.PostDTO;
import aprendendo.api.blog.entities.DTO.UpdatePostDTO;
import aprendendo.api.blog.exceptions.post.PostException;
import aprendendo.api.blog.repository.PostRepository;
import aprendendo.api.blog.repository.UserRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostDTO addPost(Post post,String username) {
        Optional<Post> optionalPost = postRepository.findByTitle(post.getTitle());
        if(optionalPost.isPresent()) {
            throw new PostException("Ja existe um post com esse title adicionado");
        }
        User user = userRepository.findByEmail(username).get();
        post.setDate(LocalDate.now());
        post.setUser(user);
        return postRepository.save(post).toDTO();
    }

    public PostDTO updatePost(UpdatePostDTO updatedPost,long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
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
        throw new PostException("Post com essa id não existe");
    }

    public PostDTO deletePost(long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postRepository.delete(post);
            return post.toDTO();
        }
        throw new PostException("Post com essa id não existe");
    }
}
