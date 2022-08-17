package lazarus.restfulapi.library.model.dto;

import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.enums.Gender;
import lazarus.restfulapi.library.model.enums.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Address address;
    private Role role;
}