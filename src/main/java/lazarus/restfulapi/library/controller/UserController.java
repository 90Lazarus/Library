package lazarus.restfulapi.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lazarus.restfulapi.library.exception.InvalidRoleException;
import lazarus.restfulapi.library.exception.PasswordsDontMatchException;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.NewUserDTO;
import lazarus.restfulapi.library.model.dto.UserDTO;
import lazarus.restfulapi.library.model.enums.Role;
import lazarus.restfulapi.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired private UserService userService;

    @GetMapping("/users")
    @Operation(summary = "Get the list of all users in the database, optionally sorted by parameters")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found users in the database")})
    public List<UserDTO> getUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                  @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return userService.readUsers(page, size, direction, sortBy);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "View a user with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the user")})
    public UserDTO getUser(@PathVariable Long id) throws ResourceNotFoundException {
        return userService.readUserById(id);
    }

    @GetMapping("/user")
    @Operation(summary = "View your own user data")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Data retrieved")})
    public NewUserDTO getUserInfo() {
        return userService.readUserInfo();
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User registered")})
    public UserDTO saveUser(@RequestBody @Valid NewUserDTO newUserDTO) throws UniqueViolationException, PasswordsDontMatchException {
        return userService.registerNewUser(newUserDTO);
    }

//    @PutMapping("/users/{id}")
//    @Operation(summary = "Modify a user with an id")
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User updated")})
//    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) throws UniqueViolationException, ResourceNotFoundException {
//        return userService.updateUserById(id, userDTO);
//    }

    @PutMapping("/user")
    @Operation(summary = "Modify your own user data")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Your data has been updated")})
    public UserDTO modifyUser(@RequestBody @Valid NewUserDTO newUserDTO) throws UniqueViolationException, PasswordsDontMatchException {
        return userService.modifyUserData(newUserDTO);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Modify a role for a user with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User's role modified")})
    public UserDTO modifyRole(@PathVariable Long id, @RequestBody @Valid String role) throws ResourceNotFoundException, InvalidRoleException {
        return userService.changeUserRoleById(id, role);
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete a user with an id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User deleted")})
    public void deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteUserById(id);
    }
}
