package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Genre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;

    @NotNull(message = "Genre name cannot be null!")
    @Column(unique = true)
    private String name;

    @Column(length = 2048)
    private String description;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "genre")
    private List<Book> books;

    @PreRemove
    public void deleteAGenre() {
        if (!(this.getBooks().isEmpty())) {
            this.getBooks().forEach(book -> book.setGenre(null));
        }
    }
}