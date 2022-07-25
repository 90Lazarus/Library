package lazarus.restfulapi.library.controller;

import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.Library;
import lazarus.restfulapi.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibraryController {
    @Autowired private LibraryService libraryService;

    @GetMapping("/libraries")
    public List<Library> getLibraries() {
        return libraryService.getLibraries();
    }

    @GetMapping("/libraries/{id}")
    public Library getLibraryById(@PathVariable ("id") Long id) throws ResourceNotFoundException {
        return libraryService.getLibraryById(id);
    }

    @GetMapping("/libraries/{id}/books")
    public List<Book> getBooks(@PathVariable Long id) throws ResourceNotFoundException {
        return libraryService.getLibraryById(id).getBooks().stream().toList();
    }
}