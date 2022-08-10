package lazarus.restfulapi.library.controller;

import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.AuthorDTO;
import lazarus.restfulapi.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {
    @Autowired private AuthorService authorService;

    @GetMapping
    public List<AuthorDTO> getAllAuthors(@RequestParam(required = false, defaultValue = "0") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer size,
                                            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                            @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return authorService.readAuthors(page, size, direction, sortBy);
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) throws ResourceNotFoundException {
        return authorService.readAuthorById(id);
    }

    @PostMapping
    public AuthorDTO saveAuthor(@RequestBody @Valid AuthorDTO authorDTO) throws UniqueViolationException {
        return authorService.createAuthor(authorDTO);
    }

    @PutMapping("/{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO) throws UniqueViolationException, ResourceNotFoundException {
        return authorService.updateAuthorById(id, authorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) throws ResourceNotFoundException {
        authorService.deleteAuthorById(id);
    }
}