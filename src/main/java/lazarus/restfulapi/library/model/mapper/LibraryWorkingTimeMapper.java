package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.LibraryWorkingTimeDTO;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryWorkingTimeMapper {
    LibraryWorkingTimeDTO toLibraryWorkingTimeDTO(LibraryWorkingTime libraryWorkingTime);
    List<LibraryWorkingTimeDTO> toLibraryWorkingTimeDTOs(List<LibraryWorkingTime> libraryWorkingTime);
    LibraryWorkingTime toLibraryWorkingTime(LibraryWorkingTimeDTO libraryWorkingTimeDTO);
}