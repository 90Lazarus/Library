package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.LibraryWorkingTimeDTO;
import lazarus.restfulapi.library.service.LibraryWorkingTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryWorkingTimeController {
    @Autowired private LibraryWorkingTimeService libraryWorkingTimeService;

    @GetMapping("/libraries/{libraryId}/working_time")
    @Operation(summary = "Retrieve the list of all available working time in the database for a library with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found all working time for the library with the id!")})
    public List<LibraryWorkingTimeDTO> getAllLibraryWorkingTime(@Parameter(description = "Library's id") @PathVariable("libraryId") Long libraryId) throws ResourceNotFoundException {
        return libraryWorkingTimeService.readLibraryWorkingTime(libraryId);
    }

    @GetMapping("/libraries/{libraryId}/working_time/{workingTimeId}")
    @Operation(summary = "Retrieve a working time with an id for a library with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the working time with the id for the library with the id!")})
    public LibraryWorkingTimeDTO getALibraryWorkingTime(@Parameter(description = "Library's id") @PathVariable("libraryId") Long libraryId, @Parameter(description = "Library Working Time's id") @PathVariable("workingTimeId") Long workingTimeId) throws ResourceNotFoundException {
        return libraryWorkingTimeService.readALibraryWorkingTime(libraryId, workingTimeId);
    }

    @PostMapping("/libraries/{libraryId}/working_time")
    @Operation(summary = "Save a new working time in the database for a library with an id ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Created new working time for the library with the id!")})
    public LibraryWorkingTimeDTO postLibraryWorkingTime(@Parameter(description = "Library's id") @PathVariable("libraryId") Long libraryId, @RequestBody @Valid LibraryWorkingTimeDTO libraryWorkingTimeDTO) throws ResourceNotFoundException {
        return libraryWorkingTimeService.createALibraryWorkingTime(libraryId, libraryWorkingTimeDTO);
    }

    @PutMapping("/libraries/{libraryId}/working_time/{workingTimeId}")
    @Operation(summary = "Modify information about a working time with an id in the database for a library with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Updated the working time with the id for the library with the id!")})
    public LibraryWorkingTimeDTO putALibraryWorkingTime(@Parameter(description = "Library's id") @PathVariable("libraryId") Long libraryId, @Parameter(description = "Library Working Time's id") @PathVariable("workingTimeId") Long workingTimeId, @RequestBody @Valid LibraryWorkingTimeDTO libraryWorkingTimeDTO) throws ResourceNotFoundException {
        return libraryWorkingTimeService.updateALibraryWorkingTime(libraryId, workingTimeId, libraryWorkingTimeDTO);
    }

    @Operation(summary = "Delete a working time with an id from the database for a library with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Deleted the working time with the id for the library with the id!")})
    @DeleteMapping("/libraries/{libraryId}/working_time/{workingTimeId}")
    public void deleteALibraryWorkingTime(@Parameter(description = "Library's id") @PathVariable("libraryId") Long libraryId, @Parameter(description = "Library Working Time's id") @PathVariable("workingTimeId") Long workingTimeId) throws ResourceNotFoundException {
        libraryWorkingTimeService.deleteALibraryWorkingTime(libraryId, workingTimeId);
    }
}