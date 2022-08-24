package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Language")
public class LanguageDTO {
    @Schema(description = "Language identifier", example = "1")
    private Long id;

    @Schema(description = "Language name", example = "English")
    private String name;
}