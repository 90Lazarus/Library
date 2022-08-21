package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.admin.AdminRentedDTO;
import lazarus.restfulapi.library.model.dto.user.UserRentedDTO;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.Rented;
import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.model.mapper.BookMapper;
import lazarus.restfulapi.library.model.mapper.admin.AdminRentedMapper;
import lazarus.restfulapi.library.model.mapper.user.UserRentedMapper;
import lazarus.restfulapi.library.repository.BookRepository;
import lazarus.restfulapi.library.repository.RentedRepository;
import lazarus.restfulapi.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentedService {
    @Autowired private RentedRepository rentedRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private UserRentedMapper userRentedMapper;
    @Autowired private AdminRentedMapper adminRentedMapper;
    @Autowired private BookMapper bookMapper;

    public AdminRentedDTO createRented(Long userId, Long bookId, AdminRentedDTO adminRentedDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (userRepository.findById(userId).isPresent()) {
            if (bookRepository.findById(bookId).isPresent()) {
                User user = userRepository.findById(userId).get();
                Book book = bookRepository.findById(bookId).get();
                if (!(book.isRented())) {
                    Rented rented = adminRentedMapper.adminRentedDTOtoRented(adminRentedDTO);
                    rented.setUser(user);
                    rented.setBook(book);
                    book.setRented(true);
                    bookRepository.save(book);
                    rentedRepository.save(rented);
                    return adminRentedMapper.rentedToAdminRentedDTO(rented);
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

    public List<AdminRentedDTO> readBooksRentedById(Long userId) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            List<Rented> userRented = rentedRepository.findByUser_IdAndDateReturnedIsNull(userId);
            if (!(userRented.isEmpty())) {
                for (Rented rented : userRented) {
                    rented.setDaysHeld(ChronoUnit.DAYS.between(rented.getDateRented(), LocalDateTime.now()));
                }
                return adminRentedMapper.rentedToAdminRentedDTOs(rentedRepository.findByUser_IdAndDateReturnedIsNull(userId));
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.USER, userId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
    }

    public List<AdminRentedDTO> readRentingHistoryById(Long userId) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            List<Rented> userRented = rentedRepository.findByUser_Id(userId);
            if (!(userRented.isEmpty())) {
                for (Rented rented : userRented) {
                    if (rented.getDateReturned() == null) {
                        rented.setDaysHeld(ChronoUnit.DAYS.between(rented.getDateRented(), LocalDateTime.now()));
                    } else {
                        rented.setDaysHeld(ChronoUnit.DAYS.between(rented.getDateRented(), rented.getDateReturned()));
                    }
                }
                return adminRentedMapper.rentedToAdminRentedDTOs(userRepository.findById(userId).get().getBooksRented());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.RENTING_HISTORY, ErrorInfo.ResourceType.USER, userId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
    }

    public List<UserRentedDTO> readBooksRented() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        List<Rented> userRented = rentedRepository.findByUser_IdAndDateReturnedIsNull(user.getId());
        if (!(userRented.isEmpty())) {
            for (Rented rented : userRented) {
                rented.setDaysHeld(ChronoUnit.DAYS.between(rented.getDateRented(), LocalDateTime.now()));
            }
            return userRentedMapper.rentedToUserRentedDTOs(rentedRepository.findByUser_IdAndDateReturnedIsNull(user.getId()));
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.RENTING_INFO, "The current logged in user doesn't have any books currently rented!");
        }
    }

    public List<UserRentedDTO> readRentingHistory() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        List<Rented> userRented = rentedRepository.findByUser_Id(user.getId());
        if (!(userRented.isEmpty())) {
            for (Rented rented : userRented) {
                if (rented.getDateReturned() == null) {
                    rented.setDaysHeld(ChronoUnit.DAYS.between(rented.getDateRented(), LocalDateTime.now()));
                } else {
                    rented.setDaysHeld(ChronoUnit.DAYS.between(rented.getDateRented(), rented.getDateReturned()));
                }
            }
            return userRentedMapper.rentedToUserRentedDTOs(userRepository.findById(user.getId()).get().getBooksRented());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.RENTING_INFO, "The current logged in user has not rented any books yet!");
        }
    }

    public AdminRentedDTO updateRentedById(Long userId, Long bookId, AdminRentedDTO adminRentedDTO) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            if (bookRepository.findById(bookId).isPresent()) {
                Book book = bookRepository.findById(bookId).get();
                if (rentedRepository.existsByUser_IdAndBook_Id(userId, bookId)) {
                    Rented oldRented = rentedRepository.findByUser_IdAndBook_Id(userId, bookId);
                    Rented newRented = adminRentedMapper.adminRentedDTOtoRented(adminRentedDTO);
                    oldRented.setDateReturned(newRented.getDateReturned());
                    book.setRented(false);
                    bookRepository.save(book);
                    rentedRepository.save(oldRented);
                    return adminRentedMapper.rentedToAdminRentedDTO(oldRented);
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

    public void deleteRentedById(Long userId, Long rentedId) throws ResourceNotFoundException {
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