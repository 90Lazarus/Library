package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.AuthorDTO;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.entity.Author;
import lazarus.restfulapi.library.model.mapper.AuthorMapper;
import lazarus.restfulapi.library.model.mapper.BookMapper;
import lazarus.restfulapi.library.repository.AuthorRepository;
import lazarus.restfulapi.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    @Autowired private AuthorRepository authorRepository;
    @Autowired private AuthorMapper authorMapper;
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public AuthorDTO createAnAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        authorRepository.save(author);
        return authorMapper.authorToAuthorDTO(author);
    }

    public List<AuthorDTO> readAuthors(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(authorRepository.findAll().isEmpty())) {
            return authorMapper.authorsToAuthorDTOs(authorRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR);
        }
    }

    public AuthorDTO readAnAuthor(Long authorId) throws ResourceNotFoundException {
        if (authorRepository.findById(authorId).isPresent()) {
            return authorMapper.authorToAuthorDTO(authorRepository.findById(authorId).get());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR, authorId);
        }
    }

    public List<BookDTO> readAuthorsBooks(Long authorId) throws ResourceNotFoundException {
        if (authorRepository.findById(authorId).isPresent()) {
            if (!(authorRepository.findById(authorId).get().getBooks().isEmpty())) {
                return bookMapper.booksToBookDTOs(authorRepository.findById(authorId).get().getBooks());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, "Found no books in the database for the author with the id: " + authorId + "!");
                //throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.AUTHOR, authorId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR, authorId);
        }
    }

    public AuthorDTO updateAnAuthor(Long authorId, AuthorDTO newAuthorDTO) throws ResourceNotFoundException {
        if (authorRepository.findById(authorId).isPresent()) {
            Author oldAuthor = authorRepository.findById(authorId).get();
            Author newAuthor = authorMapper.authorDTOToAuthor(newAuthorDTO);
            oldAuthor.setFullName(newAuthor.getFullName());
            oldAuthor.setPenName(newAuthor.getPenName());
            oldAuthor.setDateOfBirth(newAuthor.getDateOfBirth());
            oldAuthor.setDateOfDeath(newAuthor.getDateOfDeath());
            oldAuthor.setNationality(newAuthor.getNationality());
            oldAuthor.setGender(newAuthor.getGender());
            oldAuthor.setOccupation(newAuthor.getOccupation());
            oldAuthor.setShortBio(newAuthor.getShortBio());
            oldAuthor.setPhoto(newAuthor.getPhoto());
            oldAuthor.setWikipediaPageAddress(newAuthor.getWikipediaPageAddress());
            oldAuthor.setGoodreadsPageAddress(newAuthor.getGoodreadsPageAddress());
            oldAuthor.setAuthorWebsiteAddress(newAuthor.getAuthorWebsiteAddress());
            return authorMapper.authorToAuthorDTO(oldAuthor);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR, authorId);
        }
    }

    public void deleteAnAuthor(Long authorId) throws ResourceNotFoundException {
        if (authorRepository.findById(authorId).isPresent()) {
            authorRepository.deleteById(authorId);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR, authorId);
        }
    }
}