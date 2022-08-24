package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.RentedDTO;
import lazarus.restfulapi.library.service.RentedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RentedController {
    @Autowired private RentedService rentedService;

    @GetMapping("/users/{userId}/rented")
    @Operation(summary = "Admin - Retrieve the list of all books currently rented by a user with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the rented books for the user!")})
    public List<RentedDTO> getUserRentedBooks(@Parameter(description = "User's id") @PathVariable Long userId) throws ResourceNotFoundException {
        return rentedService.readRentedBooksByUser(userId);
    }

    @GetMapping("/users/{userId}/rented/history")
    @Operation(summary = "Admin - Retrieve the history of renting by a user with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the renting history for the user")})
    public List<RentedDTO> getUserRentingHistory(@Parameter(description = "User's id") @PathVariable Long userId) throws ResourceNotFoundException {
        return rentedService.readRentingHistoryByUser(userId);
    }

    @GetMapping("/user/rented")
    @Operation(summary = "User - Retrieve the list of books currently rented by a currently logged in user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the rented books for the user!")})
    public List<RentedDTO> getMyRentedBooks() throws ResourceNotFoundException {
        return rentedService.readMyBooksRented();
    }

    @GetMapping("/user/rented/history")
    @Operation(summary = "User - Retrieve the history of renting by a currently logged in user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the renting history for the user!")})
    public List<RentedDTO> getMyRentingHistory() throws ResourceNotFoundException {
        return rentedService.readMyRentingHistory();
    }

    @PostMapping("/users/{userId}/rented/{bookId}")
    @Operation(summary = "Admin - Rent a book with a specified id for a user with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book rented!")})
    public RentedDTO rentABook(@Parameter(description = "User's id") @PathVariable Long userId, @Parameter(description = "Book's id") @PathVariable Long bookId, @RequestBody @Valid RentedDTO rentedDTO) throws ResourceNotFoundException, UniqueViolationException {
        return rentedService.createARented(userId, bookId, rentedDTO);
    }

    @PutMapping("/users/{userId}/rented/{bookId}")
    @Operation(summary = "Admin - Return a book with a specified id for a user with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Renting information modified!")})
    public RentedDTO updateARented(@Parameter(description = "User's id") @PathVariable Long userId, @Parameter(description = "Book's id") @PathVariable Long bookId, @RequestBody @Valid RentedDTO rentedDTO) throws ResourceNotFoundException {
        return rentedService.updateARented(userId, bookId, rentedDTO);
    }

    @DeleteMapping("/users/{userId}/rented/{rentedId}")
    @Operation(summary = "Admin - Delete a rented information with a specified id from the database for a user with a specified id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rented info deleted")})
    public void deleteARented(@Parameter(description = "User's id") @PathVariable Long userId, @Parameter(description = "Rented id") @PathVariable Long rentedId) throws ResourceNotFoundException {
        rentedService.deleteARented(userId, rentedId);
    }
}
