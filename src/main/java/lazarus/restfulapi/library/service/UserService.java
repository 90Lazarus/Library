package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.*;
import lazarus.restfulapi.library.model.dto.NewUserDTO;
import lazarus.restfulapi.library.model.dto.UserDTO;
import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.model.enums.Role;
import lazarus.restfulapi.library.model.mapper.NewUserMapper;
import lazarus.restfulapi.library.model.mapper.user.UserRentedMapper;
import lazarus.restfulapi.library.model.mapper.UserMapper;
import lazarus.restfulapi.library.repository.RentedRepository;
import lazarus.restfulapi.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private UserMapper userMapper;
    @Autowired private RentedRepository rentedRepository;
    @Autowired private UserRentedMapper userRentedMapper;
    @Autowired private NewUserMapper newUserMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    public UserDTO registerNewUser(NewUserDTO newUserDTO) throws UniqueViolationException, PasswordsDontMatchException {
        User newUser = newUserMapper.newUserDTOToUser(newUserDTO);
        if (!(userRepository.existsByEmail(newUser.getEmail()))) {
            if (Objects.equals(newUserDTO.getPassword(), newUserDTO.getMatchingPassword())) {
                newUser.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
                userRepository.save(newUser);
                return userMapper.userToUserDTO(newUser);
            } else {
                throw new PasswordsDontMatchException(ErrorInfo.ResourceType.PASSWORDS);
            }
        } else {
            throw new UniqueViolationException(ErrorInfo.ResourceType.EMAIL);
        }
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

    public NewUserDTO readUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        return newUserMapper.userToNewUserDTO(user);
    }

    public UserDTO modifyUserData(NewUserDTO updatedUserDTO) throws UniqueViolationException, PasswordsDontMatchException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        User updatedUser = newUserMapper.newUserDTOToUser(updatedUserDTO);
        if ((Objects.equals(updatedUser.getEmail(), user.getEmail())) || !(userRepository.existsByEmail(updatedUser.getEmail()))) {
            if (Objects.equals(updatedUserDTO.getPassword(), updatedUserDTO.getMatchingPassword())) {
                user.setEmail(updatedUser.getEmail());
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                user.setFirstName(updatedUser.getFirstName());
                user.setLastName(updatedUser.getLastName());
                user.setDateOfBirth(updatedUser.getDateOfBirth());
                user.setGender(updatedUser.getGender());
                user.setAddress(updatedUser.getAddress());
                userRepository.save(user);
                return userMapper.userToUserDTO(user);
            } else {
                throw new PasswordsDontMatchException(ErrorInfo.ResourceType.PASSWORDS);
            }
        } else {
            throw new UniqueViolationException(ErrorInfo.ResourceType.EMAIL, updatedUser.getEmail());
        }
    }

    public UserDTO changeUserRoleById(Long id, String role) throws ResourceNotFoundException, InvalidRoleException {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            if (EnumUtils.isValidEnum(Role.class, role)) {
                user.setRole(Role.valueOf(role));
                userRepository.save(user);
                return userMapper.userToUserDTO(user);
            } else {
                throw new InvalidRoleException(ErrorInfo.ResourceType.ROLE);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, id);
        }
    }

//    public UserDTO updateUserById(Long id, UserDTO newUserDTO) throws ResourceNotFoundException, UniqueViolationException {
//        if (userRepository.findById(id).isPresent()) {
//            User oldUser = userRepository.findById(id).get();
//            User newUser = userMapper.userDTOToUser(newUserDTO);
//            if (!(userRepository.existsByEmail(newUser.getEmail())) || (Objects.equals(newUser.getEmail(), oldUser.getEmail()))) {
//                oldUser.setEmail(newUser.getEmail());
//                oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
//                oldUser.setFirstName(newUser.getFirstName());
//                oldUser.setLastName(newUser.getLastName());
//                oldUser.setDateOfBirth(newUser.getDateOfBirth());
//                oldUser.setGender(newUser.getGender());
//                oldUser.setAddress(newUser.getAddress());
//                oldUser.setRole(newUser.getRole());
//                userRepository.save(oldUser);
//                return userMapper.userToUserDTO(oldUser);
//            } else {
//                throw new UniqueViolationException(ErrorInfo.ResourceType.EMAIL, newUser.getEmail());
//            }
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
