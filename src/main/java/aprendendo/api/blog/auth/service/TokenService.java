package aprendendo.api.blog.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
    
    @Value("${jwt.exp}")
    private String exp;
    
    @Value("${jwt.secret}")
    private String secret;


    public String generate(Authentication authentication) {
        Long id = ((User) authentication.getPrincipal()).getId();
        Long now = new Date().getTime();
        return Jwts.builder()
        .setSubject(id.toString())
        .setExpiration(new Date(now + Long.valueOf(exp)))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
    }

    public boolean isValid(String token) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch(Exception exc) {
            return false;
        }
    }

    public long getIdFromToken(String token) {
        long id = Long.valueOf(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
        return id;
    }

}
