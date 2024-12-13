package spring.cardirectorymanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Register request containing username, password, and roles")
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "Username for the new user", example = "Amanzhol")
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    @Schema(description = "Password for the new user", example = "P@ssw0rd")
    private String password;

    @Schema(description = "Roles assigned to the new user", example = "[\"USER\", \"ADMIN\"]")
    private Set<String> roles;
}
