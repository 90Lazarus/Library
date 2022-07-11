package lazarus.restfulapi.library.model;

import lazarus.restfulapi.library.model.enumerated.Gender;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String penName;

    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;

    @Column(length = 100)
    private String nationality;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String occupation;

    @Column(length = 4096)
    private String shortBio;

    @Lob @Column(columnDefinition = "BLOB")
    private byte[] photo;

    private String wikipediaPageAddress;
    private String goodreadsPageAddress;
    private String authorWebsiteAddress;

    @ManyToMany(mappedBy = "author")
    private Set<Book> books;
}