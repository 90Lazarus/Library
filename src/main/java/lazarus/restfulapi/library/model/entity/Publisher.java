package lazarus.restfulapi.library.model.entity;

import lazarus.restfulapi.library.model.embeddable.Address;
import lazarus.restfulapi.library.model.entity.Book;
import lombok.*;
import javax.persistence.*;
import java.time.Year;
import java.util.Set;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Publisher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private Year yearFounded;

    @Embedded
    private Address address;

    @Column(length = 4096)
    private String info;

    private String website;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    @OneToMany(mappedBy = "publisherOriginal")
    private Set<Book> booksOriginal;
}