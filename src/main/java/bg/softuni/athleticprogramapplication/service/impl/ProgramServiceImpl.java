package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.entities.ProgramGoal;
import bg.softuni.athleticprogramapplication.repositories.ProgramRepository;
import bg.softuni.athleticprogramapplication.service.ProgramService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public List<Program> findAll() {
        return this.programRepository.findAll();
    }


    @Override
    public Program getProgramDetailsById(Long id) {
        return this.programRepository.findById(id).orElse(null);
    }



    @Override
    public Program findByProgramGoal(String programGoal) {
        ProgramGoal goal = ProgramGoal.valueOf(programGoal.toUpperCase());
        return programRepository.findByProgramGoal(goal).orElse(null);
    }

    @Override
    public Program findById(Long programId) {
        return this.programRepository.findById(programId).orElse(null);
    }
}
