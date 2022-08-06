package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.entity.Author;
import lazarus.restfulapi.library.model.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByFullName(String fullName);
    List<Author> findByPenName(String penName);
    List<Author> findByFullNameOrPenNameContaining(String fullName, String PenName);
    List<Author> findByNationality(String nationality);
    List<Author> findByGender(Gender gender);
}