package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.AuthorDTO;
import lazarus.restfulapi.library.model.entity.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO authorToAuthorDTO(Author author);
    List<AuthorDTO> authorsToAuthorDTOs(List<Author> authors);
    Author authorDTOToAuthor(AuthorDTO authorDTO);
}