package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LanguageController {
    @Autowired private LanguageService languageService;

    @GetMapping("/languages")
    @Operation(summary = "Get the list of all available languages, optionally sorted by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found languages in the database"),
            @ApiResponse(responseCode = "404", description = "No languages found in the database")
    })
    public List<LanguageDTO> getAllLanguages(@RequestParam(required = false, defaultValue = "0") Integer page,
                                             @RequestParam(required = false, defaultValue = "10") Integer size,
                                             @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                             @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return languageService.readLanguages(page, size, direction, sortBy);
    }

    @GetMapping("/languages/{id}")
    @Operation(summary = "View a language with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the language")
    })
    public LanguageDTO getLanguageById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return languageService.readLanguageById(id);
    }

    @PostMapping("/languages")
    @Operation(summary = "Create a new unique language")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New language created")
    })
    public LanguageDTO saveLanguage(@RequestBody @Valid LanguageDTO languageDTO) throws UniqueViolationException {
        return languageService.createLanguage(languageDTO);
    }

    @PutMapping("/languages/{id}")
    @Operation(summary = "Modify a language with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Language updated")
    })
    public LanguageDTO updateLanguage(@PathVariable("id") Long id, @RequestBody @Valid LanguageDTO languageDTO) throws ResourceNotFoundException, UniqueViolationException {
        return languageService.updateLanguage(id, languageDTO);
    }

    @DeleteMapping("/languages/{id}")
    @Operation(summary = "Delete a language with an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Language deleted")
    })
    public void deleteLanguage(@PathVariable("id") Long id) throws ResourceNotFoundException {
        languageService.deleteLanguage(id);
    }
}
