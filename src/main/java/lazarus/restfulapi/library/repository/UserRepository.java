package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.model.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByFirstNameOrderByLastName(String firstName);
    List<User> findByEmail(String email);
    List<User> findByGender(Gender gender);
    List<User> findByIsAdminTrue();
    List<User> findByIsAdminFalse();
}
