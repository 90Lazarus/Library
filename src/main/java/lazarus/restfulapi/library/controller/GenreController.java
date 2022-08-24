package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.GenreDTO;
import lazarus.restfulapi.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController {
    @Autowired private GenreService genreService;

    @GetMapping
    @Operation(summary = "Retrieve the list of all available genres in the database, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found genres in the database!")})
    public List<GenreDTO> getGenres(@RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                    @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return genreService.readGenres(page, size, direction, sortBy);
    }

    @GetMapping("/{genreId}")
    @Operation(summary = "Retrieve the information about a genre in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the genre!")})
    public GenreDTO getAGenre(@Parameter(description = "Genre's id") @PathVariable Long genreId) throws ResourceNotFoundException {
        return genreService.readAGenre(genreId);
    }

    @GetMapping("/{genreId}/books")
    @Operation(summary = "Retrieve all of the books in the database that are written in the genre with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found books in the genre!")})
    public List<BookDTO> getGenreBooks(@Parameter(description = "Genre's id") @PathVariable Long genreId) throws ResourceNotFoundException {
        return genreService.readGenreBooks(genreId);
    }

    @PostMapping
    @Operation(summary = "Save a new unique genre in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New genre created!")})
    public GenreDTO postAGenre(@RequestBody @Valid GenreDTO genreDTO) throws UniqueViolationException {
        return genreService.createAGenre(genreDTO);
    }

    @PutMapping("/{genreId}")
    @Operation(summary = "Modify information about a genre in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Genre updated!")})
    public GenreDTO putAGenre(@Parameter(description = "Genre's id") @PathVariable Long genreId, @RequestBody @Valid GenreDTO genreDTO) throws ResourceNotFoundException, UniqueViolationException {
        return genreService.updateAGenre(genreId, genreDTO);
    }

    @DeleteMapping("{genreId}")
    @Operation(summary = "Delete a genre with a specified id from the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Genre deleted!")})
    public void deleteAGenre(@Parameter(description = "Genre's id") @PathVariable Long genreId) throws ResourceNotFoundException {
        genreService.deleteAGenre(genreId);
    }
}