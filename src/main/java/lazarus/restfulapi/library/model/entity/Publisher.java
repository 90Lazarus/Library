package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lazarus.restfulapi.library.model.embed.Address;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.List;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Publisher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @NotNull(message = "Publisher name cannot be null!")
    @Column(unique = true)
    private String name;

    private Year yearFounded;

    @Embedded
    private Address address;

    @Column(length = 4096)
    private String info;

    private String website;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "publisherOriginal")
    private List<Book> booksOriginal;

    @PreRemove
    public void deleteAPublisher() {
        if (!(this.getBooks().isEmpty())) {
            this.getBooks().forEach(book -> book.setPublisher(null));
        }
        if (!(this.getBooksOriginal().isEmpty())) {
            this.getBooksOriginal().forEach(book -> book.setPublisherOriginal(null));
        }
    }
}