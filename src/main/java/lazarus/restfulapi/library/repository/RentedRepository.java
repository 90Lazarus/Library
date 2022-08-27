package lazarus.restfulapi.library.repository;

import lazarus.restfulapi.library.model.entity.Rented;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentedRepository extends JpaRepository<Rented, Long> {
    List<Rented> findByUser_IdAndDateReturnedIsNull(Long userId);
    boolean existsByIdAndUser_Id(Long rentedId, Long userId);
    List<Rented> findByUser_Id(Long userId);
    Rented findByIdAndUser_Id(Long rentedId, Long userId);
    Rented findByBook_IdAndDateReturnedIsNull(Long bookId);
    boolean existsByUser_IdAndBook_Id(Long userId, Long bookId);
    Rented findByUser_IdAndBook_Id(Long userId, Long bookId);
    List<Rented> findByDateReturnedIsNull();
}