package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.enums.Gender;
import lazarus.restfulapi.library.model.enums.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "User")
public class UserDTO {
    @Schema(description = "User identifier", example = "1")
    private Long id;

    @Schema(description = "User email", example = "slobodan@gmail.com")
    private String email;

    @Schema(description = "User password", example = "*****")
    private String password;

    @Schema(description = "User's first name", example = "Slobodan")
    private String firstName;

    @Schema(description = "User's last name", example = "Mitrovic")
    private String lastName;

    @Schema(description = "User's date of birth", example = "01 June 1990")
    private LocalDate dateOfBirth;

    @Schema(description = "User's gender", example = "MALE")
    private Gender gender;

    @Schema(description = "User's address")
    private Address address;

    @Schema(description = "User's role", example = "USER")
    private Role role;
}