package aprendendo.api.blog.exceptions.post;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostException extends RuntimeException{
    private String msg;

    public Map<String,String> getError() {
        Map<String,String> error = new HashMap<>();
        error.put("ERROR",msg);
        return error;
    }
}
