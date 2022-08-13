package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.mapper.BookMapper;
import lazarus.restfulapi.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.bookDTOtoBook(bookDTO);
        bookRepository.save(book);
        return bookMapper.bookToBookDTO(book);
    }

    public List<BookDTO> readBooks(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(bookRepository.findAll().isEmpty())) {
            List<Book> books = bookRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList();
            return bookMapper.booksToBookDTOs(books);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK);
        }
    }

    public BookDTO readBookById(Long id) throws ResourceNotFoundException {
        return bookMapper.bookToBookDTO(bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, id)));
    }

    public BookDTO updateBookById(Long id, BookDTO newBookDTO) throws ResourceNotFoundException {
        if (bookRepository.findById(id).isPresent()) {
            Book oldBook = bookRepository.findById(id).get();
            Book newBook = bookMapper.bookDTOtoBook(newBookDTO);
            oldBook.setTitle(newBook.getTitle());
            //
            oldBook.setPublicationDate(newBook.getPublicationDate());
            oldBook.setCover(newBook.getCover());
            oldBook.setNumberOfPages(newBook.getNumberOfPages());
            oldBook.setFormatType(newBook.getFormatType());
            //
            oldBook.setPlot(newBook.getPlot());
            oldBook.setIsbn(newBook.getIsbn());
            //
            oldBook.setTitleOriginal(newBook.getTitleOriginal());
            //
            oldBook.setPublicationDateOriginal(newBook.getPublicationDateOriginal());
            oldBook.setCoverOriginal(newBook.getCoverOriginal());
            oldBook.setNumberOfPagesOriginal(newBook.getNumberOfPagesOriginal());
            return bookMapper.bookToBookDTO(oldBook);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, id);
        }
    }

    public void deleteBookById(Long id) throws ResourceNotFoundException {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, id);
        }
    }
}
