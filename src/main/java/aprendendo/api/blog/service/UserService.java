package aprendendo.api.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.auth.service.TokenService;
import aprendendo.api.blog.entities.Role;
import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.entities.DTO.LoginDTO;
import aprendendo.api.blog.entities.DTO.TokenDTO;
import aprendendo.api.blog.entities.DTO.UserDTO;
import aprendendo.api.blog.exceptions.user.UserException;
import aprendendo.api.blog.repository.RoleRepository;
import aprendendo.api.blog.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    public UserDTO createUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        
        if(optionalUser.isEmpty()) {
            Optional<Role> optionalRole = roleRepository.findByName("user");
            user.setRole(optionalRole.get());
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            return userRepository.save(user).toDTO();
        }

        throw new UserException("Email ja está em uso");

    }

    public TokenDTO login(LoginDTO login) {
        UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);
        String token = tokenService.generate(authentication);
        return TokenDTO.builder().type("Bearer").token(token).build();
    }
}
