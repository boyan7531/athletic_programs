package bg.softuni.athletic_program_side_project.repository;

import bg.softuni.athletic_program_side_project.entity.Run;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunRepository extends JpaRepository<Run, Long> {
    List<Run> findByUserId(Long userId);
}
