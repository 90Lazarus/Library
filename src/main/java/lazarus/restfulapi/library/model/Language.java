package lazarus.restfulapi.library.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Language {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne @JoinColumn(name = "book_original_id")
    private Book bookOriginal;
}