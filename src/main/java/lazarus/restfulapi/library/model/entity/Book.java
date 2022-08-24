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

    @Builder.Default
    private boolean rented = false;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Rented> rentedTo;

    @Builder.Default
    private boolean adult = false;

    //information about the original version of the book
    private String titleOriginal;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany() @JoinTable(name = "book_language_original",
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

    @PreRemove
    public void deleteABook() {
        if (!(this.getAuthor().isEmpty())) {
            this.getAuthor().forEach(author -> author.setBooks(null));
        }
        if (!(this.getLanguage().isEmpty())) {
            this.getLanguage().forEach(language -> language.setBooks(null));
        }
        if (this.getPublisher() != null) {
            this.getPublisher().setBooks(null);
        }
        if (!(this.getGenre().isEmpty())) {
            this.getGenre().forEach(genre -> genre.setBooks(null));
        }
        if (this.getLibrary() != null) {
            this.getLibrary().setBooks(null);
        }
        if (!(this.getLanguageOriginal().isEmpty())) {
            this.getLanguageOriginal().forEach(languageOriginal -> languageOriginal.setBooks(null));
        }
        if (this.getPublisherOriginal() != null) {
            this.getPublisherOriginal().setBooks(null);
        }
    }
}