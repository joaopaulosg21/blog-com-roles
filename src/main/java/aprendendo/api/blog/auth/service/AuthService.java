package aprendendo.api.blog.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService{
    
    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            return (UserDetails) optionalUser.get();
        }
        throw new UsernameNotFoundException(email + " not found");
    }
}
