package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.GenreDTO;
import lazarus.restfulapi.library.model.dto.LanguageDTO;
import lazarus.restfulapi.library.model.entity.Genre;
import lazarus.restfulapi.library.model.mapper.GenreMapper;
import lazarus.restfulapi.library.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    @Autowired private GenreRepository genreRepository;
    @Autowired private GenreMapper genreMapper;

    public GenreDTO createGenre(GenreDTO genreDTO) throws UniqueViolationException {
        Genre genre = genreMapper.toGenre(genreDTO);
        if (genreRepository.existsByIdOrName(genre.getId(), genre.getName())) {
            throw new UniqueViolationException(ErrorInfo.ResourceType.GENRE, genre.getName());
        }
        else {
            genreRepository.save(genre);
        }
        return genreMapper.toGenreDTO(genre);
    }

    public List<GenreDTO> readGenres(Integer page, Integer size, Sort.Direction direction, String sortBy) {
        return genreMapper.toGenreDTOs(genreRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
    }

    public GenreDTO readGenreById(Long id) throws ResourceNotFoundException {
        return genreMapper.toGenreDTO(genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.GENRE, id)));
    }

    public GenreDTO updateGenre(Long id, GenreDTO genreDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (genreRepository.findById(id).isPresent()) {
            Genre oldGenre = genreRepository.findById(id).get();
            Genre newGenre = genreMapper.toGenre(genreDTO);
            if (genreRepository.existsByIdOrName(newGenre.getId(), newGenre.getName())) {
                throw new UniqueViolationException(ErrorInfo.ResourceType.GENRE, newGenre.getName());
            }
            else {
                oldGenre.setName(newGenre.getName());
                oldGenre.setDescription(newGenre.getDescription());
                oldGenre.setBooks(newGenre.getBooks());
                genreRepository.save(oldGenre);
                return genreMapper.toGenreDTO(oldGenre);
            }
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.GENRE, id);
        }
    }

    public void deleteGenre(Long id) throws ResourceNotFoundException {
        if (genreRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.GENRE, id);
        }
        else {
            genreRepository.deleteById(id);
        }
    }
}