package aprendendo.api.blog;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import aprendendo.api.blog.auth.service.TokenService;
import aprendendo.api.blog.service.UserService;

@TestConfiguration
public class UserServiceConfiguration {
	@Bean
	public UserService userService() {
		return new UserService();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new AuthenticationManager() {
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return null;
			}
		};
	}

		@Bean
		public TokenService tokenService() {
			return new TokenService();
		}
	}