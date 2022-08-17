package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.enums.Gender;
import lazarus.restfulapi.library.model.enums.Role;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull(message = "User email cannot be null") @Email(message = "String must be a valid email")
    @Column(unique = true)
    private String email;

    @NotNull(message = "User password cannot be null")
    private String password;

    @NotNull(message = "User's first name cannot be null")
    private String firstName;

    @NotNull(message = "User's last name cannot be null")
    private String lastName;

    @Transient
    private String fullName;

    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.OTHER;

    @Embedded
    private Address address;

    //@Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role; //= Role.GUEST;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Rented> booksRented;
}