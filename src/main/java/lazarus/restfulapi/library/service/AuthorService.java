package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.AuthorDTO;
import lazarus.restfulapi.library.model.entity.Author;
import lazarus.restfulapi.library.model.mapper.AuthorMapper;
import lazarus.restfulapi.library.repository.AuthorRepository;
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

    public AuthorDTO createAuthor(AuthorDTO authorDTO) throws UniqueViolationException {
        Author author = authorMapper.toAuthor(authorDTO);
        //if (authorRepository.findById(author.getId()).isEmpty()) {
            authorRepository.save(author);
        //} else {
        //    throw new UniqueViolationException(ErrorInfo.ResourceType.AUTHOR, author.getFullName());
        //}
        return authorMapper.toAuthorDTO(author);
    }

    public List<AuthorDTO> readAuthors(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(authorRepository.findAll().isEmpty())) {
            return authorMapper.toAuthorDTOs(authorRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR);
        }
    }

    public AuthorDTO readAuthorById(Long id) throws ResourceNotFoundException {
        return authorMapper.toAuthorDTO(authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR, id)));
    }

    public AuthorDTO updateAuthorById(Long id, AuthorDTO newAuthorDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (authorRepository.findById(id).isPresent()) {
            Author oldAuthor = authorRepository.findById(id).get();
            Author newAuthor = authorMapper.toAuthor(newAuthorDTO);
            if (authorRepository.existsById(newAuthor.getId())) {
                oldAuthor.setId(newAuthor.getId());
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
                oldAuthor.setBooks(newAuthor.getBooks());
                return authorMapper.toAuthorDTO(oldAuthor);
            } else {
                throw new UniqueViolationException(ErrorInfo.ResourceType.AUTHOR, id);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR, id);
        }
    }

    public void deleteAuthorById(Long id) throws ResourceNotFoundException {
        if (authorRepository.findById(id).isPresent()) {
            authorRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.AUTHOR, id);
        }
    }
}