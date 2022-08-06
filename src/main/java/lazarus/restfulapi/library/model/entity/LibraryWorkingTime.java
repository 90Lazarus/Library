package lazarus.restfulapi.library.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NotNull
    private DayOfWeek dayOfWeek;

    @NotNull
    private Time openingTime;

    @NotNull
    private Time closingTime;

    @ManyToOne @JoinColumn(name = "library_id")
    @JsonIgnore
    private Library library;
}