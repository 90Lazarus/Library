package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.model.enums.Gender;
import lazarus.restfulapi.library.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByFirstNameOrderByLastName(String firstName);
    List<User> findByGender(Gender gender);
    boolean existsByPasswordResetTokenToken(String token);
    User findByPasswordResetTokenToken(String token);
    List<User> findByRole(Role role);
}