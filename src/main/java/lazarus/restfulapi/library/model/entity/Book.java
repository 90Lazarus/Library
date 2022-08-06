package lazarus.restfulapi.library.model.entity;

import lazarus.restfulapi.library.model.enums.FormatType;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany() @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Column(nullable = false)
    private Set<Author> author;

    @OneToMany(mappedBy = "book")
    @Column(nullable = false)
    private Set<Language> language;

    @ManyToOne @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    private LocalDate publicationDate;

    @Lob @Column(columnDefinition = "BLOB")
    private byte[] cover;

    private Integer numberOfPages;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private FormatType formatType;

    @OneToMany(mappedBy = "book")
    private Set<Genre> genre;

    @Column(length = 8192)
    private String plot;

    private Integer isbn;

    @ManyToOne @JoinColumn(name = "library_id")
    private Library library;

    @OneToOne(mappedBy = "book")
    private Rented rentedTo;

    //original book version info
    private String titleOriginal;

    @OneToMany(mappedBy = "bookOriginal")
    private Set<Language> languageOriginal;

    @ManyToOne @JoinColumn(name = "publisher_original_id")
    private Publisher publisherOriginal;

    private LocalDate publicationDateOriginal;

    @Lob @Column(columnDefinition = "BLOB")
    private byte[] coverOriginal;

    private Integer numberOfPagesOriginal;
}