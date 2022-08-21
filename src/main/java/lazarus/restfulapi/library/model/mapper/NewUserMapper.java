package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.NewUserDTO;
import lazarus.restfulapi.library.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewUserMapper {
    NewUserDTO userToNewUserDTO(User user);
    List<NewUserDTO> usersToNewUserDTOs(List<User> users);
    User newUserDTOToUser(NewUserDTO newUserDTO);
}
