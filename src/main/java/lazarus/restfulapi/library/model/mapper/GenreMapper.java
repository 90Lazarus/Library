package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.GenreDTO;
import lazarus.restfulapi.library.model.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDTO genreToGenreDTO(Genre genre);
    List<GenreDTO> genresToGenreDTOs(List<Genre> genres);
    Genre genreDTOToGenre(GenreDTO genreDTO);
}