package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get the list of all available books, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found books in the database")})
    public List<BookDTO> getBooks(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                  @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return bookService.readBooks(page, size, direction, sortBy);
    }

    @GetMapping("/{id}")
    @Operation(summary = "View a book with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the book")})
    public BookDTO getBook(@PathVariable Long id) throws ResourceNotFoundException {
        return bookService.readBookById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new book")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New book created")})
    public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify a book with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book updated")})
    public BookDTO updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) throws ResourceNotFoundException {
        return bookService.updateBookById(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book deleted")})
    public void deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
        bookService.deleteBookById(id);
    }
}
