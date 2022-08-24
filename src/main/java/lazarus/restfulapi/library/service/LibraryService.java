package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.LibraryDTO;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.Library;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import lazarus.restfulapi.library.model.mapper.BookMapper;
import lazarus.restfulapi.library.model.mapper.LibraryMapper;
import lazarus.restfulapi.library.repository.BookRepository;
import lazarus.restfulapi.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {
    @Autowired private LibraryRepository libraryRepository;
    @Autowired private LibraryMapper libraryMapper;
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public LibraryDTO createALibrary(LibraryDTO libraryDTO) {
        Library library = libraryMapper.libraryDTOToLibrary(libraryDTO);
        libraryRepository.save(library);
        return libraryMapper.libraryToLibraryDTO(library);
    }

    public List<LibraryDTO> readLibraries(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(libraryRepository.findAll().isEmpty())) {
            List<Library> libraries = libraryRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList();
            return libraryMapper.librariesToLibraryDTOs(libraries);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY);
        }
    }

    public LibraryDTO readALibrary(Long libraryId) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            Library library = libraryRepository.findById(libraryId).get();
            return libraryMapper.libraryToLibraryDTO(library);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public List<BookDTO> readLibraryBooks(Long libraryId, Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            if (!(libraryRepository.findById(libraryId).get().getBooks().isEmpty())) {
                List<Book> books = bookRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList();
                books.stream().filter(book -> book.getLibrary().getId().equals(libraryId)).collect(Collectors.toList());
                return bookMapper.booksToBookDTOs(books);
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.LIBRARY, libraryId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public BookDTO readALibraryBook(Long libraryId, Long bookId) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            if (bookRepository.findById(bookId).isPresent()) {
                if (bookRepository.existsByIdAndLibrary_Id(bookId, libraryId)) {
                    return bookMapper.bookToBookDTO(bookRepository.findByIdAndLibrary_Id(bookId, libraryId));
                } else {
                    throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId, ErrorInfo.ResourceType.BOOK, bookId);
                }
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, bookId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public LibraryDTO updateALibrary(Long libraryId, LibraryDTO newLibraryDTO) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            Library newLibrary = libraryMapper.libraryDTOToLibrary(newLibraryDTO);
            Library oldLibrary = libraryRepository.findById(libraryId).get();
            oldLibrary.setName(newLibrary.getName());
            oldLibrary.setYearEstablished(newLibrary.getYearEstablished());
            oldLibrary.setAddress(newLibrary.getAddress());
            oldLibrary.setWebsite(newLibrary.getWebsite());
            libraryRepository.save(oldLibrary);
            return libraryMapper.libraryToLibraryDTO(oldLibrary);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public void deleteALibrary(Long libraryId) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            libraryRepository.deleteById(libraryId);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }
}