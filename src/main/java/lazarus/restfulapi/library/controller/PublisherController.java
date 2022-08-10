package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PublisherController {
    @Autowired private PublisherService publisherService;

    @GetMapping("/publishers")
    @Operation(summary = "Get the list of all available publishers, optionally sorted by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found publishers in the database"),
            @ApiResponse(responseCode = "404", description = "No publishers found in the database")
    })
    public List<PublisherDTO> getAllPublishers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                           @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                           @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return publisherService.readPublishers(page, size, direction, sortBy);
    }

    @GetMapping("/publishers/{id}")
    @Operation(summary = "View a publisher with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the publisher")
    })
    public PublisherDTO getPublisherById(@PathVariable Long id) throws ResourceNotFoundException {
        return publisherService.readPublisherById(id);
    }

    @PostMapping("/publishers")
    @Operation(summary = "Create a new unique publisher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New publisher created")
    })
    public PublisherDTO savePublisher(@RequestBody @Valid PublisherDTO publisherDTO) throws UniqueViolationException {
        return publisherService.createPublisher(publisherDTO);
    }

    @PutMapping("/publishers/{id}")
    @Operation(summary = "Modify a publisher with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher updated")
    })
    public PublisherDTO updatePublisher(@PathVariable Long id, @RequestBody @Valid PublisherDTO publisherDTO) throws ResourceNotFoundException, UniqueViolationException {
        return publisherService.updatePublisher(id, publisherDTO);
    }

    @DeleteMapping("/publishers/{id}")
    @Operation(summary = "Delete a publisher with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher deleted")
    })
    public void deletePublisher(@PathVariable Long id) throws ResourceNotFoundException {
        publisherService.deletePublisher(id);
    }
}
