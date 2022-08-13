package lazarus.restfulapi.library.model.entity;

import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.enums.Gender;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Transient
    private String fullName;

    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Builder.Default
    private boolean isAdmin = false;

    @OneToMany(mappedBy = "user")
    private Set<Rented> booksRented;
}