package lazarus.restfulapi.library.controller;

import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
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
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired private UserService userService;

    @GetMapping
    public List<UserDTO> getUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                  @RequestParam(required = false, defaultValue = "id") String sortBy) throws ResourceNotFoundException {
        return userService.readUsers(page, size, direction, sortBy);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) throws ResourceNotFoundException {
        return userService.readUserById(id);
    }

    @PostMapping
    public UserDTO saveUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) throws UniqueViolationException, ResourceNotFoundException {
        return userService.updateUserById(id, userDTO);
    }

//    @PutMapping("/{id}")
//    public UserDTO updateUserRole(@PathVariable Long id, @RequestBody @Valid Role newRole) throws ResourceNotFoundException {
//        return userService.updateUserRole(id, newRole);
//    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteUserById(id);
    }
}
