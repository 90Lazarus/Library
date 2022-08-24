package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @NotNull(message = "Full name of the author cannot be null!")
    private String fullName;

    private String penName;

    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;

    @Column(length = 100)
    private String nationality;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.OTHER;

    private String occupation;

    @Column(length = 4096)
    private String shortBio;

    @Lob @Column(columnDefinition = "BLOB")
    private byte[] photo;

    private String wikipediaPageAddress;
    private String goodreadsPageAddress;
    private String authorWebsiteAddress;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "author")
    private List<Book> books;

    @PreRemove
    public void deleteAnAuthor() {
        if (!(this.getBooks().isEmpty())) {
            this.getBooks().forEach(book -> book.setAuthor(null));
        }
    }
}