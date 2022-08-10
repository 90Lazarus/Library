package lazarus.restfulapi.library.model.entity;

import lazarus.restfulapi.library.model.enums.Gender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @NotNull
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

    @ManyToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;
}