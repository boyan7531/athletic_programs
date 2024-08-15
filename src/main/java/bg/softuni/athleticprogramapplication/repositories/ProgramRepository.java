package bg.softuni.athleticprogramapplication.repositories;

import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.entities.ProgramGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    Optional<Program> findByProgramGoal(ProgramGoal programGoal);

}
