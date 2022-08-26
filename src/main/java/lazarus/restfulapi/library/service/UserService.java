package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.*;
import lazarus.restfulapi.library.model.dto.NewUserDTO;
import lazarus.restfulapi.library.model.dto.PasswordResetDTO;
import lazarus.restfulapi.library.model.dto.UserDTO;
import lazarus.restfulapi.library.model.embed.PasswordResetToken;
import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.model.enums.Role;
import lazarus.restfulapi.library.model.mapper.NewUserMapper;
import lazarus.restfulapi.library.model.mapper.PasswordResetMapper;
import lazarus.restfulapi.library.model.mapper.RentedMapper;
import lazarus.restfulapi.library.model.mapper.UserMapper;
import lazarus.restfulapi.library.repository.RentedRepository;
import lazarus.restfulapi.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private UserMapper userMapper;
    @Autowired private RentedRepository rentedRepository;
    @Autowired private RentedMapper rentedMapper;
    @Autowired private NewUserMapper newUserMapper;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JavaMailSender mailSender;
    @Autowired private PasswordResetMapper passwordResetMapper;

    public UserDTO registerANewUser(NewUserDTO newUserDTO) throws UniqueViolationException, PasswordsDontMatchException {
        User newUser = newUserMapper.newUserDTOToUser(newUserDTO);
        if (!(userRepository.existsByEmail(newUser.getEmail()))) {
            if (Objects.equals(newUserDTO.getPassword(), newUserDTO.getMatchingPassword())) {
                newUser.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
                userRepository.save(newUser);
                //send a greeting email
                SimpleMailMessage greetingEmail = new SimpleMailMessage();
                greetingEmail.setFrom("slobodanmitrovic90@gmail.com");
                greetingEmail.setTo(newUser.getEmail());
                greetingEmail.setSubject("Registering a new user account in a Spring Boot Library");
                greetingEmail.setText("Thank you for registering a new account in our Library!");
                mailSender.send(greetingEmail);
                log.info("Greeting email sent to " + newUser.getEmail());
                //
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

    public UserDTO readAUser(Long userId) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            return userMapper.userToUserDTO(userRepository.findById(userId).get());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
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

    public UserDTO changeUserRole(Long userId, String newUserRole) throws ResourceNotFoundException, InvalidRoleException {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            if (EnumUtils.isValidEnum(Role.class, newUserRole)) {
                user.setRole(Role.valueOf(newUserRole));
                userRepository.save(user);
                return userMapper.userToUserDTO(user);
            } else {
                throw new InvalidRoleException(ErrorInfo.ResourceType.ROLE);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
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

    public void deleteAUser(Long userId) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.USER, userId);
        }
    }

    public void sendPasswordResetToken(String email) throws ResourceNotFoundException {
        //if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            user.setPasswordResetToken(new PasswordResetToken(UUID.randomUUID().toString(), 2L, LocalDateTime.now().plusHours(2)));
            userRepository.save(user);
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("slobodanmitrovic90@gmail.com");
            passwordResetEmail.setTo(email);
            passwordResetEmail.setSubject("Password Reset Request for Online Library");
            passwordResetEmail.setText("To reset your password, please click the link below:\n" + "http://localhost:8080/reset_password?token=" + user.getPasswordResetToken().getToken());
            mailSender.send(passwordResetEmail);
        //} else {
        //    throw new ResourceNotFoundException(ErrorInfo.ResourceType.EMAIL);
        //}
        log.info("Password reset token has been sent to a user provided email address!");
    }

    public UserDTO resetUserPassword(String token, PasswordResetDTO userUpdatedPassword) throws ResourceNotFoundException, PasswordsDontMatchException {
        if (userRepository.existsByPasswordResetTokenToken(token)) {
            if (LocalDateTime.now().isBefore(userRepository.findByPasswordResetTokenToken(token).getPasswordResetToken().getExpiryDateTime())) {
                User oldUserData = userRepository.findByPasswordResetTokenToken(token);
                User newUserData = passwordResetMapper.passwordResetDTOToUser(userUpdatedPassword);
                if (Objects.equals(userUpdatedPassword.getPassword(), userUpdatedPassword.getMatchingPassword())) {
                    oldUserData.setPassword(passwordEncoder.encode(newUserData.getPassword()));
                    oldUserData.setPasswordResetToken(new PasswordResetToken(null, null, null));
                    userRepository.save(oldUserData);
                    return userMapper.userToUserDTO(oldUserData);
                } else {
                    throw new PasswordsDontMatchException(ErrorInfo.ResourceType.PASSWORDS);
                }
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.EMAIL, "Token expired!");
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.EMAIL, "Invalid token!");
        }
    }
}