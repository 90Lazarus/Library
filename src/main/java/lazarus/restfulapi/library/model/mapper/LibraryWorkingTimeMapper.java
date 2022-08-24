package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.LibraryWorkingTimeDTO;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryWorkingTimeMapper {
    LibraryWorkingTimeDTO libraryWorkingTimeToLibraryWorkingTimeDTO(LibraryWorkingTime libraryWorkingTime);
    List<LibraryWorkingTimeDTO> libraryWorkingTimeToLibraryWorkingTimeDTOs(List<LibraryWorkingTime> libraryWorkingTime);
    LibraryWorkingTime libraryWorkingTimeDTOtoLibraryWorkingTime(LibraryWorkingTimeDTO libraryWorkingTimeDTO);
}