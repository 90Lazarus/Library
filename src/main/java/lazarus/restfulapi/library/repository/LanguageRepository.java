package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    boolean existsByName(String name);
}