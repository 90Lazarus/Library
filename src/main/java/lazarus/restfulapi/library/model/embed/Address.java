package lazarus.restfulapi.library.model.embed;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Address {
    private String country;
    private String state;
    private String city;
    private String street;
    private Integer streetNumber;
}