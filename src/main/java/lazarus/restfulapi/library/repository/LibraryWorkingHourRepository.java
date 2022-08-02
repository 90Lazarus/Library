package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.entity.LibraryWorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryWorkingHourRepository extends JpaRepository<LibraryWorkingHour, Long> {

}
