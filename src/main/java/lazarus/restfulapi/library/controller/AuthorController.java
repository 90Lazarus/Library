package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get the list of all available authors, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found authors in the database")})
    public List<AuthorDTO> getAuthors(@RequestParam(required = false, defaultValue = "0") Integer page,
                                      @RequestParam(required = false, defaultValue = "10") Integer size,
                                      @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                      @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return authorService.readAuthors(page, size, direction, sortBy);
    }

    @GetMapping("/{id}")
    @Operation(summary = "View an author with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the author")})
    public AuthorDTO getAuthor(@PathVariable Long id) throws ResourceNotFoundException {
        return authorService.readAuthorById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new author")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New author created")})
    public AuthorDTO saveAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.createAuthor(authorDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify an author with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Author updated")})
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO) throws ResourceNotFoundException {
        return authorService.updateAuthorById(id, authorDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Author deleted")})
    public void deleteAuthor(@PathVariable Long id) throws ResourceNotFoundException {
        authorService.deleteAuthorById(id);
    }
}