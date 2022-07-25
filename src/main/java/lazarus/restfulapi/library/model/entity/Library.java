package lazarus.restfulapi.library.model.entity;

import lazarus.restfulapi.library.model.embeddable.Address;
import lombok.*;
import javax.persistence.*;
import java.time.Year;
import java.util.Set;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Library {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private Year yearEstablished;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "library")
    private Set<LibraryWorkHours> workingHours;

    @Transient
    private Integer size;

    private String website;

    @OneToMany(mappedBy = "library")
    private Set<Book> books;
}