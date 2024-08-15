package bg.softuni.athletic_program_side_project.service;

import bg.softuni.athletic_program_side_project.entity.Run;
import bg.softuni.athletic_program_side_project.repository.RunRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RunService {
    private final RunRepository runRepository;

    public RunService(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    public List<Run> findAll() {
        return runRepository.findAll();
    }

    public Optional<Run> findById(Long id) {
        return runRepository.findById(id);
    }

    public Run save(Run run) {
        return runRepository.save(run);
    }

    public void deleteById(Long id) {
        runRepository.deleteById(id);
    }

    public List<Run> findByUserId(Long userId) {
        return runRepository.findByUserId(userId);
    }
}
