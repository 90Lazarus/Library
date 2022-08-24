package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.User;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Data
@Schema(name = "Admin Rented")
public class RentedDTO {
    @Schema(description = "Rented identifier", example = "1")
    private Long id;

    @Schema(description = "Date and time the book was rented")
    private LocalDateTime dateRented;

    @Schema(description = "Date and time the book was returned")
    private LocalDateTime dateReturned;

    @Schema(description = "How long the book was held by the user")
    private Long daysHeld;

//    public Long getDaysHeld() {
//        if (this.dateRented == null) {
//            return null;
//        } else {
//            if (this.dateReturned != null) {
//                return ChronoUnit.DAYS.between(this.dateRented, this.dateReturned);
//            } else {
//                return ChronoUnit.DAYS.between(this.dateRented, LocalDateTime.now());
//            }
//        }
//    }

    @Schema(description = "Info about the user that rented the book")
    private User user;

    @Schema(description = "Info about the book that the user rented")
    private Book book;
}