package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.enums.Gender;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Schema(name = "New User")
public class NewUserDTO {
    @Schema(description = "User's email", example = "slobodan@gmail.com")
    @NotNull(message = "User's email must not be null!")
    @NotEmpty(message = "User's email must not be an empty string!")
    @Email(message = "User's email must be a valid email in the form of user@gmail.com!")
    private String email;

    @Schema(description = "User's password", example = "*****")
    @NotNull(message = "User's password must not be null!")
    @NotEmpty(message = "User's password must not be an empty string!")
    private String password;

    @Schema(description = "String to match the string of the user's password")
    private String matchingPassword;

    @Schema(description = "User's first name", example = "Slobodan")
    @NotNull(message = "User's first name must not be null!")
    @NotEmpty(message = "User's first name must not be an empty string!")
    private String firstName;

    @Schema(description = "User's last name", example = "Mitrovic")
    @NotNull(message = "User's last name must not be null!")
    @NotEmpty(message = "User's last name must not be an empty string!")
    private String lastName;

    @Schema(description = "User's date of birth", example = "01 June 1990")
    private LocalDate dateOfBirth;

    @Schema(description = "User's gender", example = "MALE")
    private Gender gender;

    @Schema(description = "User's address")
    private Address address;
}