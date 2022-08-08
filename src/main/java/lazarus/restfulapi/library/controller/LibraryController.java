package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.LibraryDTO;
import lazarus.restfulapi.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "libraries", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryController {

    @Autowired private LibraryService libraryService;

    @GetMapping
    @Operation(summary = "Get the list of all available libraries, optionally sorted by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found libraries in the database"),
            @ApiResponse(responseCode = "404", description = "No libraries found in the database")
    })
    public List<LibraryDTO> getAllLibraries(@RequestParam(required = false, defaultValue = "0") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer size,
                                            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return libraryService.getAllLibraries(page, size, direction, sortBy);
    }

    @GetMapping("/{id}")
    @Operation(summary = "View a library with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the library")
    })
    public LibraryDTO getLibraryById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return libraryService.getLibraryById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New library created")
    })
    public LibraryDTO saveLibrary(@RequestBody LibraryDTO libraryDTO) {
        return libraryService.createLibrary(libraryDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify a library with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library updated")
    })
    public LibraryDTO updateLibrary(@PathVariable("id") Long id, @RequestBody LibraryDTO libraryDTO) throws ResourceNotFoundException {
        return libraryService.updateLibrary(id, libraryDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a library with the an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library deleted")
    })
    public void deleteLibrary(@PathVariable ("id") Long id) throws ResourceNotFoundException {
        libraryService.deleteLibraryById(id);
    }
}