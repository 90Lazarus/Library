package lazarus.restfulapi.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RentedRepositoryTest {
    @Autowired RentedRepository rentedRepository;

    @Test
    public void shouldExecuteQueries() {
        assertThat(rentedRepository.findAll()).isNotEmpty();
        assertThat(rentedRepository.findAll()).hasSize(3);
        assertThat(rentedRepository.findByDateReturnedIsNull()).hasSize(2);
        rentedRepository.getReferenceById(2L).setDateReturned(LocalDateTime.of(LocalDate.of(2022, Month.AUGUST, 27), LocalTime.of(18, 14, 56)));
        assertThat(rentedRepository.findById(2L).get().getDateReturned()).isNotNull();
    }
}