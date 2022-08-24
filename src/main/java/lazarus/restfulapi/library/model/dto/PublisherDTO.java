package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.embed.Address;
import lombok.Data;

import java.time.Year;

@Data
@Schema(name = "Publisher")
public class PublisherDTO {
    @Schema(description = "Publisher identifier", example = "1")
    private Long id;

    @Schema(description = "Publisher name", example = "Bloomsbury Publishing")
    private String name;

    @Schema(description = "The year the publisher was first founded", example = "1986")
    private Year yearFounded;

    @Schema(description = "Publisher address", example = "London")
    private Address address;

    @Schema(description = "Publisher information", example = "Bloomsbury Publishing plc is a British worldwide publishing house of fiction and non-fiction")
    private String info;

    @Schema(description = "Publisher's website", example = "https://www.bloomsbury.com/uk/")
    private String website;
}