package lazarus.restfulapi.library.model.entity;

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

    @NotNull @Column(unique = true)
    private String name;

    @Column(length = 2048)
    private String description;

    @ManyToMany(mappedBy = "genre")
    private List<Book> books;
}