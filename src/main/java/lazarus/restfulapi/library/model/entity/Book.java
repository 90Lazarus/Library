package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.*;
import lazarus.restfulapi.library.model.enums.FormatType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Book {
    //information about this particular version of the book
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @NotNull(message = "Book title cannot be empty")
    private String title;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> author;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany @JoinTable(name = "book_language",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> language;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    private LocalDate publicationDate;

    @Lob @Column(columnDefinition = "BLOB")
    private byte[] cover;

    private Integer numberOfPages;

    @Enumerated(EnumType.STRING)
    private FormatType formatType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany() @JoinTable (name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genre;

    @Column(length = 8192)
    private String plot;

    private Integer isbn;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne @JoinColumn(name = "library_id")
    private Library library;

    @Transient
    private boolean rented = false;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "book")
    private Rented rentedTo;

    private boolean adult;

    //information of the original version of the book
    private String titleOriginal;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany() @JoinTable(name = "book_language",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> languageOriginal;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne @JoinColumn(name = "publisher_original_id")
    private Publisher publisherOriginal;

    private LocalDate publicationDateOriginal;

    @Lob @Column(columnDefinition = "BLOB")
    private byte[] coverOriginal;

    private Integer numberOfPagesOriginal;
}