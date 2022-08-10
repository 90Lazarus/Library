package lazarus.restfulapi.library.model.entity;

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

    @NotNull @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "language")
    private List<Book> books;

    @ManyToMany(mappedBy = "languageOriginal")
    private List<Book> booksOriginal;
}