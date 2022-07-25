package lazarus.restfulapi.library.model.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Rented {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rented_id")
    private Long id;

    @Column(nullable = false)
    private Date dateRented;

    private Date dateReturned;

    @Transient
    private Integer daysHeld;

    @ManyToOne(optional = false) @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(optional = false) @JoinColumn(name = "book_id", unique = true, nullable = false, updatable = false)
    private Book book;
}
