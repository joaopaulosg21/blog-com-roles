package aprendendo.api.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aprendendo.api.blog.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
    Optional<Post> findByTitle(String title);
}
