package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Language {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Long id;

    @NotNull(message = "Language name cannot be null!")
    @Column(unique = true)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "language")
    private List<Book> books;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "languageOriginal")
    private List<Book> booksOriginal;

    @PreRemove
    public void deleteALanguage() {
        if (!(this.getBooks().isEmpty())) {
            this.getBooks().forEach(book -> book.setLanguage(null));
        }
        if (!(this.getBooksOriginal().isEmpty())) {
            this.getBooksOriginal().forEach(book -> book.setLanguageOriginal(null));
        }
    }
}