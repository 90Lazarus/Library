package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Rented {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rented_id")
    private Long id;

    @NotNull(message = "Date when the book is rented cannot be null")
    private LocalDateTime dateRented;

    @Builder.Default
    private LocalDateTime dateReturned = null;

    @Transient
    private Long daysHeld;

    @ManyToOne(optional = false) @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User field is mandatory!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @ManyToOne(optional = false) @JoinColumn(name = "book_id")
    @NotNull(message = "Book field is mandatory!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Book book;
}