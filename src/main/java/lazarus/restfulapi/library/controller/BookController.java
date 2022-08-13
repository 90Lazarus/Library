package lazarus.restfulapi.library.controller;

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
    public List<BookDTO> getBooks(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                  @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return bookService.readBooks(page, size, direction, sortBy);
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) throws ResourceNotFoundException {
        return bookService.readBookById(id);
    }

    @PostMapping
    public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) throws ResourceNotFoundException {
        return bookService.updateBookById(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
        bookService.deleteBookById(id);
    }
}
