package lazarus.restfulapi.library.model.mapper.user;

import lazarus.restfulapi.library.model.dto.user.UserRentedDTO;
import lazarus.restfulapi.library.model.entity.Rented;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRentedMapper {
    UserRentedDTO rentedToUserRentedDTO(Rented rented);
    List<UserRentedDTO> rentedToUserRentedDTOs(List<Rented> rented);
    Rented userRentedDTOtoRented(UserRentedDTO userRentedDTO);
}