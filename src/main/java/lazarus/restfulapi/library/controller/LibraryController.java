package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.Library;
import lazarus.restfulapi.library.service.LibraryService;
import lazarus.restfulapi.library.service.LibraryWorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "libraries", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private LibraryWorkingTimeService libraryWorkingTimeService;

    @GetMapping
    @Operation(summary = "Get the list of all available libraries, optionally sorted by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all libraries")
    })
    public List<Library> getAllLibraries(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer size,
                                         @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return libraryService.getAllLibraries(page, size, direction, sortBy);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the job with the following id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the library")
    })
    public Library getLibraryById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return libraryService.getLibraryById(id);
    }

//    @GetMapping("/{id}/working_hours")
//    @Operation(summary = "Get the working time for the library with the following id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Found the working time"),
//            @ApiResponse(responseCode = "404", description = "Can not find the working time"),
//    })
//    public List<LibraryWorkingTime> getLibraryWorkingHours(@PathVariable("id") Long id) throws ResourceNotFoundException {
//        return libraryService.getLibraryById(id).getWorkingTime();
//    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Get the list of all books in the library with the following id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the books")
    })
    public List<Book> getBooks(@PathVariable Long id) throws ResourceNotFoundException {
        return libraryService.getLibraryById(id).getBooks().stream().toList();
    }

    @PostMapping
    @Operation(summary = "Create a new library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New library created")
    })
    public Library saveLibrary(@RequestBody Library library) {
        return libraryService.createLibrary(library);
    }

//    @PostMapping("/{id}")
//    @Operation(summary = "Create a working hours for the library")
//    public List<LibraryWorkingTime> saveLibraryWorkingHour(@PathVariable("id") Long id, @RequestBody LibraryWorkingTime libraryWorkingTime) throws ResourceNotFoundException {
//        libraryWorkingTimeService.createLibraryWorkingTimeInLibrary(id, libraryWorkingTime);
//        return libraryService.getLibraryById(id).getWorkingTime();
//    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the contents of the library with the following id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library updated")
    })
    public Library updateLibrary(@PathVariable("id") Long id, @RequestBody Library library) throws ResourceNotFoundException {
        return libraryService.updateLibrary(id, library);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete the library with the following id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library deleted")
    })
    public void deleteLibrary(@PathVariable ("id") Long id) throws ResourceNotFoundException {
        libraryService.deleteLibraryById(id);
    }
}