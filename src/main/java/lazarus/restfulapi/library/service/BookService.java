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

    public BookDTO createABook(BookDTO bookDTO) {
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

    public BookDTO readABook(Long bookId) throws ResourceNotFoundException {
        if (bookRepository.findById(bookId).isPresent()) {
            return bookMapper.bookToBookDTO(bookRepository.findById(bookId).get());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, bookId);
        }
    }

    public BookDTO updateABook(Long bookId, BookDTO newBookDTO) throws ResourceNotFoundException {
        if (bookRepository.findById(bookId).isPresent()) {
            Book oldBook = bookRepository.findById(bookId).get();
            Book newBook = bookMapper.bookDTOtoBook(newBookDTO);
            oldBook.setTitle(newBook.getTitle());
            oldBook.setAuthor(newBook.getAuthor());
            oldBook.setLanguage(newBook.getLanguage());
            oldBook.setPublisher(newBook.getPublisher());
            oldBook.setPublicationDate(newBook.getPublicationDate());
            oldBook.setCover(newBook.getCover());
            oldBook.setNumberOfPages(newBook.getNumberOfPages());
            oldBook.setFormatType(newBook.getFormatType());
            oldBook.setGenre(newBook.getGenre());
            oldBook.setPlot(newBook.getPlot());
            oldBook.setIsbn(newBook.getIsbn());
            oldBook.setLibrary(newBook.getLibrary());
            oldBook.setAdult(newBook.isAdult());
            oldBook.setTitleOriginal(newBook.getTitleOriginal());
            oldBook.setLanguageOriginal(newBook.getLanguageOriginal());
            oldBook.setPublisherOriginal(newBook.getPublisherOriginal());
            oldBook.setPublicationDateOriginal(newBook.getPublicationDateOriginal());
            oldBook.setCoverOriginal(newBook.getCoverOriginal());
            oldBook.setNumberOfPagesOriginal(newBook.getNumberOfPagesOriginal());
            return bookMapper.bookToBookDTO(oldBook);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, bookId);
        }
    }

    public void deleteABook(Long bookId) throws ResourceNotFoundException {
        if (bookRepository.findById(bookId).isPresent()) {
            if (!(bookRepository.findById(bookId).get().isRented())) {
                bookRepository.deleteById(bookId);
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.RENTING_INFO, "Thief! return a book first!");
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, bookId);
        }
    }
}
