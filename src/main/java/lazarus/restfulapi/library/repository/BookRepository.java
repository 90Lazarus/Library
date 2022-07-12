package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.Book;
import lazarus.restfulapi.library.model.enumerated.FormatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
    List<Book> findByTitleAndTitleOriginalContaining(String title, String titleOriginal);
    List<Book> findByAuthor(String name);
    List<Book> findByLanguage(String language);
    List<Book> findByPublisher(String publisher);
    List<Book> findByFormatType(FormatType formatType);
    List<Book> findByGenre(String genre);
}
