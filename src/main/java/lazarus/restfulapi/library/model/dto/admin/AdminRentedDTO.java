package lazarus.restfulapi.library.model.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "Admin Rented")
public class AdminRentedDTO {
    @Schema(description = "Rented identifier", example = "1")
    private Long id;

    @Schema(description = "Date and time the book was rented")
    private LocalDateTime dateRented;

    @Schema(description = "Date and time the book was returned")
    private LocalDateTime dateReturned;

    @Schema(description = "How long the book was held by the user")
    private Long daysHeld;

    //@Schema(description = "Info about the user that rented the book")
    //private User user;

    @Schema(description = "Info about the book that the user rented")
    private Book book;
}
