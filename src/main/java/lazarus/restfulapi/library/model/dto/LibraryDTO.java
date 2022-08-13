package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.embed.Address;
import lombok.Data;

import java.time.Year;

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

    //@Schema(description = "Library working hours")
    //private List<LibraryWorkingTime> workingTime;

    @Schema(description = "Weather the library is currently open or not", example = "True")
    private Boolean open;

    @Schema(description = "Library website", example = "library.com")
    private String website;

    //@JsonIgnore
    //private List<Book> books;

    @Schema(description = "The number of books in the library's database", example = "256")
    private Integer size;
}