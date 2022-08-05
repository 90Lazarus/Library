package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryWorkingTimeRepository extends JpaRepository<LibraryWorkingTime, Long> {
    boolean existsByIdAndLibrary_Id(Long libraryWorkingTimeId, Long libraryId);
    LibraryWorkingTime findByIdAndLibrary_Id(Long libraryWorkingTimeId, Long libraryId);
}
