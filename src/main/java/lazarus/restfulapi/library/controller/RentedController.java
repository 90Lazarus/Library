package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.admin.AdminRentedDTO;
import lazarus.restfulapi.library.model.dto.user.UserRentedDTO;
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

    @GetMapping("/users/{id}/rented")
    @Operation(summary = "Admin - View the list of all books currently rented by a user with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the rented books for the user")})
    public List<AdminRentedDTO> adminGetUserRentedBooks(@PathVariable Long id) throws ResourceNotFoundException {
        return rentedService.readBooksRentedById(id);
    }

    @GetMapping("/users/{id}/rented/history")
    @Operation(summary = "Admin - View the history of renting by a user with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the renting history for the user")})
    public List<AdminRentedDTO> adminGetUserRentingHistory(@PathVariable Long id) throws ResourceNotFoundException {
        return rentedService.readRentingHistoryById(id);
    }

    @GetMapping("/user/rented")
    @Operation(summary = "User - View the list of books currently rented by a currently logged in user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the rented books for the user")})
    public List<UserRentedDTO> userGetUserRentedBooks() throws ResourceNotFoundException {
        return rentedService.readBooksRented();
    }

    @GetMapping("/user/rented/history")
    @Operation(summary = "User - View the history of renting by a currently logged in user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the renting history for the user")})
    public List<UserRentedDTO> userGetUserRentingHistory() throws ResourceNotFoundException {
        return rentedService.readRentingHistory();
    }

    @PostMapping("/users/{userId}/rented/{bookId}")
    @Operation(summary = "Admin - Rent a book with an id for a user with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book rented")})
    public AdminRentedDTO saveRented(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody @Valid AdminRentedDTO adminRentedDTO) throws ResourceNotFoundException, UniqueViolationException {
        return rentedService.createRented(userId, bookId, adminRentedDTO);
    }

    @PutMapping("/users/{userId}/rented/{bookId}")
    @Operation(summary = "Admin - Return a book with an id for a user with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rented info modified")})
    public AdminRentedDTO updateRented(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody @Valid AdminRentedDTO adminRentedDTO) throws ResourceNotFoundException {
        return rentedService.updateRentedById(userId, bookId, adminRentedDTO);
    }

    @DeleteMapping("/users/{userId}/rented/{rentedId}")
    @Operation(summary = "Admin - Delete rented information with an id for a user with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rented info deleted")})
    public void deleteRented(@PathVariable Long userId, @PathVariable Long rentedId) throws ResourceNotFoundException {
        rentedService.deleteRentedById(userId, rentedId);
    }
}
