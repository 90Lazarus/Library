package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.model.entity.Author;
import lazarus.restfulapi.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.getReferenceById(id);
    }

    public Author updateAuthor(Long id, Author newAuthor) {
        Author oldAuthor = authorRepository.findById(id).get();
        newAuthor.setFullName(oldAuthor.getFullName());
        //
        return authorRepository.save(newAuthor);
    }

    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}