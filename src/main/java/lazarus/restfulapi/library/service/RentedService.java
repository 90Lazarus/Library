package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.RentedDTO;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.Rented;
import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.model.mapper.BookMapper;
import lazarus.restfulapi.library.model.mapper.RentedMapper;
import lazarus.restfulapi.library.repository.BookRepository;
import lazarus.restfulapi.library.repository.RentedRepository;
import lazarus.restfulapi.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentedService {
    @Autowired private RentedRepository rentedRepository;
    @Autowired private RentedMapper rentedMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public RentedDTO createARented(Long userId, Long bookId, RentedDTO rentedDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (userRepository.findById(userId).isPresent()) {
            if (bookRepository.findById(bookId).isPresent()) {
                User user = userRepository.findById(userId).get();
                Book book = bookRepository.findById(bookId).get();
                if (!(book.isRented())) {
                    if ((user.getAge() >= 18) || (!book.isAdult())) {
                        Rented rented = rentedMapper.rentedDTOtoRented(rentedDTO);
                        rented.setUser(user);
                        rented.setBook(book);
                        book.setRented(true);
                        bookRepository.save(book);
                        rentedRepository.save(rented);
                        return rentedMapper.rentedToRentedDTO(rented);
                    } else {
                        throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, "User's age must be 18 or over in order to rent this book!");
                    }
                } else {
                    throw new UniqueViolationException(ErrorInfo.ResourceType.BOOK, bookId, ErrorInfo.ResourceType.USER, rentedRepository.findByBook_IdAndDateReturnedIsNull(bookId).getUser().getId());
                }
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, bookId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
    }

    public List<RentedDTO> readRentedBooksByUser(Long userId) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            List<Rented> userRented = rentedRepository.findByUser_IdAndDateReturnedIsNull(userId);
            if (!(userRented.isEmpty())) {
                return rentedMapper.rentedToRentedDTOs(rentedRepository.findByUser_IdAndDateReturnedIsNull(userId));
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.USER, userId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
    }

    public List<RentedDTO> readRentingHistoryByUser(Long userId) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            List<Rented> userRented = rentedRepository.findByUser_Id(userId);
            if (!(userRented.isEmpty())) {
                return rentedMapper.rentedToRentedDTOs(userRepository.findById(userId).get().getBooksRented());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.RENTING_HISTORY, ErrorInfo.ResourceType.USER, userId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
    }

    public List<RentedDTO> readMyBooksRented() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        List<Rented> userRented = rentedRepository.findByUser_IdAndDateReturnedIsNull(user.getId());
        if (!(userRented.isEmpty())) {
            return rentedMapper.rentedToRentedDTOs(rentedRepository.findByUser_IdAndDateReturnedIsNull(user.getId()));
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.RENTING_INFO, "The current logged in user doesn't have any books currently rented!");
        }
    }

    public List<RentedDTO> readMyRentingHistory() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        List<Rented> userRented = rentedRepository.findByUser_Id(user.getId());
        if (!(userRented.isEmpty())) {
            return rentedMapper.rentedToRentedDTOs(userRepository.findById(user.getId()).get().getBooksRented());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.RENTING_INFO, "The current logged in user has not rented any books yet!");
        }
    }

    public RentedDTO updateARented(Long userId, Long bookId, RentedDTO rentedDTO) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            if (bookRepository.findById(bookId).isPresent()) {
                Book book = bookRepository.findById(bookId).get();
                if (rentedRepository.existsByUser_IdAndBook_Id(userId, bookId)) {
                    Rented oldRented = rentedRepository.findByUser_IdAndBook_Id(userId, bookId);
                    Rented newRented = rentedMapper.rentedDTOtoRented(rentedDTO);
                    oldRented.setDateReturned(newRented.getDateReturned());
                    book.setRented(false);
                    bookRepository.save(book);
                    rentedRepository.save(oldRented);
                    return rentedMapper.rentedToRentedDTO(oldRented);
                } else {
                    throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, bookId, ErrorInfo.ResourceType.USER, userId);
                }
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, bookId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
    }

    public void deleteARented(Long userId, Long rentedId) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            if (rentedRepository.findById(rentedId).isPresent()) {
                if (rentedRepository.existsByIdAndUser_Id(rentedId, userId)) {
                    rentedRepository.deleteById(rentedId);
                } else {
                    throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId, ErrorInfo.ResourceType.RENTING_INFO, rentedId);
                }
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.RENTING_INFO, rentedId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
    }
}