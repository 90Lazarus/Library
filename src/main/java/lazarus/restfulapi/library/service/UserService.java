package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.UserDTO;
import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.model.enums.Role;
import lazarus.restfulapi.library.model.mapper.UserMapper;
import lazarus.restfulapi.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private UserMapper userMapper;

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }

    public List<UserDTO> readUsers(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(userRepository.findAll().isEmpty())) {
            return userMapper.usersToUserDTOs(userRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER);
        }
    }

    public UserDTO readUserById(Long id) throws ResourceNotFoundException {
        return userMapper.userToUserDTO(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.USER, id)));
    }

    public UserDTO updateUserById(Long id, UserDTO newUserDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (userRepository.findById(id).isPresent()) {
            User oldUser = userRepository.findById(id).get();
            User newUser = userMapper.userDTOToUser(newUserDTO);
            if (!(userRepository.existsByEmail(newUser.getEmail()))) {
                oldUser.setEmail(newUser.getEmail());
                oldUser.setPassword(newUser.getPassword());
                oldUser.setFirstName(newUser.getFirstName());
                oldUser.setLastName(newUser.getLastName());
                oldUser.setDateOfBirth(newUser.getDateOfBirth());
                oldUser.setGender(newUser.getGender());
                oldUser.setAddress(newUser.getAddress());
                return userMapper.userToUserDTO(oldUser);
            } else {
                throw new UniqueViolationException(ErrorInfo.ResourceType.EMAIL, newUser.getEmail());
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, id);
        }
    }

//    public UserDTO updateUserRole(Long id, Role role) throws ResourceNotFoundException {
//        if (userRepository.findById(id).isPresent()) {
//            User user = userRepository.findById(id).get();
//            user.setRole(role);
//            return userMapper.userToUserDTO(user);
//        } else {
//            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, id);
//        }
//    }

    public void deleteUserById(Long id) throws ResourceNotFoundException {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, id);
        }
    }
}
