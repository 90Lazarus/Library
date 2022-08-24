package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.LibraryDTO;
import lazarus.restfulapi.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/libraries", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryController {
    @Autowired private LibraryService libraryService;

    @GetMapping
    @Operation(summary = "Retrieve the pageable list of all available libraries in the database, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found libraries in the database!")})
    public List<LibraryDTO> getLibraries(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer size,
                                         @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return libraryService.readLibraries(page, size, direction, sortBy);
    }

    @GetMapping("/{libraryId}")
    @Operation(summary = "Retrieve the information about a library in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the library!")})
    public LibraryDTO getALibrary(@Parameter(description = "Library's id") @PathVariable Long libraryId) throws ResourceNotFoundException {
        return libraryService.readALibrary(libraryId);
    }

    @GetMapping("/{libraryId}/books")
    @Operation(summary = "Retrieve the pageable list of all available books in a library with a specified id, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found books in the library!")})
    public List<BookDTO> getLibraryBooks(@Parameter(description = "Library's id") @PathVariable Long libraryId,
                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer size,
                                         @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return libraryService.readLibraryBooks(libraryId, page, size, direction, sortBy);
    }

    @GetMapping("/{libraryId}/books/{bookId}")
    @Operation(summary = "Retrieve the information about a book with a specified id for a library with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the book in the library!")})
    public BookDTO getALibraryBook(@Parameter(description = "Library's id") @PathVariable Long libraryId, @Parameter(description = "Book's id") @PathVariable Long bookId) throws ResourceNotFoundException {
        return libraryService.readALibraryBook(libraryId, bookId);
    }

    @PostMapping
    @Operation(summary = "Save a new author in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New library created!")})
    public LibraryDTO postALibrary(@RequestBody @Valid LibraryDTO libraryDTO) {
        return libraryService.createALibrary(libraryDTO);
    }

    @PutMapping("/{libraryId}")
    @Operation(summary = "Modify information about a library in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Library updated!")})
    public LibraryDTO putALibrary(@Parameter(description = "Library's id") @PathVariable Long libraryId, @RequestBody @Valid LibraryDTO libraryDTO) throws ResourceNotFoundException {
        return libraryService.updateALibrary(libraryId, libraryDTO);
    }

    @DeleteMapping("/{libraryId}")
    @Operation(summary = "Delete a library with a specified id from the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Library deleted!")})
    public void deleteLibrary(@Parameter(description = "Library's id") @PathVariable Long libraryId) throws ResourceNotFoundException {
        libraryService.deleteALibrary(libraryId);
    }
}