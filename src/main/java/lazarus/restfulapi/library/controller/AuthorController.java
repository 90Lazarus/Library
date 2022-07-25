package lazarus.restfulapi.library.controller;

import lazarus.restfulapi.library.model.entity.Author;
import lazarus.restfulapi.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
//@RequestMapping(path = "/library", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping("/authors/{id}")
    public Author getAuthor(@PathVariable(value = "id") Long id) {
        return authorService.getAuthorById(id);
    }
}