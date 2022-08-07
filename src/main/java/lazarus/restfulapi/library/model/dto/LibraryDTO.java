package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.embeddable.Address;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Data
@Schema(name = "Library")
public class LibraryDTO {
    @Schema(description = "Library identifier", example = "1")
    private Long id;

    @Schema(description = "Library name", example = "Belgrade library")
    @NotNull private String name;

    @Schema(description = "The year library was first established", example = "2000")
    private Year yearEstablished;

    @Schema(description =  "Library address")
    private Address address;

    @Schema(description = "Library working hours")
    private List<LibraryWorkingTime> workingTime;

    @Transient
    private Boolean open;

    @Schema(description = "Library website", example = "library.com")
    private String website;

    private Set<Book> books;

    @Transient
    private Integer size;
}