package aprendendo.api.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import aprendendo.api.blog.auth.TokenAuthenticationFilter;
import aprendendo.api.blog.auth.service.AuthService;
import aprendendo.api.blog.auth.service.TokenService;
import aprendendo.api.blog.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        .antMatchers(HttpMethod.POST,"/user/create").permitAll()
        .antMatchers(HttpMethod.POST,"/user/login").permitAll()
        .antMatchers(HttpMethod.GET,"/user").hasAuthority("admin")
        .antMatchers(HttpMethod.DELETE,"/post/delete").hasAuthority("admin")
        .antMatchers(HttpMethod.PUT,"/post/update").hasAnyAuthority("editor","admin")
        .anyRequest().authenticated()
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(new TokenAuthenticationFilter(userRepository, tokenService),UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
