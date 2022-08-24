package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.RentedDTO;
import lazarus.restfulapi.library.model.entity.Rented;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentedMapper {
    RentedDTO rentedToRentedDTO(Rented rented);
    List<RentedDTO> rentedToRentedDTOs(List<Rented> rented);
    Rented rentedDTOtoRented(RentedDTO rentedDTO);
}