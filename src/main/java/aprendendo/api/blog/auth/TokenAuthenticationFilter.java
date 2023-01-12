package aprendendo.api.blog.auth;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import aprendendo.api.blog.auth.service.TokenService;
import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;


    public void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) 
    throws IOException,ServletException{
        String token = getToken(req);
        if(tokenService.isValid(token)) {
            authenticate(token);
        }
        doFilter(req, res, chain);
    }

    public String getToken(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.split(" ")[1];
    }

    public void authenticate(String token) {
        long id = tokenService.getIdFromToken(token);
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordToken);
        }
    }
}
