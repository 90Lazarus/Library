package lazarus.restfulapi.library.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.entity.Book;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "User Rented")
public class UserRentedDTO {
    @Schema(description = "Date and time the book was rented")
    private LocalDateTime dateRented;

    @Schema(description = "Date and time the book was returned")
    private LocalDateTime dateReturned;

    @Schema(description = "How long the book was held by the user")
    private Long daysHeld;

    @Schema(description = "Info about the book that the user rented")
    private Book book;
}