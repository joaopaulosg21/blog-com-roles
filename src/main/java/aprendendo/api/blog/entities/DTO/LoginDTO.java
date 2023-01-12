package aprendendo.api.blog.entities.DTO;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginDTO {
    @NotBlank(message = "EMAIL não pode ser null")
    private String email;

    @NotBlank(message = "PASSWORD não pode ser null")
    private String password;
}
