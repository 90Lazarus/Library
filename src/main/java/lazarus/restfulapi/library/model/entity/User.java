package lazarus.restfulapi.library.model.entity;

import lazarus.restfulapi.library.model.embeddable.Address;
import lazarus.restfulapi.library.model.enumerated.Gender;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
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

    @Column(nullable = false)
    @Builder.Default
    private boolean isAdmin = false;

    @OneToMany(mappedBy = "user")
    private Set<Rented> booksRented;
}