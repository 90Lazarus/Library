package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.LibraryDTO;
import lazarus.restfulapi.library.model.entity.Library;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    LibraryDTO libraryToLibraryDTO(Library library);
    List<LibraryDTO> librariesToLibraryDTOs(List<Library> libraries);
    Library libraryDTOToLibrary(LibraryDTO libraryDTO);
}