package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.LanguageDTO;
import lazarus.restfulapi.library.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
public class LanguageController {
    @Autowired private LanguageService languageService;

    @GetMapping
    @Operation(summary = "Retrieve the list of all available languages in the database, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found languages in the database!")})
    public List<LanguageDTO> getLanguages(@RequestParam(required = false, defaultValue = "0") Integer page,
                                          @RequestParam(required = false, defaultValue = "10") Integer size,
                                          @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                          @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return languageService.readLanguages(page, size, direction, sortBy);
    }

    @GetMapping("/{languageId}")
    @Operation(summary = "Retrieve the information about a language in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the language!")})
    public LanguageDTO getALanguage(@Parameter(description = "Language's id") @PathVariable Long languageId) throws ResourceNotFoundException {
        return languageService.readALanguage(languageId);
    }

    @GetMapping("/{languageId}/books")
    @Operation(summary = "Retrieve all of the books in the database that are written in the language with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found books in the language!")})
    public List<BookDTO> getLanguageBooks(@Parameter(description = "Language's id") @PathVariable Long languageId) throws ResourceNotFoundException {
        return languageService.readLanguageBooks(languageId);
    }

    @GetMapping("/{languageId}/original/books")
    @Operation(summary = "Retrieve all of the books in the database that were originally written in the language with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found books in the language!")})
    public List<BookDTO> getOriginalLanguageBooks(@Parameter(description = "Language's id") @PathVariable Long languageId) throws ResourceNotFoundException {
        return languageService.readOriginalLanguageBooks(languageId);
    }

    @PostMapping
    @Operation(summary = "Save a new unique language in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New language created!")})
    public LanguageDTO postALanguage(@RequestBody @Valid LanguageDTO languageDTO) throws UniqueViolationException {
        return languageService.createALanguage(languageDTO);
    }

    @PutMapping("/{languageId}")
    @Operation(summary = "Modify information about a language in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Language updated!")})
    public LanguageDTO putALanguage(@Parameter(description = "Language's id") @PathVariable Long languageId, @RequestBody @Valid LanguageDTO languageDTO) throws ResourceNotFoundException, UniqueViolationException {
        return languageService.updateALanguage(languageId, languageDTO);
    }

    @DeleteMapping("/{languageId}")
    @Operation(summary = "Delete a language with a specified id from the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Language deleted!")})
    public void deleteALanguage(@Parameter(description = "Language's id") @PathVariable Long languageId) throws ResourceNotFoundException {
        languageService.deleteALanguage(languageId);
    }
}