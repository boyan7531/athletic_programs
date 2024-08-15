package bg.softuni.athleticprogramapplication.repositories;

import bg.softuni.athleticprogramapplication.entities.ProgressTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressTrackerRepository  extends JpaRepository<ProgressTracker, Long> {
    List<ProgressTracker> findByUserId(Long userId);

    Optional<ProgressTracker> findTopByUserIdOrderByDateDesc(Long userId);

}
