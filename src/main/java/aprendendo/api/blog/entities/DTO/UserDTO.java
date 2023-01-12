package aprendendo.api.blog.entities.DTO;

import aprendendo.api.blog.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class UserDTO {
    private String name;
    private String email;
    private Role role;
}
