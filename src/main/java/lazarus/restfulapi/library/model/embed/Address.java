package lazarus.restfulapi.library.model.embed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Schema(name = "Address")
public class Address {
    @Schema(description = "Country", example = "USA")
    private String country;

    @Schema(description = "State", example = "Michigan")
    private String state;

    @Schema(description = "City", example = "Detroit")
    private String city;

    @Schema(description = "Street", example = "Great Lakes Street")
    private String street;

    @Schema(description = "Street Number", example = "666")
    private Integer streetNumber;
}