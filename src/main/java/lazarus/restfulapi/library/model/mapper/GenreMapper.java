package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.GenreDTO;
import lazarus.restfulapi.library.model.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDTO toGenreDTO(Genre genre);
    List<GenreDTO> toGenreDTOs(List<Genre> genres);
    Genre toGenre(GenreDTO genreDTO);
}