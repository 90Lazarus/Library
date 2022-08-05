package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "LibraryWorkingTime identifier", example = "1")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Schema(description = "Day of week", example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @NotNull
    @Schema(description = "The time at which the library opens in 'HH:mm:ss' format", example = "08:00:00")
    private Time openingTime;

    @NotNull
    @Schema(description = "The time at which the library closes in 'HH:mm:ss' format", example = "20:00:00")
    private Time closingTime;

    @ManyToOne @JoinColumn(name = "library_id")
    @JsonIgnore
    private Library library;
}