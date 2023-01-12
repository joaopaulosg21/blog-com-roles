package aprendendo.api.blog.utils;

import org.springframework.http.HttpHeaders;

public class UserTokenUtils {

    public static String getTokenFromHeader(HttpHeaders headers) {
        String token = headers.getFirst("Authorization").split(" ")[1];
        return token;
    }
}
