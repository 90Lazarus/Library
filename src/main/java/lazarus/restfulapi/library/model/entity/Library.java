package lazarus.restfulapi.library.model.entity;

import lazarus.restfulapi.library.model.embed.Address;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Library {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id")
    private Long id;

    @NotNull
    private String name;

    private Year yearEstablished;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "library")
    private List<LibraryWorkingTime> workingTime;

    @Transient
    private Boolean open;

    private String website;

    @OneToMany(mappedBy = "library")
    private Set<Book> books;

    @Transient
    private Integer size;
}