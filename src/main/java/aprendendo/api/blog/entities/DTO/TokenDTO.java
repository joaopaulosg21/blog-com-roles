package aprendendo.api.blog.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class TokenDTO {
    private String type;
    private String token;
}
