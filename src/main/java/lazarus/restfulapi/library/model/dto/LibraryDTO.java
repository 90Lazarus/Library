package lazarus.restfulapi.library.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import lombok.Data;

import java.time.Year;
import java.util.List;

@Data
@Schema(name = "Library")
public class LibraryDTO {
    @Schema(description = "Library identifier", example = "1")
    private Long id;

    @Schema(description = "Library name", example = "Belgrade library")
    private String name;

    @Schema(description = "The year library was first established", example = "2000")
    private Year yearEstablished;

    @Schema(description =  "Library address")
    private Address address;

    @JsonIgnore
    @Schema(description = "Library working hours")
    private List<LibraryWorkingTime> workingTime;

    private Boolean open;

    @Schema(description = "Library website", example = "library.com")
    private String website;

    @JsonIgnore
    private List<Book> books;

    private Integer size;
}