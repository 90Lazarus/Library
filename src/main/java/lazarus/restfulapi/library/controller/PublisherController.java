package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get the list of all available publishers, optionally sorted by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found publishers in the database"),
            @ApiResponse(responseCode = "404", description = "No publishers found in the database")
    })
    public List<PublisherDTO> getPublishers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer size,
                                            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                            @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return publisherService.readPublishers(page, size, direction, sortBy);
    }

    @GetMapping("/{id}")
    @Operation(summary = "View a publisher with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the publisher")
    })
    public PublisherDTO getPublisher(@PathVariable Long id) throws ResourceNotFoundException {
        return publisherService.readPublisherById(id);
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Get the list of all books by a publisher with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all books for the publisher with an id"),
            @ApiResponse(responseCode = "404", description = "Publisher with the id has no books in the database")
    })
    public List<BookDTO> getPublisherBooks(@PathVariable Long id) throws ResourceNotFoundException {
        return publisherService.readPublisherBooks(id);
    }

    @PostMapping
    @Operation(summary = "Create a new unique publisher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New publisher created")
    })
    public PublisherDTO savePublisher(@RequestBody @Valid PublisherDTO publisherDTO) throws UniqueViolationException {
        return publisherService.createPublisher(publisherDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify a publisher with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher updated")
    })
    public PublisherDTO updatePublisher(@PathVariable Long id, @RequestBody @Valid PublisherDTO publisherDTO) throws ResourceNotFoundException, UniqueViolationException {
        return publisherService.updatePublisherById(id, publisherDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a publisher with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher deleted")
    })
    public void deletePublisher(@PathVariable Long id) throws ResourceNotFoundException {
        publisherService.deletePublisherById(id);
    }
}
