package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.GenreDTO;
import lazarus.restfulapi.library.model.entity.Genre;
import lazarus.restfulapi.library.model.mapper.BookMapper;
import lazarus.restfulapi.library.model.mapper.GenreMapper;
import lazarus.restfulapi.library.repository.BookRepository;
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
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public GenreDTO createAGenre(GenreDTO genreDTO) throws UniqueViolationException {
        Genre genre = genreMapper.genreDTOToGenre(genreDTO);
        if (!(genreRepository.existsByName(genre.getName()))) {
            genreRepository.save(genre);
            return genreMapper.genreToGenreDTO(genre);
        } else {
            throw new UniqueViolationException(ErrorInfo.ResourceType.GENRE, genre.getName());
        }
    }

    public List<GenreDTO> readGenres(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(genreRepository.findAll().isEmpty())) {
            return genreMapper.genresToGenreDTOs(genreRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.GENRE);
        }
    }

    public GenreDTO readAGenre(Long genreId) throws ResourceNotFoundException {
        if (genreRepository.findById(genreId).isPresent()) {
            return genreMapper.genreToGenreDTO(genreRepository.findById(genreId).get());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.GENRE, genreId);
        }
    }

    public List<BookDTO> readGenreBooks(Long genreId) throws ResourceNotFoundException {
        if (genreRepository.findById(genreId).isPresent()) {
            if (!(genreRepository.findById(genreId).get().getBooks().isEmpty())) {
                return bookMapper.booksToBookDTOs(genreRepository.findById(genreId).get().getBooks());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, "Found no books in the database for the genre with the id: " + genreId + "!");
                //throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.GENRE, genreId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.GENRE, genreId);
        }
    }

    public GenreDTO updateAGenre(Long genreId, GenreDTO genreDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (genreRepository.findById(genreId).isPresent()) {
            Genre oldGenre = genreRepository.findById(genreId).get();
            Genre newGenre = genreMapper.genreDTOToGenre(genreDTO);
            if (!(genreRepository.existsByName(newGenre.getName()))) {
                oldGenre.setName(newGenre.getName());
                oldGenre.setDescription(newGenre.getDescription());
                genreRepository.save(oldGenre);
                return genreMapper.genreToGenreDTO(oldGenre);
            } else {
                throw new UniqueViolationException(ErrorInfo.ResourceType.GENRE, newGenre.getName());
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.GENRE, genreId);
        }
    }

    public void deleteAGenre(Long genreId) throws ResourceNotFoundException {
        if (genreRepository.findById(genreId).isPresent()) {
            genreRepository.deleteById(genreId);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.GENRE, genreId);
        }
    }
}