package spring.cardirectorymanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request containing username and password")
public class LoginRequest {
    @NotBlank
    @Schema(description = "Username of the user", example = "Amanzhol")
    private String username;

    @NotBlank
    @Schema(description = "Password of the user", example = "P@ssw0rd")
    private String password;
}
