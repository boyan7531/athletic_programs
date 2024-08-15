package bg.softuni.athleticprogramapplication.repositories;

import bg.softuni.athleticprogramapplication.entities.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunRepository extends JpaRepository<Run, Integer> {
    List<Run> findByUserId(Long userId);

    Run findById(Long id);
}
