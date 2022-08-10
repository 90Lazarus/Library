package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.AuthorDTO;
import lazarus.restfulapi.library.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toAuthorDTO(Author author);
    List<AuthorDTO> toAuthorDTOs(List<Author> authors);
    Author toAuthor(AuthorDTO authorDTO);
}
