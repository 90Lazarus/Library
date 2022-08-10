package lazarus.restfulapi.library.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.entity.Book;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Language")
public class LanguageDTO {
    @Schema(description = "Language identifier", example = "1")
    private Long id;

    @Schema(description = "Language name", example = "English")
    private String name;

    @JsonIgnore
    private List<Book> books;

    @JsonIgnore
    private List<Book> booksOriginal;
}