package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.UserDTO;
import lazarus.restfulapi.library.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    List<UserDTO> usersToUserDTOs(List<User> users);
    User userDTOToUser(UserDTO userDTO);
    List<User> userDTOsToUsers(List<UserDTO> userDTOs);
}