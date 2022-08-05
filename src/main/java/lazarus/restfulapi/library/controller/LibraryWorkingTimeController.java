package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import lazarus.restfulapi.library.service.LibraryWorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryWorkingTimeController {
    @Autowired private LibraryWorkingTimeService libraryWorkingTimeService;

    @GetMapping("/libraries/{libraryId}/working_time")
    @Operation(summary = "View the list of all available working time for a library with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all working time for the library with the id"),
            @ApiResponse(responseCode = "404", description = "Library with the id has no working time")
    })
    public List<LibraryWorkingTime> getAllLibraryWorkingTime(@Parameter(description = "id of the library") @PathVariable("libraryId") Long libraryId) throws ResourceNotFoundException {
        return libraryWorkingTimeService.readLibraryWorkingTime(libraryId);
    }

    @GetMapping("/libraries/{libraryId}/working_time/{workingTimeId}")
    @Operation(summary = "View a working time with an id for a library with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the working time with the id for the library with the id"),
            @ApiResponse(responseCode = "404", description = "Can not find the entry in the database that matches the specified ids")
    })
    public LibraryWorkingTime getOneLibraryWorkingTime(@Parameter(description = "id of the library") @PathVariable("libraryId") Long libraryId, @Parameter(description = "id of the working time") @PathVariable("workingTimeId") Long workingTimeId) throws ResourceNotFoundException {
        return libraryWorkingTimeService.readLibraryWorkingTimeById(libraryId, workingTimeId);
    }

    @PostMapping("/libraries/{libraryId}/working_time")
    @Operation(summary = "Add new working time for a library with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created new working time for the library with the id"),
            @ApiResponse(responseCode = "404", description = "Library with the id does not exist")
    })
    public List<LibraryWorkingTime> saveLibraryWorkingTime(@Parameter(description = "id of the library") @PathVariable("libraryId") Long libraryId, @Valid @RequestBody LibraryWorkingTime libraryWorkingTime) throws ResourceNotFoundException {
        return libraryWorkingTimeService.createLibraryWorkingTimeById(libraryId, libraryWorkingTime);
    }

    @PutMapping("/libraries/{libraryId}/working_time/{workingTimeId}")
    @Operation(summary = "Modify a working time with an id for a library with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modified the working time with the id for the library with the id"),
            @ApiResponse(responseCode = "404", description = "Can not find the entry in the database that matches the specified ids")
    })
    public LibraryWorkingTime updateLibraryWorkingTime(@Parameter(description = "id of the library") @PathVariable("libraryId") Long libraryId, @Parameter(description = "id of the working time") @PathVariable("workingTimeId") Long workingTimeId, @Valid @RequestBody LibraryWorkingTime libraryWorkingTime) throws ResourceNotFoundException {
        return libraryWorkingTimeService.updateLibraryWorkingTime(libraryId, workingTimeId, libraryWorkingTime);
    }

    @Operation(summary = "Delete a working time with an id for a library with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the working time with the id for the library with the id"),
            @ApiResponse(responseCode = "404", description = "Can not find the entry in the database that matches the specified ids")
    })
    @DeleteMapping("/libraries/{libraryId}/working_time/{workingTimeId}")
    public void deleteLibraryWorkingTime(@Parameter(description = "id of the library") @PathVariable("libraryId") Long libraryId, @Parameter(description = "id of the working time") @PathVariable("workingTimeId") Long workingTimeId) throws ResourceNotFoundException {
        libraryWorkingTimeService.deleteLibraryWorkingTime(libraryId, workingTimeId);
    }
}