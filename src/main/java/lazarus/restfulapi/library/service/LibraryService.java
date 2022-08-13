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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {

    @Autowired private LibraryRepository libraryRepository;
    @Autowired private LibraryMapper libraryMapper;
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public LibraryDTO createLibrary(LibraryDTO libraryDTO) {
        Library library = libraryMapper.toLibrary(libraryDTO);
        libraryRepository.save(library);
        return libraryMapper.toLibraryDTO(library);
    }

    public List<LibraryDTO> readLibraries(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(libraryRepository.findAll().isEmpty())) {
            List<Library> libraries = libraryRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList();
            for (Library library : libraries) {
                library.setOpen(checkIfLibraryIsOpen(library.getId()));
                library.setSize(setLibrarySize(library.getId()));
            }
            return libraryMapper.toLibraryDTOs(libraries);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY);
        }
    }

    public LibraryDTO readLibraryById(Long id) throws ResourceNotFoundException {
        Library library = libraryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id));
        library.setOpen(checkIfLibraryIsOpen(library.getId()));
        library.setSize(setLibrarySize(id));
        return libraryMapper.toLibraryDTO(library);
    }

    public List<BookDTO> readLibraryBooks(Long id, Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (libraryRepository.findById(id).isPresent()) {
            if (!(libraryRepository.findById(id).get().getBooks().isEmpty())) {
                List<Book> books = bookRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList();
                books.stream().filter(book -> book.getLibrary().getId().equals(id)).collect(Collectors.toList());
                return bookMapper.booksToBookDTOs(books);
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, id, ErrorInfo.ResourceType.LIBRARY);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id);
        }
    }

    public BookDTO readLibraryBookById(Long libraryId, Long bookId) throws ResourceNotFoundException {
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

    public LibraryDTO updateLibraryById(Long id, LibraryDTO newLibraryDTO) throws ResourceNotFoundException {
        if (libraryRepository.findById(id).isPresent()) {
            Library newLibrary = libraryMapper.toLibrary(newLibraryDTO);
            Library oldLibrary = libraryRepository.findById(id).get();
            oldLibrary.setName(newLibrary.getName());
            oldLibrary.setYearEstablished(newLibrary.getYearEstablished());
            oldLibrary.setAddress(newLibrary.getAddress());
            oldLibrary.setWebsite(newLibrary.getWebsite());
            libraryRepository.save(oldLibrary);
            return libraryMapper.toLibraryDTO(oldLibrary);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id);
        }
    }

    public void deleteLibraryById(Long id) throws ResourceNotFoundException {
        if (libraryRepository.findById(id).isPresent()) {
            libraryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id);
        }
    }

    private boolean checkIfLibraryIsOpen(Long id) {
        Library library = libraryRepository.getReferenceById(id);
        boolean isOpen = false;
        for (LibraryWorkingTime libraryWorkingTime : library.getWorkingTime()) {
            if (LocalDate.now().getDayOfWeek() == libraryWorkingTime.getDayOfWeek()) {
                if (LocalTime.now().isAfter(libraryWorkingTime.getOpeningTime().toLocalTime()) && LocalTime.now().isBefore(libraryWorkingTime.getClosingTime().toLocalTime())) {
                    isOpen = true;
                }
            }
        }
        return isOpen;
    }

    private Integer setLibrarySize(Long id) {
        return libraryRepository.getReferenceById(id).getBooks().size();
    }
}