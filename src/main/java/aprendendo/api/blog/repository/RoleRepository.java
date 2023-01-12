package aprendendo.api.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import aprendendo.api.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
    Optional<Role> findByName(String name);
}
