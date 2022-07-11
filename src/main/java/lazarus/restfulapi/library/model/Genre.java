package lazarus.restfulapi.library.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Genre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 2048)
    private String description;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}