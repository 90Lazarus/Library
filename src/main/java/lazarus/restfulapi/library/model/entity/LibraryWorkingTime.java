package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.DayOfWeek;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class LibraryWorkingTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_working_time_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Day of Week cannot be null!")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Opening Time cannot be null!")
    private Time openingTime;

    @NotNull(message = "Closing Time cannot be null!")
    private Time closingTime;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne @JoinColumn(name = "library_id")
    private Library library;
}