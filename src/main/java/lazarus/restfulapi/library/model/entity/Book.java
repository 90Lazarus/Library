package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lazarus.restfulapi.library.model.enums.FormatType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Book {
    //book edition info
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @NotNull
    private String title;

    @ManyToMany @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> author;

    @ManyToMany @JoinTable(
            name = "book_language",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> language;

    @ManyToOne @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    private LocalDate publicationDate;

    @Lob @Column(columnDefinition = "BLOB")
    private byte[] cover;

    private Integer numberOfPages;

    @Enumerated(EnumType.STRING)
    private FormatType formatType;

    @ManyToMany() @JoinTable (
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genre;

    @Column(length = 8192)
    private String plot;

    private Integer isbn;

    @ManyToOne @JoinColumn(name = "library_id")
    private Library library;

    @OneToOne(mappedBy = "book")
    private Rented rentedTo;

    private boolean available;

    //original book edition info
    private String titleOriginal;

    @ManyToMany() @JoinTable(
            name = "book_language",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languageOriginal;

    @ManyToOne @JoinColumn(name = "publisher_original_id")
    private Publisher publisherOriginal;

    private LocalDate publicationDateOriginal;

    @Lob @Column(columnDefinition = "BLOB")
    private byte[] coverOriginal;

    private Integer numberOfPagesOriginal;
}