package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO bookToBookDTO(Book book);
    List<BookDTO> booksToBookDTOs(List<Book> books);
    Book bookDTOtoBook(BookDTO bookDTO);
}