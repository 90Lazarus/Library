package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.PasswordResetDTO;
import lazarus.restfulapi.library.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordResetMapper {
    PasswordResetDTO userToPasswordResetDTO(User user);
    User passwordResetDTOToUser(PasswordResetDTO passwordResetDTO);
}