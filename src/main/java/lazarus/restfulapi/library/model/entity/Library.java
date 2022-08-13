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
public class Library {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id", nullable = false)
    private Long id;

    @NotNull(message = "Library name cannot be null")
    private String name;

    private Year yearEstablished;

    @Embedded
    private Address address;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "library")
    private List<LibraryWorkingTime> workingTime;

    @Transient
    private Boolean open;

    private String website;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "library")
    private List<Book> books;

    @Transient
    private Integer size;
}