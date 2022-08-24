package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Rented {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rented_id")
    private Long id;

    @NotNull(message = "Date when the book was rented cannot be null!")
    private LocalDateTime dateRented;

    @Builder.Default
    private LocalDateTime dateReturned = null;

    @Transient
    private Long daysHeld;

    public Long getDaysHeld() {
        return ChronoUnit.DAYS.between(this.getDateRented(), Objects.requireNonNullElseGet(this.getDateReturned(), LocalDateTime::now));
    }

    @ManyToOne(optional = false) @JoinColumn(name = "user_id")
    @NotNull(message = "User field is mandatory!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @ManyToOne(optional = false) @JoinColumn(name = "book_id")
    @NotNull(message = "Book field is mandatory!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Book book;
}