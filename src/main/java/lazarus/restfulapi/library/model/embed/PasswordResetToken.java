package lazarus.restfulapi.library.model.embed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Schema(name = "Password Reset Token")
public class PasswordResetToken {
    @Schema(description = "Token string")
    private String token;

    @Schema(description = "How long the token will be valid")
    private Long expiration;

    @Schema(description = "Token's expiration date and time")
    private LocalDateTime expiryDateTime;
}