package spring.cardirectorymanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Authentication response containing JWT token")
public class AuthResponse {
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Type of the token", example = "Bearer")
    private String tokenType = "Bearer";

    // Custom constructor for specific initialization
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
