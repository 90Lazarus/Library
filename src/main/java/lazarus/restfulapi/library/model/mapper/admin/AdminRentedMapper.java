package lazarus.restfulapi.library.model.mapper.admin;

import lazarus.restfulapi.library.model.dto.admin.AdminRentedDTO;
import lazarus.restfulapi.library.model.entity.Rented;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminRentedMapper {
    AdminRentedDTO rentedToAdminRentedDTO(Rented rented);
    List<AdminRentedDTO> rentedToAdminRentedDTOs(List<Rented> rented);
    Rented adminRentedDTOtoRented(AdminRentedDTO adminRentedDTO);
}
