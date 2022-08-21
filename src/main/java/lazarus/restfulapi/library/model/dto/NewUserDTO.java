package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.enums.Gender;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Schema(name = "NewUser")
public class NewUserDTO {
    @NotNull(message = "User's email must not be null!") @NotEmpty(message = "User's email must not be an empty string!")
    @Schema(description = "User's email", example = "slobodan@gmail.com")
    private String email;

    @NotNull(message = "User's password must not be null!") @NotEmpty(message = "User's password must not be an empty string!")
    @Schema(description = "User's password", example = "*****")
    private String password;

    @Schema(description = "String to match the string of the password")
    private String matchingPassword;

    @NotNull(message = "User's first name must not be null!") @NotEmpty(message = "User's first name must not be an empty string!")
    @Schema(description = "User's first name", example = "Slobodan")
    private String firstName;

    @NotNull(message = "User's last name must not be null!") @NotEmpty(message = "User's last name must not be an empty string!")
    @Schema(description = "User's last name", example = "Mitrovic")
    private String lastName;

    @Schema(description = "User's date of birth", example = "01 June 1990")
    private LocalDate dateOfBirth;

    @Schema(description = "User's gender", example = "MALE")
    private Gender gender;

    @Schema(description = "User's address")
    private Address address;
}