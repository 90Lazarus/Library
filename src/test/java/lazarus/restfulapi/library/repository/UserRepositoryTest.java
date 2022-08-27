package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.model.enums.Gender;
import lazarus.restfulapi.library.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test
    public void shouldExecuteQueries() {
        assertThat(userRepository.findAll()).hasSize(4);
        User userNissa = User.builder()
                .email("nissa@gmail.com")
                .password("zendikar")
                .firstName("Nissa")
                .lastName("Revane")
                .dateOfBirth(LocalDate.of(2000, Month.JULY, 5))
                .gender(Gender.FEMALE)
                .role(Role.ADMIN)
                .build();
        userRepository.save(userNissa);
        assertThat(userRepository.findAll()).hasSize(5);
        assertThat(userRepository.findByFirstNameAndLastName("Nissa", "Revane")).isNotEmpty();
        assertThat(userRepository.findByGender(Gender.OTHER)).hasSize(1);
        assertThat(userRepository.findByRole(Role.ADMIN)).hasSize(2);
        userNissa.setFirstName("Nahiri");
        userRepository.save(userNissa);
        assertThat(userRepository.findByFirstNameAndLastName("Nahiri", "Revane")).isNotEmpty();
        userRepository.deleteById(5L);
        assertThat(userRepository.findAll()).hasSize(4);
        assertThat(userRepository.existsByEmail("nissa@gmail.com")).isFalse();
    }
}