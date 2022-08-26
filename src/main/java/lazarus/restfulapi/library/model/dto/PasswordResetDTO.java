package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Schema(name = "Password Reset")
public class PasswordResetDTO {
    @Schema(description = "User's password", example = "*****")
    @NotNull(message = "User's password must not be null!")
    @NotEmpty(message = "User's password must not be an empty string!")
    @Size(min = 6, message = "User's password should be at least 6 characters long")
    private String password;

    @Schema(description = "String to match the string of the user's password")
    private String matchingPassword;
}
