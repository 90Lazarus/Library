package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.embed.PasswordResetToken;
import lazarus.restfulapi.library.model.enums.Gender;
import lazarus.restfulapi.library.model.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull(message = "User email cannot be null!")
    @NotEmpty(message = "User email must not be an empty string!")
    @Email(message = "String must be a valid email in the form of user@gmail.com!")
    @Column(unique = true)
    private String email;

    @NotNull(message = "User password cannot be null!")
    @Length(min = 3, message = "User password must be at least 3 characters long!")
    private String password;

    @NotNull(message = "User's first name cannot be null!")
    @NotEmpty(message = "User's first name must not be an empty string!")
    private String firstName;

    @NotNull(message = "User's last name cannot be null!")
    @NotEmpty(message = "User's last name must not be an empty string!")
    private String lastName;

    @Transient
    private String fullName;

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    @NotNull(message = "User's date of birth cannot be null!")
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    public Integer getAge() {
        return Period.between(this.getDateOfBirth(), LocalDate.now()).getYears();
    }

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.OTHER;

    @Embedded
    private Address address;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Rented> booksRented;

    @Embedded
    private PasswordResetToken passwordResetToken;
}