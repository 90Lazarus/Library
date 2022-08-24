package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.AuthorDTO;
import lazarus.restfulapi.library.model.dto.BookDTO;
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
    @Operation(summary = "Retrieve the pageable list of all available authors in the database, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found authors in the database!")})
    public List<AuthorDTO> getAuthors(@RequestParam(required = false, defaultValue = "0") Integer page,
                                      @RequestParam(required = false, defaultValue = "10") Integer size,
                                      @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                      @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return authorService.readAuthors(page, size, direction, sortBy);
    }

    @GetMapping("/{authorId}")
    @Operation(summary = "Retrieve the information about an author in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the author!")})
    public AuthorDTO getAnAuthor(@Parameter(description = "Author's id") @PathVariable Long authorId) throws ResourceNotFoundException {
        return authorService.readAnAuthor(authorId);
    }

    @GetMapping("/{authorId}/books")
    @Operation(summary = "Retrieve all of the books in the database written by an author with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found author's books!")})
    public List<BookDTO> getAuthorsBooks(@Parameter(description = "Author's id") @PathVariable Long authorId) throws ResourceNotFoundException {
        return authorService.readAuthorsBooks(authorId);
    }

    @PostMapping
    @Operation(summary = "Save a new author in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New author created!")})
    public AuthorDTO postAnAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.createAnAuthor(authorDTO);
    }

    @PutMapping("/{authorId}")
    @Operation(summary = "Modify information about an author in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Author updated!")})
    public AuthorDTO putAnAuthor(@Parameter(description = "Author's id") @PathVariable Long authorId, @RequestBody @Valid AuthorDTO authorDTO) throws ResourceNotFoundException {
        return authorService.updateAnAuthor(authorId, authorDTO);
    }

    @DeleteMapping("/{authorId}")
    @Operation(summary = "Delete an author with a specified id from the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Author deleted!")})
    public void deleteAnAuthor(@Parameter(description = "Author's id") @PathVariable Long authorId) throws ResourceNotFoundException {
        authorService.deleteAnAuthor(authorId);
    }
}