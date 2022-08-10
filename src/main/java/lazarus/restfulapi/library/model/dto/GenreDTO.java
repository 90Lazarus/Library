package lazarus.restfulapi.library.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.entity.Book;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Genre")
public class GenreDTO {
    @Schema(description = "Genre identifier", example = "1")
    private Long id;

    @Schema(description = "Genre name", example = "Fantasy")
    private String name;

    @Schema(description = "Genre description", example = "Fantasy is a genre of speculative fiction involving magical elements, typically set in a fictional universe and sometimes inspired by mythology and folklore")
    private String description;

    @JsonIgnore
    private List<Book> books;
}
