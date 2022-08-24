package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.PublisherDTO;
import lazarus.restfulapi.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/publishers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublisherController {
    @Autowired private PublisherService publisherService;

    @GetMapping
    @Operation(summary = "Retrieve the pageable list of all available publishers in the database, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found publishers in the database!")})
    public List<PublisherDTO> getPublishers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer size,
                                            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                            @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return publisherService.readPublishers(page, size, direction, sortBy);
    }

    @GetMapping("/{publisherId}")
    @Operation(summary = "Retrieve the information about a publisher in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the publisher!")})
    public PublisherDTO getAPublisher(@Parameter(description = "Publisher's id") @PathVariable Long publisherId) throws ResourceNotFoundException {
        return publisherService.readAPublisher(publisherId);
    }

    @GetMapping("/{publisherId}/books")
    @Operation(summary = "Retrieve all of the books in the database that are published by a publisher with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found books by a publisher!")})
    public List<BookDTO> getPublisherBooks(@Parameter(description = "Publisher's id") @PathVariable Long publisherId) throws ResourceNotFoundException {
        return publisherService.readPublisherBooks(publisherId);
    }

    @GetMapping("/{publisherId}/original/books")
    @Operation(summary = "Retrieve all of the books in the database that were originally published by a publisher with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found books by a publisher!")})
    public List<BookDTO> getOriginalPublisherBooks(@Parameter(description = "Publisher's id") @PathVariable Long publisherId) throws ResourceNotFoundException {
        return publisherService.readOriginalPublisherBooks(publisherId);
    }

    @PostMapping
    @Operation(summary = "Save a new unique publisher in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New publisher created!")})
    public PublisherDTO postAPublisher(@RequestBody @Valid PublisherDTO publisherDTO) throws UniqueViolationException {
        return publisherService.createAPublisher(publisherDTO);
    }

    @PutMapping("/{publisherId}")
    @Operation(summary = "Modify information about a publisher in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Publisher updated!")})
    public PublisherDTO updateAPublisher(@Parameter(description = "Publisher's id") @PathVariable Long publisherId, @RequestBody @Valid PublisherDTO publisherDTO) throws ResourceNotFoundException, UniqueViolationException {
        return publisherService.updateAPublisher(publisherId, publisherDTO);
    }

    @DeleteMapping("/{publisherId}")
    @Operation(summary = "Delete a publisher with a specified id from the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Publisher deleted!")})
    public void deleteAPublisher(@Parameter(description = "Publisher's id") @PathVariable Long publisherId) throws ResourceNotFoundException {
        publisherService.deleteAPublisher(publisherId);
    }
}