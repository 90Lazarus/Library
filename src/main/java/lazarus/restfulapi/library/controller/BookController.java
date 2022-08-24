package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    @Autowired private BookService bookService;

    @GetMapping
    @Operation(summary = "Retrieve the pageable list of all available books in the database, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found books in the database!")})
    public List<BookDTO> getBooks(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                  @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return bookService.readBooks(page, size, direction, sortBy);
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Retrieve the information about a book in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the book!")})
    public BookDTO getABook(@Parameter(description = "Book's id") @PathVariable Long bookId) throws ResourceNotFoundException {
        return bookService.readABook(bookId);
    }

    @PostMapping
    @Operation(summary = "Save a new book in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New book created!")})
    public BookDTO postABook(@RequestBody @Valid BookDTO bookDTO) {
        return bookService.createABook(bookDTO);
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "Modify information about a book in the database with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book updated!")})
    public BookDTO putABook(@Parameter(description = "Book's id") @PathVariable Long bookId, @RequestBody @Valid BookDTO bookDTO) throws ResourceNotFoundException {
        return bookService.updateABook(bookId, bookDTO);
    }

    @DeleteMapping("/{bookId}")
    @Operation(summary = "Delete a book with a specified id from the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book deleted!")})
    public void deleteABook(@Parameter(description = "Book's id") @PathVariable Long bookId) throws ResourceNotFoundException {
        bookService.deleteABook(bookId);
    }
}
