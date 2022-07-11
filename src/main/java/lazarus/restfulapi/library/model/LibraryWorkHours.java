package lazarus.restfulapi.library.model;

import lombok.*;
import javax.persistence.*;
import java.sql.Time;
import java.time.DayOfWeek;

@Entity
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class LibraryWorkHours {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_work_hours_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private Time openingTime;

    @Column(nullable = false)
    private Time closingTime;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;
}