package lazarus.restfulapi.library.model.embed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PasswordResetToken {
    private String token;
    private Long expiration;
    private LocalDateTime expiryDateTime;
}